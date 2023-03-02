package com.app.insight.front.service;

import com.app.insight.domain.AppUser;
import com.app.insight.service.*;
import com.app.insight.service.command.LoginCommand;
import com.app.insight.service.command.RegistrationCommand;
import com.app.insight.service.dto.*;
import com.app.insight.service.mapper.SecureUserMapper;
import com.app.insight.util.JwtTokenUtil;
import com.app.insight.util.UserUtils;
import com.app.insight.web.rest.errors.InvalidLoginOrPassword;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Locale;

@Service
public class AuthService {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private AppRoleService appRoleService;

    @Autowired
    private ParentsNumberService parentsNumberService;

    @Autowired
    private CityService cityService;

    @Autowired
    private RegionService regionService;

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private SecureUserMapper secureUserMapper;

    @Autowired
    private UserUtils userUtils;

    private final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final Locale locale = LocaleContextHolder.getLocale();

    public TokenDTO authorize(LoginCommand loginCommand) {
        UserDetails appUser = appUserService.loadUserByUsername(loginCommand.getLogin());
        if (appUser == null) {
            throw new InvalidLoginOrPassword();
        }

        AppUser user = (AppUser) appUser;

        if (!appUser.isEnabled()) {
            String description;
            try {
                description = messageSource.getMessage("error.user_is_blocked", null, locale);
            } catch (NoSuchMessageException exception) {
                description = "Пользователь заблокирован. Обратитесь к администратору системы";
            }
            throw new RuntimeException(description);
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
            appUser,
            null,
            appUser.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        String accessToken = jwtTokenUtil.generateAccessToken(appUser);
        String refreshToken = jwtTokenUtil.generateRefreshToken(appUser);
        return new TokenDTO(accessToken, refreshToken, getMe());
    }

    @Transactional
    public void registration(RegistrationCommand registrationCommand) {
        validateRegistrationCommand(registrationCommand);
        createUser(registrationCommand);
    }

    public TokenDTO refreshToken(String refreshToken) {
        if (jwtTokenUtil.isRefreshToken(refreshToken)) {
            String username = jwtTokenUtil.getUsernameFromToken(refreshToken);
            UserDetails appUser = appUserService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                appUser,
                null,
                appUser.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            String accessToken = jwtTokenUtil.generateAccessToken(appUser);
            refreshToken = jwtTokenUtil.generateRefreshToken(appUser);
            return new TokenDTO(accessToken, refreshToken, getMe());
        }
        throw new IllegalArgumentException("not a valid refresh token");
    }

    public SecureUserDto getMe() {
        AppUserDTO appUser = userUtils.getCurrentUser();
        return secureUserMapper.toDto(appUser);
    }

    private void validateRegistrationCommand(RegistrationCommand registrationCommand) {
        // Password validation
        if (!registrationCommand.getPassword().equals(registrationCommand.getPasswordConfirmation())) {
            String description = messageSource.getMessage("error.validation.password_confirmation", null, locale);
            throw new RuntimeException(description);
        }

        // email check for unique for active users
        AppUserDTO appUserExistByEmail = appUserService.findByEmail(registrationCommand.getEmail()).orElse(null);
        if (appUserExistByEmail != null) {
            if (appUserExistByEmail.getIsActive()) {
                log.debug("email is not unique");
                String description = messageSource.getMessage("error.validation.user.exist_email", null, locale);
                throw new RuntimeException(description);
            }
        }

        // username check for unique
        AppUserDTO appUserExistByUsername = appUserService.findByLogin(registrationCommand.getLogin().toLowerCase()).orElse(null);
        if (appUserExistByUsername != null) {
            log.debug("username is not unique");
            if (appUserExistByUsername.getIsActive()) {
                String description = messageSource.getMessage("error.validation.user.exist_username", null, locale);
                throw new RuntimeException(description);
            }
        }

        // phoneNumber check for unique
        AppUserDTO appUserExistByPhone = appUserService.findByPhone(registrationCommand.getPhoneNumber()).orElse(null);
        if (appUserExistByPhone != null) {
            log.debug("phone is not unique");
            if (appUserExistByPhone.getIsActive()) {
                String description = messageSource.getMessage("error.validation.user.exist_username", null, locale);
                throw new RuntimeException(description);
            }
        }

        // parentsNumber check for notNull
        if (registrationCommand.getParentsNumbers() == null) {
            log.debug("parentsNumber is not unique");
            String description = messageSource.getMessage("error.validation.user.exist_username", null, locale);
            throw new RuntimeException(description);
        }
    }

    private void createUser(RegistrationCommand registrationCommand) {
        AppUserDTO appUserDTO = new AppUserDTO();
        appUserDTO.setFirstName(registrationCommand.getFirstName());
        appUserDTO.setMiddleName(registrationCommand.getMiddleName());
        appUserDTO.setLastName(registrationCommand.getLastName());
        appUserDTO.setAge(registrationCommand.getAge());
        appUserDTO.setIin(registrationCommand.getIin());
        appUserDTO.setEntResult(registrationCommand.getEntResult());
        appUserDTO.setEmail(registrationCommand.getEmail());
        appUserDTO.setPhoneNumber(registrationCommand.getPhoneNumber());
        appUserDTO.setLogin(registrationCommand.getLogin());
        appUserDTO.setPassword(passwordEncoder.encode(registrationCommand.getPassword()));
        appUserDTO.setRegDateTime(ZonedDateTime.now());
        appUserDTO.setIsActive(true);
        appUserDTO.setIsDeleted(false);
        if (registrationCommand.getRole() != null) {
            AppRoleDTO role = appRoleService
                .findByName(registrationCommand.getRole())
                .orElseThrow(() -> new RuntimeException("Такая роль не найдена"));
            appUserDTO.getAppRoles().add(role);
        }

        if (registrationCommand.getCity().getId() == null) {
            // create city if it doesn't exist
            CityDTO saveCity = cityService.save(registrationCommand.getCity());

            // after create region
            registrationCommand.getRegion().setCity(saveCity);
            RegionDTO saveRegion = regionService.save(registrationCommand.getRegion());
            appUserDTO.setCity(saveCity);
            appUserDTO.setRegion(saveRegion);
        } else {
            appUserDTO.setCity(registrationCommand.getCity());
            appUserDTO.setRegion(registrationCommand.getRegion());
        }

        if (registrationCommand.getSchool().getId() == null) {
            // create school if it doesn't exist
            SchoolDTO saveSchool = schoolService.save(registrationCommand.getSchool());
            appUserDTO.setSchool(saveSchool);
        } else {
            appUserDTO.setSchool(registrationCommand.getSchool());
        }

        AppUserDTO finalAppUserDTO = appUserService.save(appUserDTO);
        registrationCommand
            .getParentsNumbers()
            .forEach(parentsNumberDTO -> {
                parentsNumberDTO.setAppUser(finalAppUserDTO);
                parentsNumberService.save(parentsNumberDTO);
            });
    }
}
