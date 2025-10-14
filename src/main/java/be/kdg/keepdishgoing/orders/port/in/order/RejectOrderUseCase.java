package be.kdg.keepdishgoing.orders.port.in.order;

import be.kdg.keepdishgoing.orders.domain.order.OrderId;

import java.util.UUID;

public interface RejectOrderUseCase {
    void rejectOrder(RejectOrderCommand command);

    record RejectOrderCommand(OrderId orderId, UUID restaurantId, String reason) {}

    enum RejectionReason {
        INGREDIENTS_UNAVAILABLE("Ingredients not available"),
        RESTAURANT_BUSY("RestaurantProjectorRecord is too busy"),
        KITCHEN_CLOSED("Kitchen is closed"),
        ITEM_OUT_OF_STOCK("Item out of stock"),
        DELIVERY_UNAVAILABLE("Delivery unavailable to this area"),
        OTHER("Other reason");

        public final String description;
        RejectionReason(String description) {
            this.description = description;
        }
    }
}