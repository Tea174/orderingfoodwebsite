package be.kdg.keepdishgoing.orders.port.in.basket;

import be.kdg.keepdishgoing.orders.domain.order.OrderId;

import java.util.UUID;

public interface CheckoutBasketUseCase {
    // Registered customer checkout
    OrderId checkout(UUID customerId);

    // Guest customer checkout
    OrderId checkoutAsGuest(UUID basketId, GuestCheckoutDetails guestDetails);

    record GuestCheckoutDetails(
            String name,
            String email,
            String deliveryAddress
    ) {
        public GuestCheckoutDetails {
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("Name is required");
            }
            if (email == null || email.isBlank()) {
                throw new IllegalArgumentException("Email is required");
            }
            if (deliveryAddress == null || deliveryAddress.isBlank()) {
                throw new IllegalArgumentException("Delivery address is required");
            }
        }
    }
}
