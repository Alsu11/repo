package ru.itis.usersservice.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.itis.usersservice.dto.PasswordDto;

import javax.validation.Valid;
import java.security.Principal;

public interface PasswordChangeApi {
    @Operation(summary = "Получение токена подтверждения смены пароля на почту")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Токен отправлен")
    })
    @PostMapping("/password-change")
    @ResponseStatus(HttpStatus.OK)
    void passwordChangeToken(@Valid @RequestBody PasswordDto passwordDto, Principal userPrincipal);

    @Operation(summary = "Подтверждение смены пароля")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пароль успешно изменен")
    })
    @GetMapping("/password-change/{confirm-code}")
    @ResponseStatus(HttpStatus.OK)
    void passwordChange(@Parameter(description = "Код подтверждения")
                        @PathVariable("confirm-code") String confirmCode,
                        Principal userPrincipal);
}
