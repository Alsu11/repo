package ru.itis.services;

import ru.itis.dto.OrderDto;
import ru.itis.dto.ProductDto;
import ru.itis.dto.UserDto;
import ru.itis.models.Product;

import java.util.List;

public interface ShopService {
    List<OrderDto> findAllOrdersByUserId(Long userId);
    List<UserDto> findAllUsersByFirstName(String firstName);
    ProductDto save(Product product);
    ProductDto findAllByOrderId(Long productId);
}
