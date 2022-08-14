package ru.itis.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.api.SignUpController;
import ru.itis.dto.SignUpForm;
import ru.itis.dto.UserDto;
import ru.itis.services.UsersService;

@RequiredArgsConstructor
@RestController
public class SignUpControllerImpl implements SignUpController {

    private final UsersService usersService;

    @Override
    public ResponseEntity<UserDto> signUp(SignUpForm signUpForm) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usersService.signUp(signUpForm));
    }
}
