package ru.itis.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.dao.jpa.ProductsRepository;
import ru.itis.dao.jpa.UsersRepository;
import ru.itis.dao.mongo.FilesRepository;
import ru.itis.dto.ProductDto;
import ru.itis.dto.UserDto;
import ru.itis.exceptions.Errors;
import ru.itis.exceptions.FilesException;
import ru.itis.exceptions.NotFoundException;
import ru.itis.models.FileInfoEntity;
import ru.itis.models.ProductEntity;
import ru.itis.models.UserEntity;
import ru.itis.services.FilesService;
import ru.itis.utils.mappers.FilesMapper;
import ru.itis.utils.mappers.ProductsMapper;
import ru.itis.utils.mappers.UsersMapper;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FilesServiceImpl implements FilesService {

    private final String TYPE_JPEG = "jpg";
    private final String TYPE_PNG = "png";

    private final UsersRepository usersRepository;
    private final ProductsRepository productsRepository;
    private final FilesRepository filesRepository;
    private final UsersMapper usersMapper;
    private final ProductsMapper productsMapper;
    private final FilesMapper filesMapper;

    @Transactional
    @Override
    public UserDto uploadUsersFile(UUID userId, MultipartFile multipart) {
        FileInfoEntity file = uploadFileToDb(multipart);

        UserEntity user = getUserFromDb(userId);
        user.setAvatar(file.getId());

        return usersMapper.toResponse(
                usersRepository
                        .save(user));
    }

    @Transactional
    @Override
    public ProductDto uploadProductsFile(UUID productId, MultipartFile multipart) {
        FileInfoEntity file = uploadFileToDb(multipart);

        ProductEntity product = getProductFromDb(productId);
        product.setPicture(file.getId());

        return productsMapper.toResponse(
                productsRepository
                        .save(product));
    }

    @Override
    public Resource downloadUserAvatar(UUID id) {
        UserEntity user = getUserFromDb(id);
        return downloadFile(user.getAvatar());
    }

    @Override
    public Resource downloadProductPicture(UUID id) {
        ProductEntity product = getProductFromDb(id);
        return downloadFile(product.getPicture());
    }

    public Resource downloadFile(UUID id) {
        if(Objects.isNull(id)) {
            throw new FilesException(Errors.NO_FILE);
        }

        FileInfoEntity file = getFileFromDb(id);
        Resource resource = new ByteArrayResource(file.getBinary().getData());
        if(resource.exists() || resource.isReadable()){
            return resource;
        } else {
            throw new FilesException(Errors.INVALID_FILE);
        }
    }

    private FileInfoEntity uploadFileToDb(MultipartFile multipart) {
        try {
            String type= Objects.requireNonNull(multipart.getOriginalFilename()).split("\\.")[1];

            if(type.equals(TYPE_JPEG) || type.equals(TYPE_PNG)) {
                FileInfoEntity file = filesMapper.toRequest(multipart);
                file.setId(UUID.randomUUID());

                return filesRepository.save(file);

            } else {
                throw new FilesException(Errors.INVALID_FILE_TYPE);
            }
        } catch (IOException e) {
            throw new FilesException(Errors.INVALID_FILE);
        }
    }


    private UserEntity getUserFromDb(UUID id) {
        return usersRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Errors.NOT_FOUND));
    }

    private ProductEntity getProductFromDb(UUID id) {
        return productsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Errors.NOT_FOUND));
    }

    private FileInfoEntity getFileFromDb(UUID id) {
        return filesRepository.findById(id)
                .orElseThrow(() -> new FilesException(Errors.NOT_FOUND));
    }
}
