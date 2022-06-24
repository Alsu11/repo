package ru.itis.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.dto.SignUpForm;
import ru.itis.dto.UserDto;
import ru.itis.services.UsersService;

import javax.validation.Valid;

@Tag(name = "Пользователь", description = "Контроллер, связанный с пользоваетлями")
@RequiredArgsConstructor
@RestController
public class UsersController {

    private final UsersService usersService;

    @Operation(summary = "Регистрация нового пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь зарегестрирован",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = UserDto.class)
                            )
                    }
            )
    })
    @PostMapping(value = "/sign-up")
    public UserDto signUp(@Valid @RequestBody SignUpForm signUpForm) {
        return usersService.signUp(signUpForm);
    }
}
