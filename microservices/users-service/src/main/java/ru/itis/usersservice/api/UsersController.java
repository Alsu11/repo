package ru.itis.usersservice.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.usersservice.dto.SignUpForm;
import ru.itis.usersservice.dto.UserDto;

import javax.validation.Valid;

public interface UsersController {

    @PostMapping("/sign-up")
    ResponseEntity<UserDto> signUp(@Valid @RequestBody SignUpForm signUpForm);

}
