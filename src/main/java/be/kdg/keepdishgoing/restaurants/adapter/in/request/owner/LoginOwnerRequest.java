package be.kdg.keepdishgoing.restaurants.adapter.in.request.owner;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginOwnerRequest(@NotBlank @Email String email, @NotBlank String password) {}