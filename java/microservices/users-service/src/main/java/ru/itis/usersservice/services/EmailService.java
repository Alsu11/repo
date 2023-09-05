package ru.itis.usersservice.services;

import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;

public interface EmailService {

    Boolean confirmEmail(String confirmCode, Principal userDetails);

    void createConfirmationToken(Principal userDetails);

}
