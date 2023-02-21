package ru.itis.usersservice.services;

import ru.itis.usersservice.dto.AddCardDto;

import java.util.UUID;

public interface CardsService {

    String addCard(UUID userId, AddCardDto addCardDto);
}
