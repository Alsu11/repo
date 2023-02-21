package ru.itis.services;

import ru.itis.dto.OrderDto;
import ru.itis.dto.UserDto;

import java.util.Set;
import java.util.UUID;

public interface UsersService {
    Set<OrderDto> getOrders(UUID id);
}
