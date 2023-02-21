package ru.itis.usersservice.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.itis.usersservice.client.EmailClient;
import ru.itis.usersservice.dao.jpa.EmailConfirmationRepository;
import ru.itis.usersservice.dao.jpa.UsersRepository;
import ru.itis.usersservice.dto.EmailDto;
import ru.itis.usersservice.dto.enums.State;
import ru.itis.usersservice.exceptions.ConfirmException;
import ru.itis.usersservice.exceptions.Errors;
import ru.itis.usersservice.exceptions.UsersException;
import ru.itis.usersservice.models.EmailConfirmationClassEntity;
import ru.itis.usersservice.models.UserEntity;
import ru.itis.usersservice.services.EmailService;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Value("${email.confirm.url}")
    String emailUrl;

    private final EmailConfirmationRepository emailConfirmationRepository;

    private final EmailClient emailClient;

    private final UsersRepository userRepository;

    @Override
    public Boolean confirmEmail(String confirmCode, Principal userDetails) {

        UserEntity user = userRepository.findByEmail(userDetails.getName()).orElseThrow(() -> new UsersException(Errors.USER_NOT_FOUND));
        EmailConfirmationClassEntity confirmationToken = emailConfirmationRepository.findByToken(confirmCode)
                .orElseThrow(()->new ConfirmException(Errors.CONFIRM_CODE_NOT_FOUND));
        if (user.getId() == confirmationToken.getUser().getId()) {

            if (user.getState().equals(State.CONFIRMED)) {
                throw new ConfirmException(Errors.ALREADY_CONFIRMED_EMAIL);
            } else {
                user.setState(State.CONFIRMED);
                userRepository.save(user);
                emailConfirmationRepository.delete(confirmationToken);
            }
        } else throw new UsersException(Errors.ACCESS_DENIED);
        return true;
    }


    @Override
    public void createConfirmationToken(Principal userDetails) {

        UserEntity user = userRepository.findByEmail(userDetails.getName()).orElseThrow(() -> new UsersException(Errors.USER_NOT_FOUND));
        if (user.getState().equals(State.CONFIRMED)) {
            throw new ConfirmException(Errors.ALREADY_CONFIRMED_EMAIL);
        } else {

            EmailConfirmationClassEntity token = EmailConfirmationClassEntity.builder()
                    .token(UUID.randomUUID().toString())
                    .user(user)
                    .build();
            emailConfirmationRepository.save(token);

            Map<String, Object> data = new HashMap<>();
            data.put("user", user);
            data.put("confirmLink", emailUrl + token.getToken());

            EmailDto emailDto = EmailDto.builder()
                    .data(data)
                    .to(userDetails.getName())
                    .subject("Email confirmation")
                    .templateName("confirm_mail")
                    .build();

            emailClient.sendEmail(emailDto);
        }
    }
}

