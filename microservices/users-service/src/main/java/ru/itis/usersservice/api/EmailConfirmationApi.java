package ru.itis.usersservice.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.security.Principal;

@RequestMapping("/email")
public interface EmailConfirmationApi {

    @Operation(summary = "Подтверждение почты")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Почта успешно подтверждена")
    })
    @GetMapping("/confirmation/{confirm-code}")
    @ResponseStatus(HttpStatus.OK)
    Boolean emailConfirmation(@Parameter(description = "Код подтверждения")
                              @PathVariable("confirm-code") String confirmCode,
                              Principal userPrincipal);

    @Operation(summary = "Получение токена подтверждения почты на почту")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Токен успешно отправлен")
    })
    @GetMapping("/confirmation")
    @ResponseStatus(HttpStatus.OK)
    void confirmationToken(Principal userPrincipal);
}
