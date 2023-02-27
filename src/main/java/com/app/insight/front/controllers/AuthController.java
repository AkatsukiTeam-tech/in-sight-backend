package com.app.insight.front.controllers;

import com.app.insight.front.service.AuthService;
import com.app.insight.service.command.LoginCommand;
import com.app.insight.service.command.RegistrationCommand;
import com.app.insight.service.dto.TokenDTO;
import javax.validation.Valid;
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
        log.debug("loginCommand : " + loginCommand);
        return new ResponseEntity<>(authService.authorize(loginCommand), HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenDTO> refreshToken(@RequestParam String refreshToken) {
        log.debug("POST /refresh-token");
        log.debug("refreshTokenCommand : " + refreshToken);
        return new ResponseEntity<>(authService.refreshToken(refreshToken), HttpStatus.OK);
    }
}
