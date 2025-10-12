package be.kdg.keepdishgoing.orders.adapter.in.basket.request;

public record GuestCheckoutRequest(
        String name,
        String email,
        String deliveryAddress
) {}