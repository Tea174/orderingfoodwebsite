package be.kdg.keepdishgoing.owners.adapter.in.response.owner;

import be.kdg.keepdishgoing.owners.domain.owner.Owner;

import java.util.UUID;

public record OwnerLoginResponse(String accessToken, String refreshToken, Integer expiresIn) {}