package ru.itis.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.dto.ProductDto;
import ru.itis.dto.UserDto;

import java.util.UUID;

public interface FilesService {
    UserDto uploadUsersFile(UUID userId, MultipartFile multipart);
    ProductDto uploadProductsFile(UUID productId, MultipartFile multipart);
    Resource downloadUserAvatar(UUID id);
    Resource downloadProductPicture(UUID id);
}
