package ru.itis.services;

import ru.itis.dto.*;

import java.util.Set;
import java.util.UUID;

public interface UsersService {
    UserDto signUp(SignUpForm signUpForm);
    Set<OrderDto> getOrders(UUID id);
    CardDto addCard(UUID userId, AddCardDto addCardDto);
}
