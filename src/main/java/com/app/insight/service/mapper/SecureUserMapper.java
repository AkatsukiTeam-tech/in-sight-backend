package com.app.insight.service.mapper;

import com.app.insight.service.dto.AppUserDTO;
import com.app.insight.service.dto.SecureUserDto;
import org.springframework.stereotype.Component;

@Component
public class SecureUserMapper {

    public SecureUserDto toDto(AppUserDTO appUser) {
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
        secureUser.setCity(appUser.getCity());
        secureUser.setSchool(appUser.getSchool());
        secureUser.setCoins(appUser.getCoins());
        secureUser.setEntResult(appUser.getEntResult());
        secureUser.setAppRoles(appUser.getAppRoles());
        secureUser.setSubgroups(appUser.getSubgroups());

        return secureUser;
    }

}
