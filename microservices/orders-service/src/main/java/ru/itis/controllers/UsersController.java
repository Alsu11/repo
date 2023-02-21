package ru.itis.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.dto.CardDto;
import ru.itis.dto.OrderDto;
import ru.itis.dto.ShopAddressDto;

import java.util.Set;
import java.util.UUID;

@RequestMapping("/users/{user-id}")
public interface UsersController {

    @Operation(summary = "Получение всех заказов пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация о пользователе",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = OrderDto.class)
                            )
                    })
            , @ApiResponse(responseCode = "404", description = "Пользователь с таким идентификатором не найден")
    })
    @GetMapping("/orders")
    ResponseEntity<Set<OrderDto>> getOrders(@PathVariable("user-id") UUID id);

    @PostMapping("/ordering")
    ResponseEntity<OrderDto> ordering(@PathVariable("user-id") UUID id, CardDto cardDto, ShopAddressDto shopAddressDto);
}
