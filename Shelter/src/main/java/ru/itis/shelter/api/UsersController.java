package ru.itis.shelter.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.shelter.dto.ConfirmDto;
import ru.itis.shelter.dto.ShelterDto;
import ru.itis.shelter.dto.SignUpForm;
import ru.itis.shelter.dto.UserDto;

import javax.validation.Valid;

public interface UsersController {

    @Operation(summary = "Регистрация пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь зарегестрирован",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = ConfirmDto.class)
                            )
                    }
            )
    })
    @PostMapping("/sign-up")
    ResponseEntity<ConfirmDto> signUp(@Valid @RequestBody SignUpForm signUpForm);


    @Operation(summary = "Подтверждение почты пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = UserDto.class)
                            )
                    }
            )
    })
    @GetMapping("/sign-up/confirm/{confirm-code}")
    ResponseEntity<UserDto> confirm(@PathVariable("confirm-code") String confirmCode);
}
