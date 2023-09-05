package ru.itis.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.api.SupportController;
import ru.itis.dto.SupportDto;
import ru.itis.services.SupportService;

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
