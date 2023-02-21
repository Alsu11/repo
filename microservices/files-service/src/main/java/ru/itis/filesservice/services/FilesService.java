package ru.itis.filesservice.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.filesservice.dto.ProductDto;
import ru.itis.filesservice.dto.UserDto;

import java.util.UUID;

public interface FilesService {
    UserDto uploadUsersFile(UUID id, MultipartFile multipart);

    Resource downloadUserAvatar(UUID id);

    ProductDto uploadProductsFile(UUID id, MultipartFile multipart);

    Resource downloadProductPicture(UUID id);
}
