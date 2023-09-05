package ru.itis.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.api.UsersController;
import ru.itis.dao.jpa.UsersRepository;
import ru.itis.dto.*;
import ru.itis.models.FileInfoEntity;
import ru.itis.services.FilesService;
import ru.itis.services.UsersService;

import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class UsersControllerImpl implements UsersController {

    private final FilesService filesService;
    private final UsersService usersService;

    @Override
    public ResponseEntity<UserDto> uploadAvatar(UUID id, MultipartFile multipart) {
        return ResponseEntity.ok(filesService.uploadUsersFile(id, multipart));
    }

    @Override
    public ResponseEntity<Resource> downloadAvatar(UUID id) {
        Resource file = filesService.downloadUserAvatar(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=\"" + file.getFilename()+"\"")
                .body(file);
    }

    @Override
    public ResponseEntity<Set<OrderDto>> getOrders(UUID id) {
        return ResponseEntity.ok(usersService.getOrders(id));
    }

    @Override
    public ResponseEntity<CardDto> addCard(UUID userId, AddCardDto addCardDto) {
        return ResponseEntity.ok(usersService.addCard(userId, addCardDto));
    }

}
