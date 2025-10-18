package be.kdg.keepdishgoing.orders.adapter.in.order.response;

import be.kdg.keepdishgoing.orders.domain.order.Order;
import be.kdg.keepdishgoing.common.commonEnum.commonOrderEnum.OrderStatus;

import java.util.List;
import java.util.UUID;

public record OrderResponse(
        UUID orderId,
        UUID customerId,
        String guestName,
        String guestEmail,
        String deliveryAddress,
        UUID restaurantId,
        Double totalPrice,
        OrderStatus status,
        String createdAt,
        List<OrderItemResponse> items
) {
    public static OrderResponse fromDomain(Order order) {
        return new OrderResponse(
                order.getOrderId().id(),
                order.getCustomerId(),
                order.getGuestName(),
                order.getGuestEmail(),
                order.getDeliveryAddress(),
                order.getRestaurantId(),
                order.getTotalPrice(),
                order.getStatus(),
                order.getCreatedAt().toString(),
                order.getItems().stream()
                        .map(OrderItemResponse::fromDomain)
                        .toList()
        );
    }
}