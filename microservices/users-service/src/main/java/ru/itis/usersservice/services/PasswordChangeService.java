package ru.itis.usersservice.services;

import org.springframework.security.core.userdetails.UserDetails;
import ru.itis.usersservice.dto.PasswordDto;

import java.security.Principal;

public interface PasswordChangeService {
    void createToken(PasswordDto passwordDto, Principal userPrincipal);

    void changePassword(String confirmCode, Principal userPrincipal);

}
