package ru.itis.productservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.productservice.dto.CustomPage;
import ru.itis.productservice.dto.ProductDto;

@RequestMapping("/products")
@Schema(description = "Главный контроллер сервиса, отвечает за взаимодействие с продуктами")
public interface ProductController {

    @GetMapping
    @Operation(method = "GET", description = "Получить список продуктов с пагинацией")
    ResponseEntity<CustomPage<ProductDto>> getProducts(
            @Parameter(description = "Параметр отвечающий за пагинацию. Автоматически собирается из параметров запроса (page=*&size=*)") Pageable pagination);

    @GetMapping("/name")
    @Operation(method = "GET", description = "Получить список продуктов по имени с пагинацией")
    ResponseEntity<CustomPage<ProductDto>> getProductsByName(
            @Parameter(description = "Параметр отвечающий за пагинацию. Автоматически собирается из параметров запроса (page=*&size=*)") Pageable pagination,
            @RequestParam(name = "name") String name);

    @GetMapping("/category")
    @Operation(method = "GET", description = "Получить список продуктов по категории с пагинацией")
    ResponseEntity<CustomPage<ProductDto>> getProductsByCategory(
            @Parameter(description = "Параметр отвечающий за пагинацию. Автоматически собирается из параметров запроса (page=*&size=*)") Pageable pagination,
            @RequestParam(name = "category") String category);

    @GetMapping("/discount")
    @Operation(method = "GET", description = "Получить список продуктов на которые действует скидка с пагинацией")
    ResponseEntity<CustomPage<ProductDto>> getProductsWithDiscount(
            @Parameter(description = "Параметр отвечающий за пагинацию. Автоматически собирается из параметров запроса (page=*&size=*)") Pageable pagination);

}
