package ru.itis.usersservice.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itis.usersservice.dao.jpa.CardsRepository;
import ru.itis.usersservice.dao.jpa.UsersRepository;
import ru.itis.usersservice.dto.AddCardDto;
import ru.itis.usersservice.exceptions.Errors;
import ru.itis.usersservice.exceptions.UsersException;
import ru.itis.usersservice.models.CardEntity;
import ru.itis.usersservice.models.UserEntity;
import ru.itis.usersservice.services.CardsService;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class CardsServiceImpl implements CardsService {

    private final CardsRepository cardsRepository;
    private final UsersRepository usersRepository;

    @Override
    public String addCard(UUID userId, AddCardDto addCardDto) {
        log.info("Try to add card");


        CardEntity cardEntity = encode(addCardDto);
        cardEntity.setUser(getUserFromDb(userId));

        cardsRepository.save(cardEntity);
        return "Card successfully added!";
    }

    private UserEntity getUserFromDb(UUID userId) {
        log.info("Looking for user by id");
        return usersRepository.findById(userId)
                .orElseThrow(() -> new UsersException(Errors.USER_NOT_FOUND));
    }

    private CardEntity encode(AddCardDto addCardDto) {
        log.info("Encode card's data");
        String data = addCardDto.getCvv() + addCardDto.getNumber() + addCardDto.getExpiration();

        return CardEntity.builder()
                .data(String.valueOf(data.hashCode()))
                .build();
    }
}
