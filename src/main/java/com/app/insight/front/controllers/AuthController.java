package com.app.insight.front.controllers;

import com.app.insight.front.service.AuthService;
import com.app.insight.service.command.LoginCommand;
import com.app.insight.service.command.RegistrationCommand;
import com.app.insight.service.dto.AppUserDTO;
import com.app.insight.service.dto.SecureUserDto;
import com.app.insight.service.dto.TokenDTO;
import javax.validation.Valid;

import com.app.insight.util.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller to authenticate users.
 */
@RestController("AuthController")
@RequestMapping("/api")
public class AuthController extends BaseController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserUtils userUtils;

    private final Logger log = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/registration")
    public ResponseEntity<Void> registration(@Valid @RequestBody RegistrationCommand registrationCommand) {
        log.debug("POST /registration");
        log.debug("registrationCommand : " + registrationCommand);
        authService.registration(registrationCommand);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> authorizeSuppliers(@Valid @RequestBody LoginCommand loginCommand) {
        log.debug("POST /login");
        return new ResponseEntity<>(authService.authorize(loginCommand), HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<SecureUserDto> getCurrentUser() {
        AppUserDTO appUser = userUtils.getCurrentUser();

        SecureUserDto secureUser = new SecureUserDto();
        secureUser.setId(appUser.getId());
        secureUser.setAge(appUser.getAge());
        secureUser.setFirstName(appUser.getFirstName());
        secureUser.setMiddleName(appUser.getMiddleName());
        secureUser.setLastName(appUser.getLastName());
        secureUser.setEmail(appUser.getEmail());
        secureUser.setLogin(appUser.getLogin());
        secureUser.setPhoneNumber(appUser.getPhoneNumber());
        secureUser.setIin(appUser.getIin());
        secureUser.setRegion(appUser.getRegion());
        secureUser.setSchool(appUser.getSchool());
        secureUser.setCoins(appUser.getCoins());
        secureUser.setEntResult(appUser.getEntResult());
        secureUser.setAppRoles(appUser.getAppRoles());
        secureUser.setSubgroups(appUser.getSubgroups());

        return new ResponseEntity<>(secureUser, HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenDTO> refreshToken(@RequestParam String refreshToken) {
        return new ResponseEntity<>(authService.refreshToken(refreshToken), HttpStatus.OK);
    }
}
