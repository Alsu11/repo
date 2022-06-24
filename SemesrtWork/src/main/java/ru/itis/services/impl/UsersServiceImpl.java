package ru.itis.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.dao.UserRepository;
import ru.itis.dto.SignUpForm;
import ru.itis.dto.UserDto;
import ru.itis.exceptions.*;
import ru.itis.mappers.UserMapper;
import ru.itis.models.User;
import ru.itis.services.UsersService;

import javax.transaction.Transactional;
import java.time.Instant;

@RequiredArgsConstructor
@Service
public class UsersServiceImpl implements UsersService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Transactional
    @Override
    public UserDto signUp(SignUpForm signUpForm) {
        if(userRepository.findByEmail(signUpForm.getEmail()).isPresent()) {
            throw new AlreadyExistsException(ErrorEntity.EMAIL_ALREADY_TAKEN);
        }
        String password = passwordEncoder.encode(signUpForm.getPassword());

        User user = userMapper.toRequest(signUpForm);
        user.setPassword(password);
        user.setCreatedAt(Instant.now());
        user.setRole(User.Role.USER);
        user.setState(User.State.CONFIRMED);

        userRepository.save(user);

        return userMapper.toResponse(user);
    }
}
