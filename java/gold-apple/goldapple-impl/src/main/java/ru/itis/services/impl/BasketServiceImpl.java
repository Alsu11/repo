package ru.itis.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.dao.jpa.BasketRepository;
import ru.itis.dao.jpa.ProductsRepository;
import ru.itis.dao.jpa.UsersRepository;
import ru.itis.dto.ProductCollectionDto;
import ru.itis.dto.ProductDto;
import ru.itis.exceptions.Errors;
import ru.itis.exceptions.ProductsException;
import ru.itis.exceptions.UsersException;
import ru.itis.models.BasketEntity;
import ru.itis.models.ProductEntity;
import ru.itis.services.BasketService;
import ru.itis.utils.mappers.ProductsMapper;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BasketServiceImpl implements BasketService {

    private final BasketRepository basketRepository;
    private final ProductsRepository productsRepository;
    private final ProductsMapper productsMapper;
    private final UsersRepository usersRepository;

    @Override
    public ProductCollectionDto getBasket(UUID userId) {
        List<BasketEntity> basketEntityList = basketRepository.findAllByUserId(userId);

        List<ProductDto> productDtoList = new ArrayList<>();
        for(BasketEntity entity : basketEntityList) {
            ProductDto productDto = productsMapper.toResponse(getProductFromDb(entity.getProductId()));
            productDtoList.add(productDto);
        }
        return ProductCollectionDto.builder()
                .productsDto(productDtoList)
                .build();
    }

    @Override
    public void addProduct(UUID productId, UUID userId) {

        usersRepository.findById(userId).orElseThrow(()-> new UsersException(Errors.USER_NOT_FOUND));
        getProductFromDb(productId);

        BasketEntity basketEntity = BasketEntity.builder()
                .createDate(Instant.now())
                .productId(productId)
                .userId(userId)
                .build();

        basketRepository.save(basketEntity);

    }

    @Override
    public void deleteProduct(UUID productId, UUID userId) {
        basketRepository.deleteByUserIdAndProductId(userId, productId);
    }

    private ProductEntity getProductFromDb(UUID uuid) {
        return productsRepository.findById(uuid)
                .orElseThrow(() -> new ProductsException(Errors.PRODUCT_DOES_NOT_EXIST));
    }
}
