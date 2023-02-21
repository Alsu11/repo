package ru.itis.filesservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.filesservice.dto.ProductDto;
import ru.itis.filesservice.dto.UserDto;
import ru.itis.filesservice.services.FilesService;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@Slf4j
public class FilesController {

    private final FilesService filesService;

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
    @PutMapping("/users/{user-id}/upload")
    ResponseEntity<UserDto> uploadAvatar(@PathVariable("user-id") UUID id, @RequestParam("file") MultipartFile multipart) {
        log.info("Upload avatar");
        return ResponseEntity.ok(filesService.uploadUsersFile(id, multipart));
    }


    @Operation(summary = "Выгрузка аватара пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Изображение в байтах")
            , @ApiResponse(responseCode = "404", description = "Пользователь с таким идентификатором не найден")
            , @ApiResponse(responseCode = "470", description = "Файл отсутствует")
            , @ApiResponse(responseCode = "472" ,description = "Файл недействителен")
    })
    @GetMapping("/users/{user-id}/download")
    ResponseEntity<Resource> downloadAvatar(@PathVariable("user-id") UUID id) {
        log.info("Download avatar");
        Resource file = filesService.downloadUserAvatar(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=\"" + file.getFilename()+"\"")
                .body(file);
    }

    @Operation(summary = "Загрузка изображения продукта")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация о продукте",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = ProductDto.class)
                            )
                    })
            , @ApiResponse(responseCode = "404", description = "Продукт с таким идентификатором не найден")
            , @ApiResponse(responseCode = "471", description = "Недопустимый тип файла, тип должен быть либо png, либо jpeg")
            , @ApiResponse(responseCode = "472" ,description = "Файл недействителен")
    })
    @PutMapping("/products/{product-id}/upload")
    ResponseEntity<ProductDto> uploadPicture(@PathVariable("product-id") UUID id, @RequestParam("file") MultipartFile multipart) {
        return ResponseEntity.ok(filesService.uploadProductsFile(id, multipart));
    }


    @Operation(summary = "Выгрузка изображения продукта")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Изображение в байтах")
            , @ApiResponse(responseCode = "404", description = "Продукт с таким идентификатором не найден")
            , @ApiResponse(responseCode = "470", description = "Файл отсутствует")
            , @ApiResponse(responseCode = "472" ,description = "Файл недействителен")
    })
    @GetMapping("/products/{product-id}/download")
    ResponseEntity<Resource> downloadPicture(@PathVariable("product-id") UUID id) {
        Resource file = filesService.downloadProductPicture(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=\"" + file.getFilename()+"\"")
                .body(file);
    }
}
