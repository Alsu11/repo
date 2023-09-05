package ru.itis.filesservice.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.filesservice.dao.jpa.ProductsRepository;
import ru.itis.filesservice.dao.jpa.UsersRepository;
import ru.itis.filesservice.dao.mongo.FilesRepository;
import ru.itis.filesservice.dto.ProductDto;
import ru.itis.filesservice.dto.UserDto;
import ru.itis.filesservice.exceptions.Errors;
import ru.itis.filesservice.exceptions.FilesException;
import ru.itis.filesservice.exceptions.NotFoundException;
import ru.itis.filesservice.models.FileInfoEntity;
import ru.itis.filesservice.models.ProductEntity;
import ru.itis.filesservice.models.UserEntity;
import ru.itis.filesservice.services.FilesService;
import ru.itis.filesservice.utils.mappers.FilesMapper;
import ru.itis.filesservice.utils.mappers.ProductsMapper;
import ru.itis.filesservice.utils.mappers.UsersMapper;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
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
                        .save(user)
        );
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
            log.info("Try to upload file");
            String type= Objects.requireNonNull(multipart.getOriginalFilename()).split("\\.")[1];

            if(type.equals(TYPE_JPEG) || type.equals(TYPE_PNG)) {
                FileInfoEntity file = filesMapper.toRequest(multipart);
                file.setId(UUID.randomUUID());
                log.info(file.getId().toString());
                return filesRepository.save(file);

            } else {
                log.info("Wrong file format");
                throw new FilesException(Errors.INVALID_FILE_TYPE);
            }
        } catch (IOException e) {
            log.info("Wrong file");
            throw new FilesException(Errors.INVALID_FILE);
        }
    }

    private UserEntity getUserFromDb(UUID id) {
        log.info("Looking for user by id");
        return usersRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Errors.NOT_FOUND));
    }

    private ProductEntity getProductFromDb(UUID id) {
        log.info("Looking for product by id");
        return productsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Errors.NOT_FOUND));
    }

    private FileInfoEntity getFileFromDb(UUID id) {
        log.info("Looking for file by id");
        return filesRepository.findById(id)
                .orElseThrow(() -> new FilesException(Errors.NOT_FOUND));
    }
}
