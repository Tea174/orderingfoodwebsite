package be.kdg.keepdishgoing.restaurants.adapter.in.response.owner;

public record OwnerLoginResponse(String accessToken, String refreshToken, Integer expiresIn) {}