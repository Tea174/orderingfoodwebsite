package be.kdg.keepdishgoing.orders.domain.order;


import java.util.UUID;

public record OrderItemId(UUID id) {
    public static OrderItemId of(UUID id) {
        return new OrderItemId(id);
    }
    public static OrderItemId create() {
        return new OrderItemId(UUID.randomUUID());
    }
}
