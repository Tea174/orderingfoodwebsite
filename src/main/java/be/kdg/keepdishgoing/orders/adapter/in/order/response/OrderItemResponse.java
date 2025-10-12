package be.kdg.keepdishgoing.orders.adapter.in.order.response;

import be.kdg.keepdishgoing.orders.domain.order.OrderItem;

import java.util.UUID;

public record OrderItemResponse(
        UUID dishId,
        String dishName,
        Double price,
        int quantity,
        Double subtotal
) {
    public static OrderItemResponse fromDomain(OrderItem item) {
        return new OrderItemResponse(
                item.getDishId(),
                item.getDishName(),
                item.getPrice(),
                item.getQuantity(),
                item.getSubtotal()
        );
    }
}