package com.app.insight.util;

import com.app.insight.domain.AppUser;
import com.app.insight.service.AppUserService;
import com.app.insight.service.dto.AppUserDTO;
import com.app.insight.web.rest.errors.UserNotFoundError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserUtils {

    @Autowired
    private AppUserService appUserService;

    @Transactional
    public AppUserDTO getCurrentUser() {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder
            .getContext()
            .getAuthentication();
        AppUser user = (AppUser) usernamePasswordAuthenticationToken.getPrincipal();
        return appUserService.findOne(user.getId()).orElseThrow(UserNotFoundError::new);
    }
}
