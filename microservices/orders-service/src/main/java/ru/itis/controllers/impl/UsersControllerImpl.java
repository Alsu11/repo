package ru.itis.controllers.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.controllers.UsersController;
import ru.itis.dto.CardDto;
import ru.itis.dto.OrderDto;
import ru.itis.dto.ShopAddressDto;
import ru.itis.services.OrdersService;
import ru.itis.services.UsersService;

import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class UsersControllerImpl implements UsersController {

    private final UsersService usersService;
    private final OrdersService ordersService;

    @Override
    public ResponseEntity<Set<OrderDto>> getOrders(UUID id) {
        return ResponseEntity.ok(usersService.getOrders(id));
    }

    @Override
    public ResponseEntity<OrderDto> ordering(UUID id, CardDto cardDto, ShopAddressDto shopAddressDto) {
        return ResponseEntity.ok(ordersService.save(id, cardDto, shopAddressDto));
    }


}
