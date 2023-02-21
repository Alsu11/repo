package ru.itis.usersservice.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.usersservice.dao.jpa.LikedRepository;
import ru.itis.usersservice.dao.jpa.ProductsRepository;
import ru.itis.usersservice.dao.jpa.UsersRepository;
import ru.itis.usersservice.dto.ProductCollectionDto;
import ru.itis.usersservice.dto.ProductDto;
import ru.itis.usersservice.exceptions.Errors;
import ru.itis.usersservice.exceptions.ProductsException;
import ru.itis.usersservice.exceptions.UsersException;
import ru.itis.usersservice.models.LikedEntity;
import ru.itis.usersservice.models.ProductEntity;
import ru.itis.usersservice.services.LikedService;
import ru.itis.usersservice.utils.mappers.ProductsMapper;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class LikedServiceImpl implements LikedService {

    private final ProductsRepository productsRepository;
    private final ProductsMapper productsMapper;
    private final UsersRepository usersRepository;
    private final LikedRepository likedRepository;

    @Override
    public ProductCollectionDto getAllProductsInLiked(UUID userId) {
        log.info("Getting all products in list");

        List<LikedEntity> likedEntityList = likedRepository.findAllByUserId(userId);

        List<ProductDto> productDtoList = new ArrayList<>();

        for(LikedEntity entity : likedEntityList) {
            ProductDto productDto = productsMapper.toResponse(getProductFromDb(entity.getProductId()));
            productDtoList.add(productDto);
        }

        return ProductCollectionDto.builder()
                .productsDto(productDtoList)
                .build();
    }

    @Transactional
    @Override
    public void addProduct(UUID productId, UUID userId) {
        log.info("Adding product to liked");
        usersRepository.findById(userId)
                .orElseThrow(()-> new UsersException(Errors.USER_NOT_FOUND));

        getProductFromDb(productId);

        LikedEntity likedEntity = LikedEntity.builder()
                .createDate(Instant.now())
                .productId(productId)
                .userId(userId)
                .build();

        likedRepository.save(likedEntity);
    }

    @Transactional
    @Override
    public void deleteProduct(UUID productId, UUID userId) {
        log.info("Deletion from db liked");
        likedRepository.deleteByUserIdAndProductId(userId, productId);
    }

    private ProductEntity getProductFromDb(UUID uuid) {
        log.info("Looking for product by id");
        return productsRepository.findById(uuid)
                .orElseThrow(() -> new ProductsException(Errors.PRODUCT_DOES_NOT_EXIST));
    }
}
