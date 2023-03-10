package com.app.insight.front.service;

import com.app.insight.service.*;
import com.app.insight.service.command.LoginCommand;
import com.app.insight.service.command.RegistrationCommand;
import com.app.insight.service.command.ResetPasswordCommand;
import com.app.insight.service.dto.*;
import com.app.insight.service.mapper.SecureUserMapper;
import com.app.insight.util.JwtTokenUtil;
import com.app.insight.util.UserUtils;
import com.app.insight.util.Utils;
import com.app.insight.web.rest.errors.InvalidLoginOrPassword;
import com.app.insight.web.rest.errors.ObjectNotFoundError;
import com.app.insight.web.rest.errors.ValidationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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

    @Autowired
    private MailService mailService;

    private final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final Locale locale = LocaleContextHolder.getLocale();

    public TokenDTO authorize(LoginCommand loginCommand) {
        UserDetails appUser = appUserService.loadUserByUsername(loginCommand.getLogin());
        if (appUser == null) {
            throw new InvalidLoginOrPassword();
        }

        if (!appUser.isEnabled()) {
            throw new ValidationError(messageSource.getMessage("error.user_is_blocked", null, locale));
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
    public GeneratedPasswordDto registration(RegistrationCommand registrationCommand) {
        validateRegistrationCommand(registrationCommand);
        return createUser(registrationCommand);
    }

    @Transactional
    public SecureUserDto resetPassword(ResetPasswordCommand resetPasswordCommand) {
        if (resetPasswordCommand.getPassword().equals(resetPasswordCommand.getPasswordConfirmation())) {
            AppUserDTO appUser = userUtils.getCurrentUser();
            appUser.setPassword(passwordEncoder.encode(resetPasswordCommand.getPassword()));
            appUserService.save(appUser);
            return secureUserMapper.toDto(appUser);
        }
        throw new ValidationError(messageSource.getMessage("error.passwords_dont_match", null, locale));
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

        // email check for unique for active users
        AppUserDTO appUserExistByEmail = appUserService.findByEmail(registrationCommand.getEmail()).orElse(null);
        if (appUserExistByEmail != null) {
            if (appUserExistByEmail.getIsActive()) {
                String description = messageSource.getMessage("error.validation.user.exist_email", null, locale);
                throw new ValidationError(description);
            }
        }

        // username check for unique
        AppUserDTO appUserExistByUsername = appUserService.findByLogin(registrationCommand.getLogin().toLowerCase()).orElse(null);
        if (appUserExistByUsername != null) {
            if (appUserExistByUsername.getIsActive()) {
                String description = messageSource.getMessage("error.validation.user.exist_username", null, locale);
                throw new ValidationError(description);
            }
        }

        // phoneNumber check for unique
        AppUserDTO appUserExistByPhone = appUserService.findByPhone(registrationCommand.getPhoneNumber()).orElse(null);
        if (appUserExistByPhone != null) {
            if (appUserExistByPhone.getIsActive()) {
                String description = messageSource.getMessage("error.validation.user.exist_phone", null, locale);
                throw new ValidationError(description);
            }
        }

        // iin check for unique
        AppUserDTO appUserExistByIin = appUserService.findByIin(registrationCommand.getIin()).orElse(null);
        if (appUserExistByIin != null) {
            if (appUserExistByIin.getIsActive()) {
                String description = messageSource.getMessage("error.validation.user.exist_iin", null, locale);
                throw new ValidationError(description);
            }
        }

        // parentsNumber check for notNull
        if (registrationCommand.getParentsNumbers() == null) {
            String description = messageSource.getMessage("error.validation.user.parents_number_null", null, locale);
            throw new ValidationError(description);
        }
    }

    private GeneratedPasswordDto createUser(RegistrationCommand registrationCommand) {
        String password = RandomUtil.generatePassword();

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
        appUserDTO.setPassword(passwordEncoder.encode(password));
        appUserDTO.setRegDateTime(ZonedDateTime.now());
        appUserDTO.setIsActive(true);
        appUserDTO.setIsDeleted(false);
        if (registrationCommand.getRole() != null) {
            AppRoleDTO role = appRoleService
                .findByName(registrationCommand.getRole())
                .orElseThrow(() -> new ObjectNotFoundError("Роль не найдена"));
            appUserDTO.getAppRoles().add(role);
        }

        if (registrationCommand.getCity().getId() == null) {
            // create region if it doesn't exist
            RegionDTO saveRegion = regionService.save(registrationCommand.getRegion());

            // after create city
            registrationCommand.getCity().setRegion(saveRegion);
            CityDTO saveCity = cityService.save(registrationCommand.getCity());
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
        mailService.sendCreationEmail(finalAppUserDTO, password);

        return new GeneratedPasswordDto(password);
    }
}
