package ru.itis.shelter.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.shelter.api.UsersController;
import ru.itis.shelter.dto.ConfirmDto;
import ru.itis.shelter.dto.SignUpForm;
import ru.itis.shelter.dto.UserDto;
import ru.itis.shelter.services.UsersService;

@RequiredArgsConstructor
@RestController
public class UsersControllerImpl implements UsersController {

    private final UsersService usersService;

    @Override
    public ResponseEntity<ConfirmDto> signUp(SignUpForm signUpForm) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usersService.signUp(signUpForm));
    }

    @Override
    public ResponseEntity<UserDto> confirm(String confirmCode) {
        return ResponseEntity.ok(usersService.confirm(confirmCode));
    }
}
