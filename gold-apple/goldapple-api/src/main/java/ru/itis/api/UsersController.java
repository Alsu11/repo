package ru.itis.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.dto.*;

import javax.validation.Valid;
import java.util.Set;
import java.util.UUID;

@RequestMapping("/users/{user-id}")
public interface UsersController {
    @Operation(summary = "Загрузка аватара пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация о пользователе",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = UserDto.class)
                            )
                    })
            , @ApiResponse(responseCode = "404", description = "Пользователь с таким идентификатором не найден")
            , @ApiResponse(responseCode = "471", description = "Недопустимый тип файла, тип должен быть либо png, либо jpeg")
            , @ApiResponse(responseCode = "472" ,description = "Файл недействителен")
    })
    @PutMapping("/upload")
    ResponseEntity<UserDto> uploadAvatar(@PathVariable("user-id") UUID id, @RequestParam("file") MultipartFile multipart);

    @Operation(summary = "Выгрузка аватара пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Изображение в байтах")
            , @ApiResponse(responseCode = "404", description = "Пользователь с таким идентификатором не найден")
            , @ApiResponse(responseCode = "470", description = "Файл отсутствует")
            , @ApiResponse(responseCode = "472" ,description = "Файл недействителен")
    })
    @GetMapping("/download")
    ResponseEntity<Resource> downloadAvatar(@PathVariable("user-id") UUID id);

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

    @Operation(summary = "Добавление карты")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация о карте",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = CardDto.class)
                            )
                    })
            , @ApiResponse(responseCode = "404", description = "Пользователь с таким идентификатором не найден")
    })
    @PutMapping("/add-card")
    ResponseEntity<CardDto> addCard(@PathVariable("user-id") UUID id, @Valid @RequestBody AddCardDto addCardDto);

}
