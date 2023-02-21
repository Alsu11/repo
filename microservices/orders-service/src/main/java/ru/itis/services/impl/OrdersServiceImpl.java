package ru.itis.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.dao.BasketRepository;
import ru.itis.dao.OrdersRepository;
import ru.itis.dao.ProductsRepository;
import ru.itis.dao.UsersRepository;
import ru.itis.dto.CardDto;
import ru.itis.dto.OrderDto;
import ru.itis.dto.ShopAddressDto;
import ru.itis.exceptions.Errors;
import ru.itis.exceptions.OrdersException;
import ru.itis.exceptions.ProductsException;
import ru.itis.mappers.OrdersMapper;
import ru.itis.mappers.ShopAddressMapper;
import ru.itis.models.BasketEntity;
import ru.itis.models.OrderEntity;
import ru.itis.models.ProductEntity;
import ru.itis.services.OrdersService;

import java.util.*;

@RequiredArgsConstructor
@Service
public class OrdersServiceImpl implements OrdersService {

    private final OrdersRepository ordersRepository;
    private final BasketRepository basketRepository;
    private final ProductsRepository productsRepository;
    private final UsersRepository usersRepository;
    private final ShopAddressMapper shopAddressMapper;
    private final OrdersMapper ordersMapper;

    @Override
    public OrderDto save(UUID id, CardDto cardDto, ShopAddressDto shopAddressDto) {
        OrderEntity orderEntity = OrderEntity.builder()
                .amount(amountOrder(id))
                .user(usersRepository.findById(id).orElseThrow(() -> new OrdersException(Errors.USER_NOT_FOUND)))
                .shopAddress(shopAddressMapper.toRequest(shopAddressDto))
                .products(getProductsFromBasket(id))
                .build();
        return ordersMapper.toResponse(ordersRepository.save(orderEntity));
    }

    public Integer amountOrder(UUID id) {
        List<BasketEntity> products = basketRepository.findAllByUserId(id);
        Integer count = 0;
        for(BasketEntity basketEntity : products) {
            count += productsRepository.findById(basketEntity.getProductId()).get().getPrice();
            basketRepository.delete(basketEntity);
        }
        return count;
    }

    public Set<ProductEntity> getProductsFromBasket(UUID id) {
        List<BasketEntity> products = basketRepository.findAllByUserId(id);
        Set<ProductEntity> productEntities = new HashSet<>();
        for(BasketEntity basketEntity : products) {
            productEntities.add(productsRepository.findById(basketEntity.getProductId()).orElseThrow(() -> new ProductsException(Errors.PRODUCT_DOES_NOT_EXIST)));
        }
        return productEntities;
    }
}
