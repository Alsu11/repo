package ru.itis.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.itis.dto.OrderDto;
import ru.itis.dto.ProductDto;
import ru.itis.dto.UserDto;
import ru.itis.models.Product;
import ru.itis.services.ShopService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ShopController {

    private final ShopService shopService;

    @GetMapping("/orders")
    public List<OrderDto> getByTitle(@RequestParam("userId") Long userId) {
        return shopService.findAllOrdersByUserId(userId);
    }

    @GetMapping("/users")
    public List<UserDto> getByTitle(@RequestParam("name") String firstName) {
        return shopService.findAllUsersByFirstName(firstName);
    }

    @PostMapping(value = "/products")
    public ProductDto addProduct(@RequestBody Product product) {
        return shopService.save(product);
    }

    @GetMapping("/products")
    public ProductDto getByOrder(@RequestParam("productId") Long productId) {
        return shopService.findAllByOrderId(productId);
    }

}
