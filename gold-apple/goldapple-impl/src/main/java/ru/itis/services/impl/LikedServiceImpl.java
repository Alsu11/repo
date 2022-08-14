package ru.itis.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.dao.jpa.LikedRepository;
import ru.itis.dao.jpa.ProductsRepository;
import ru.itis.dao.jpa.UsersRepository;
import ru.itis.dto.ProductCollectionDto;
import ru.itis.dto.ProductDto;
import ru.itis.exceptions.Errors;
import ru.itis.exceptions.ProductsException;
import ru.itis.exceptions.UsersException;
import ru.itis.models.LikedEntity;
import ru.itis.models.ProductEntity;
import ru.itis.services.LikedService;
import ru.itis.utils.mappers.ProductsMapper;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class LikedServiceImpl implements LikedService {

    private final ProductsRepository productsRepository;
    private final ProductsMapper productsMapper;
    private final UsersRepository usersRepository;
    private final LikedRepository likedRepository;

    @Override
    public ProductCollectionDto getAllProductsInLiked(UUID userId) {

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

    @Override
    public void addProduct(UUID productId, UUID userId) {
        usersRepository.findById(userId).orElseThrow(()-> new UsersException(Errors.USER_NOT_FOUND));
        getProductFromDb(productId);

        LikedEntity likedEntity = LikedEntity.builder()
                .createDate(Instant.now())
                .productId(productId)
                .userId(userId)
                .build();

        likedRepository.save(likedEntity);
    }

    @Override
    public void deleteProduct(UUID productId, UUID userId) {
        likedRepository.deleteByUserIdAndProductId(userId, productId);
    }

    private ProductEntity getProductFromDb(UUID uuid) {
        return productsRepository.findById(uuid)
                .orElseThrow(() -> new ProductsException(Errors.PRODUCT_DOES_NOT_EXIST));
    }
}
