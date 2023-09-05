package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.models.Order;
import ru.itis.models.Product;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    private Long id;
    private String amount;
    private String userName;
    private Long userId;

    public static OrderDto from(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .amount(order.getAmount())
                .userName(order.getUser().getFirstName() + " " + order.getUser().getLastName())
                .userId(order.getUser().getId())
                .build();
    }

    public static List<OrderDto> from(List<Order> orders) {
        return orders.stream().map(OrderDto::from).collect(Collectors.toList());
    }
}

