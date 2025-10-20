package be.kdg.keepdishgoing.orders.port.in.basket;

import be.kdg.keepdishgoing.orders.domain.order.OrderId;

import java.util.UUID;

public interface CheckoutBasketUseCase {
    // Registered customer checkout
    OrderId checkout(UUID customerId);

    // Guest customer checkout
    OrderId checkoutAsGuest(UUID basketId, GuestCheckoutDetails guestDetails);

    public record GuestCheckoutDetails(
            String name,
            String email,
            String phone,
            String deliveryAddress
    ) {
        public GuestCheckoutDetails {
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("Guest name is required");
            }
            if (email == null || email.isBlank()) {
                throw new IllegalArgumentException("Guest email is required");
            }
            if (phone == null ) {
                throw new IllegalArgumentException("Guest phone is required");
            }
        }
    }
}
