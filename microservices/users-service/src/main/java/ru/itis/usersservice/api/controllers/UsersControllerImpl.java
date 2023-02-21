package ru.itis.usersservice.api.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.usersservice.api.UsersController;
import ru.itis.usersservice.dto.SignUpForm;
import ru.itis.usersservice.dto.UserDto;
import ru.itis.usersservice.services.UsersService;

@RequiredArgsConstructor
@RestController
@Slf4j
public class UsersControllerImpl implements UsersController {

    private final UsersService usersService;

    @Override
    public ResponseEntity<UserDto> signUp(SignUpForm signUpForm) {
        log.info("SIGN UP");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usersService.signUp(signUpForm));
    }

}
