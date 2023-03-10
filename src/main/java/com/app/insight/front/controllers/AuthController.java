package com.app.insight.front.controllers;

import com.app.insight.front.service.AuthService;
import com.app.insight.service.command.LoginCommand;
import com.app.insight.service.command.RegistrationCommand;
import com.app.insight.service.command.ResetPasswordCommand;
import com.app.insight.service.dto.GeneratedPasswordDto;
import com.app.insight.service.dto.SecureUserDto;
import com.app.insight.service.dto.TokenDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public ResponseEntity<GeneratedPasswordDto> registration(@Valid @RequestBody RegistrationCommand registrationCommand) {
        log.debug("POST /registration");
        return new ResponseEntity<>(authService.registration(registrationCommand), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> authorize(@Valid @RequestBody LoginCommand loginCommand) {
        log.debug("POST /login");
        return new ResponseEntity<>(authService.authorize(loginCommand), HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<SecureUserDto> getCurrentUser() {
        return new ResponseEntity<>(authService.getMe(), HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenDTO> refreshToken(@RequestParam String refreshToken) {
        return new ResponseEntity<>(authService.refreshToken(refreshToken), HttpStatus.OK);
    }

    @PutMapping("/reset-password")
    public ResponseEntity<SecureUserDto> resetPassword(@RequestBody ResetPasswordCommand newPasswordCommand) {
        return new ResponseEntity<>(authService.resetPassword(newPasswordCommand), HttpStatus.OK);
    }
}
