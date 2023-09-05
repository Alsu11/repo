package ru.itis.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.api.ConfirmController;
import ru.itis.dto.UserDto;
import ru.itis.services.ConfirmService;

@RequiredArgsConstructor
@RestController
public class ConfirmControllerImpl implements ConfirmController {

    private final ConfirmService confirmService;

    @Override
    public ResponseEntity<UserDto> confirm(String code) {
        return ResponseEntity.ok(confirmService.confirmAccount(code));
    }
}
