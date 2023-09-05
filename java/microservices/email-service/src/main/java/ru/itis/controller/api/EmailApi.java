package ru.itis.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.itis.dto.EmailDto;

@RequestMapping("/email-sender")
public interface EmailApi {

    @Operation(summary = "Отправка письма")
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    void sendEmail(@RequestBody EmailDto emailDto);
}
