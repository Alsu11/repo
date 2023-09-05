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

@RequestMapping("/products")
public interface ProductsController {

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
    @PutMapping("/{product-id}/upload")
    ResponseEntity<ProductDto> uploadPicture(@PathVariable("product-id") UUID id, @RequestParam("file") MultipartFile multipart);


    @Operation(summary = "Выгрузка изображения продукта")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Изображение в байтах")
            , @ApiResponse(responseCode = "404", description = "Продукт с таким идентификатором не найден")
            , @ApiResponse(responseCode = "470", description = "Файл отсутствует")
            , @ApiResponse(responseCode = "472" ,description = "Файл недействителен")
    })
    @GetMapping("/{product-id}/download")
    ResponseEntity<Resource> downloadPicture(@PathVariable("product-id") UUID id);


    @Operation(summary = "Добавление продукта в корзину")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Продукт успешно добавлен")
            , @ApiResponse(responseCode = "404", description = "Пользователь с таким идентификатором не найден")
            , @ApiResponse(responseCode = "492", description = "Продукт с таким идентификатором не найден")
    })
    @PutMapping("/add_to_basket")
    void addProductInBasket(@Valid @RequestBody AddToBasketDto addToBasketDto);

    @Operation(summary = "Добавление продукта в избранное")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Продукт успешно добавлен")
            , @ApiResponse(responseCode = "404", description = "Пользователь с таким идентификатором не найден")
            , @ApiResponse(responseCode = "492", description = "Продукт с таким идентификатором не найден")
    })
    @PutMapping("/add_to_liked")
    void addProductInLiked(@Valid @RequestBody AddToLikedDto addToLikedDto);

    @Operation(summary = "Получение всех товаров по названию с пагинацией")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Страница с товарами",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = ProductsPage.class)
                            )
                    })
    })
    @GetMapping("/search/name/{product-name}")
    ResponseEntity<ProductsPage> searchProductByName(@PathVariable("product-name") String productName, @RequestParam("page") int page);

    @Operation(summary = "Получение всех товаров по заданной категории")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Страница с товарами",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = ProductDto.class)
                            )
                    })
    })
    @GetMapping("/search/category/{product-category}")
    ResponseEntity<Set<ProductDto>> searchProductByCategory(@PathVariable("product-category") String productCategory);

    @Operation(summary = "Получение всех товаров со скидками")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Страница с товарами",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = ProductDto.class)
                            )
                    })
    })
    @GetMapping("/with-discount")
    ResponseEntity<Set<ProductDto>> productsWithDiscount();

}
