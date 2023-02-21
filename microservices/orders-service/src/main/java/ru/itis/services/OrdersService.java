package ru.itis.services;

import ru.itis.dto.CardDto;
import ru.itis.dto.OrderDto;
import ru.itis.dto.ShopAddressDto;

import java.util.UUID;

public interface OrdersService {
    OrderDto save(UUID id, CardDto cardDto, ShopAddressDto shopAddressDto);
}
