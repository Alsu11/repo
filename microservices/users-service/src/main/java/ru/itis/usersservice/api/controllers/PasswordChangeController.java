package ru.itis.usersservice.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.usersservice.api.PasswordChangeApi;
import ru.itis.usersservice.dto.PasswordDto;
import ru.itis.usersservice.services.PasswordChangeService;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
public class PasswordChangeController implements PasswordChangeApi {

    private final PasswordChangeService passwordChangeService;

    @Override
    public void passwordChangeToken(PasswordDto passwordDto, Principal userPrincipal) {
        passwordChangeService.createToken(passwordDto, userPrincipal);
    }

    @Override
    public void passwordChange(String confirmCode, Principal userPrincipal) {
        passwordChangeService.changePassword(confirmCode, userPrincipal);
    }
}
