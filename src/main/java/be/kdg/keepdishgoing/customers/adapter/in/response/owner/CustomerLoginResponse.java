package be.kdg.keepdishgoing.customers.adapter.in.response.owner;

public record CustomerLoginResponse(String accessToken, String refreshToken, Integer expiresIn) {

}
