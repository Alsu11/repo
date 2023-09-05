package ru.itis.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.dao.OrdersRepository;
import ru.itis.dto.OrderDto;
import ru.itis.exceptions.Errors;
import ru.itis.exceptions.OrdersException;
import ru.itis.mappers.OrdersMapper;
import ru.itis.models.OrderEntity;
import ru.itis.services.UsersService;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UsersServiceImpl implements UsersService {

    private final OrdersRepository ordersRepository;
    private final OrdersMapper ordersMapper;

    @Override
    public Set<OrderDto> getOrders(UUID id) {
        Set<OrderEntity> ordersEntity = ordersRepository.findAllByUser(id)
                .orElseThrow(() -> new OrdersException(Errors.ORDERS_DOES_NOT_EXIST));
        Set<OrderDto> ordersDtoSet = new HashSet<>();

        for (OrderEntity orderEntity : ordersEntity) {
            OrderDto orderDto = ordersMapper.toResponse(orderEntity);
            ordersDtoSet.add(orderDto);
        }
        return ordersDtoSet;
    }

}
