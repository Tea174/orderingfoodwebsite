package be.kdg.keepdishgoing.orders.port.in.basket;

import java.util.UUID;

public interface ValidateBasketUseCase {
    record ValidationResult(
            boolean valid,
            String message,
            java.util.List<InvalidItem> invalidItems
    ) {}

    record InvalidItem(
            UUID dishId,
            String dishName,
            String reason  // "OUT_OF_STOCK" or "UNPUBLISHED"
    ) {}

    ValidationResult validateBasket(UUID customerId);
}