package ru.itis.usersservice.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.usersservice.api.EmailConfirmationApi;
import ru.itis.usersservice.services.EmailService;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
public class EmailConfirmationController implements EmailConfirmationApi {

    private final EmailService emailService;

    @Override
    public Boolean emailConfirmation(String confirmCode, Principal userPrincipal) {
        return emailService.confirmEmail(confirmCode, userPrincipal);
    }

    @Override
    public void confirmationToken(Principal userPrincipal) {
        emailService.createConfirmationToken(userPrincipal);
    }
}
