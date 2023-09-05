package ru.itis.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.api.ProductsController;
import ru.itis.dto.*;
import ru.itis.services.FilesService;
import ru.itis.services.ProductsService;

import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class ProductsControllerImpl implements ProductsController {

    private final FilesService filesService;

    private final ProductsService productsService;

    @Override
    public ResponseEntity<ProductDto> uploadPicture(UUID id, MultipartFile multipart) {
        return ResponseEntity.ok(filesService.uploadProductsFile(id, multipart));
    }

    @Override
    public ResponseEntity<Resource> downloadPicture(UUID id) {
        Resource file = filesService.downloadProductPicture(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=\"" + file.getFilename()+"\"")
                .body(file);
    }

    @Override
    public void addProductInBasket(AddToBasketDto addToBasketDto) {
        productsService.addProductInBasket(addToBasketDto);
    }

    @Override
    public void addProductInLiked(AddToLikedDto addToLikedDto) {
        productsService.addProductInLiked(addToLikedDto);
    }

    @Override
    public ResponseEntity<ProductsPage> searchProductByName(String productName, int page) {
        return ResponseEntity.ok(productsService.searchProductByName(productName, page));
    }

    @Override
    public ResponseEntity<Set<ProductDto>> searchProductByCategory(String productCategory) {
        return ResponseEntity.ok(productsService.searchProductByCategory(productCategory));
    }

    @Override
    public ResponseEntity<Set<ProductDto>> productsWithDiscount() {
        return ResponseEntity.ok(productsService.getProductsWithDiscount());
    }

}
