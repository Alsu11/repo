package ru.itis.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.dao.OrderRepository;
import ru.itis.dao.ProductRepository;
import ru.itis.dao.UserRepository;
import ru.itis.dto.OrderDto;
import ru.itis.dto.ProductDto;
import ru.itis.dto.UserDto;
import ru.itis.models.Product;

import java.util.List;
import java.util.Optional;

import static ru.itis.dto.OrderDto.from;
import static ru.itis.dto.UserDto.from;
import static ru.itis.dto.ProductDto.from;

@RequiredArgsConstructor
@Service
public class ShopServiceImpl implements ShopService {

    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    @Override
    public List<OrderDto> findAllOrdersByUserId(Long userId) {
        return from(orderRepository.findAllByUserId(userId));
    }

    @Override
    public List<UserDto> findAllUsersByFirstName(String firstName) {
        return from(userRepository.findAllByFirstNameLike(firstName));
    }

    @Override
    public ProductDto save(Product product) {
        return from(productRepository.save(product));
    }

    @Override
    public ProductDto findAllByOrderId(Long productId) {
        Optional<Product> optionalProductDto = productRepository.findById(productId);
        if (optionalProductDto.isPresent()) {
            return from(optionalProductDto.get());
        }
        else return ProductDto.builder().build();
    }
}
