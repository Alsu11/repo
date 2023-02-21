package ru.itis.usersservice.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.usersservice.api.SupportController;
import ru.itis.usersservice.dto.SupportDto;
import ru.itis.usersservice.services.SupportService;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class SupportControllerImpl implements SupportController {

    private final SupportService supportService;

    @Override
    public ResponseEntity<SupportDto> addComment(UUID userId, SupportDto supportDto) {
        return ResponseEntity.ok(supportService.addComment(userId, supportDto));
    }
}
