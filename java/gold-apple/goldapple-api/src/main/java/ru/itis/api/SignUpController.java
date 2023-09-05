package ru.itis.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.dto.SignUpForm;
import ru.itis.dto.UserDto;

import javax.validation.Valid;

@RequestMapping("/sign-up")
public interface SignUpController {

    @PostMapping
    ResponseEntity<UserDto> signUp(@Valid @RequestBody SignUpForm signUpForm);

}
