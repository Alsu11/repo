package ru.itis.usersservice.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.usersservice.api.CardsController;
import ru.itis.usersservice.dto.AddCardDto;
import ru.itis.usersservice.services.CardsService;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class CardsControllerImpl implements CardsController {

    private final CardsService cardsService;

    @Override
    public ResponseEntity<String> addCard(UUID userId, AddCardDto addCardDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cardsService.addCard(userId, addCardDto));
    }
}
