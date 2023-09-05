package ru.itis.usersservice.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.usersservice.dao.jpa.UsersRepository;
import ru.itis.usersservice.dto.SignUpForm;
import ru.itis.usersservice.dto.UserDto;
import ru.itis.usersservice.dto.enums.Role;
import ru.itis.usersservice.dto.enums.State;
import ru.itis.usersservice.exceptions.Errors;
import ru.itis.usersservice.exceptions.SignUpException;
import ru.itis.usersservice.models.UserEntity;
import ru.itis.usersservice.services.UsersService;
import ru.itis.usersservice.utils.mappers.UsersMapper;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final UsersMapper usersMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public UserDto signUp(SignUpForm signUpForm) {

        if (usersRepository.findUserEntityByEmail(signUpForm.getEmail()).isPresent()) {
            throw new SignUpException(Errors.EMAIL_ALREADY_TAKEN);
        }

        log.info("Registration user");

        UserEntity user = usersMapper.toRequest(signUpForm);
        user.setState(State.NOT_CONFIRMED);
        user.setPassword(passwordEncoder.encode(signUpForm.getPassword()));
        user.setRole(Role.USER);

        log.info(user.toString());

        usersRepository.save(user);

        return usersMapper.toResponse(user);
    }

}
