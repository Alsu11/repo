package ru.itis.usersservice.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.itis.usersservice.client.EmailClient;
import ru.itis.usersservice.dao.jpa.PasswordChangeConfirmationRepository;
import ru.itis.usersservice.dao.jpa.UsersRepository;
import ru.itis.usersservice.dto.EmailDto;
import ru.itis.usersservice.dto.PasswordDto;
import ru.itis.usersservice.dto.enums.State;
import ru.itis.usersservice.exceptions.ConfirmException;
import ru.itis.usersservice.exceptions.Errors;
import ru.itis.usersservice.exceptions.UsersException;
import ru.itis.usersservice.models.PasswordChangeConfirmationClassEntity;
import ru.itis.usersservice.models.UserEntity;
import ru.itis.usersservice.services.PasswordChangeService;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordChangeServiceImpl implements PasswordChangeService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final PasswordChangeConfirmationRepository passwordChangeConfirmationRepository;

    private final UsersRepository userRepository;

    @Value("${password.change.url}")
    String emailUrl;

    private final EmailClient emailClient;

    @Override
    public void createToken(PasswordDto passwordDto, Principal userDetails) {
        if (passwordDto.getEmail().equals(userDetails.getName())) {
            UserEntity user = userRepository.findByEmail(userDetails.getName()).orElseThrow(() -> new UsersException(Errors.USER_NOT_FOUND));
            if (user.getState().equals(State.NOT_CONFIRMED)) {
                throw new UsersException(Errors.NOT_CONFIRMED_EMAIL);
            } else {
                if (passwordEncoder.matches(passwordDto.getOldPassword(), user.getPassword())) {
                    if (passwordEncoder.matches(passwordDto.getNewPassword(), user.getPassword())) {
                        throw new UsersException(Errors.SAME_PASSWORDS);
                    } else {
                        PasswordChangeConfirmationClassEntity passwordChange = PasswordChangeConfirmationClassEntity.builder()
                                .confirmed(false)
                                .user(user)
                                .newPassword(passwordEncoder.encode(passwordDto.getNewPassword()))
                                .token(UUID.randomUUID().toString())
                                .build();
                        passwordChangeConfirmationRepository.save(passwordChange);

                        Map<String, Object> data = new HashMap<>();
                        data.put("user", user);
                        data.put("confirmLink", emailUrl + passwordChange.getToken());

                        EmailDto emailDto = EmailDto.builder()
                                .data(data)
                                .to(userDetails.getName())
                                .subject("Password change confirmation")
                                .templateName("confirm_password_change")
                                .build();

                        emailClient.sendEmail(emailDto);
                    }
                } else throw new UsersException(Errors.INCORRECT_PASSWORD_OR_EMAIL);
            }
        } else throw new UsersException(Errors.ACCESS_DENIED);
    }

    @Override
    public void changePassword(String confirmCode, Principal userDetails) {
        UserEntity user = userRepository.findByEmail(userDetails.getName()).orElseThrow(() -> new UsersException(Errors.USER_NOT_FOUND));
        PasswordChangeConfirmationClassEntity passwordChange = passwordChangeConfirmationRepository.findByToken(confirmCode)
                .orElseThrow(() -> new ConfirmException(Errors.ALREADY_CONFIRMED_CHANGE));
        if (user.getId() == passwordChange.getUser().getId()) {
            user.setPassword(passwordChange.getNewPassword());
            userRepository.save(user);
            passwordChangeConfirmationRepository.delete(passwordChange);
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(passwordChange.getUser().getEmail(), passwordChange.getNewPassword(), auth.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authRequest);
            auth.setAuthenticated(false);
        } else throw new UsersException(Errors.ACCESS_DENIED);
    }
}
