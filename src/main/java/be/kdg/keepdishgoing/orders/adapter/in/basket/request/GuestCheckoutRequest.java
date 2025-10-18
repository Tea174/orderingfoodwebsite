package be.kdg.keepdishgoing.orders.adapter.in.basket.request;

public record GuestCheckoutRequest(
        String name,
        String email,
        Integer phone,
        String deliveryAddress
) {}