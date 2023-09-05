package ru.itis.shelter.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.shelter.dao.UsersRepository;
import ru.itis.shelter.dto.ConfirmDto;
import ru.itis.shelter.dto.SignUpForm;
import ru.itis.shelter.dto.UserDto;
import ru.itis.shelter.exceptions.Errors;
import ru.itis.shelter.exceptions.UsersException;
import ru.itis.shelter.models.UserEntity;
import ru.itis.shelter.services.UsersService;
import ru.itis.shelter.utils.confirm.EmailUtil;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final EmailUtil emailUtil;
    private final PasswordEncoder passwordEncoder;

    @Value("${confirm.expiration}")
    private String confirmCodeExpirationTime;

    @Override
    public ConfirmDto signUp(SignUpForm signUpForm) {
        Optional<UserEntity> user = usersRepository.findByEmail(signUpForm.getEmail());

        if (user.isPresent()) {
            UserEntity user1 = user.get();
            if (user1.getState().equals(UserEntity.State.NOT_CONFIRMED)) {
                if(user1.getUntil().isBefore(Instant.now())) {
                    sendEmail(user1);
                    return new ConfirmDto("We have send you a new email, please verify your email");
                } else {
                    return new ConfirmDto("Please verify your email");
                }
            } else {
                throw new UsersException(Errors.EMAIL_ALREADY_TAKEN);
            }
        }

        sendEmail(usersRepository
                .save(
                        buildUser(signUpForm)
                ));

        usersRepository.save(buildUser(signUpForm));

        return new ConfirmDto("Please verify your email");
    }

    @Transactional
    @Override
    public UserDto confirm(String confirmCode) {
        UserEntity user = usersRepository.findByConfirmCode(confirmCode)
                .orElseThrow(() -> new UsersException(Errors.USER_NOT_FOUND));

        if(Instant.now().isBefore(user.getUntil())) {
            user.setState(UserEntity.State.CONFIRMED);

            return UserDto.from(
                    usersRepository
                            .save(user));

        }
        throw new UsersException(Errors.INVALID_CONFIRM_CODE);
    }


    private void sendEmail(UserEntity userEntity) {
        Map<String, Object> data = new HashMap<>();
        data.put("user", userEntity);

        try {
            emailUtil.sendMail(userEntity.getEmail(), "confirm", "confirm_mail", data);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    private UserEntity buildUser(SignUpForm signUpForm) {

        return UserEntity.builder()
                .firstName(signUpForm.getFirstName())
                .lastName(signUpForm.getLastName())
                .email(signUpForm.getEmail())
                .state(UserEntity.State.NOT_CONFIRMED)
                .role(UserEntity.Role.USER)
                .confirmCode(UUID.randomUUID().toString())
                .password(passwordEncoder.encode(signUpForm.getPassword()))
                .until(Instant.now().plusSeconds(Long.parseLong(confirmCodeExpirationTime)))
                .build();
    }

}
