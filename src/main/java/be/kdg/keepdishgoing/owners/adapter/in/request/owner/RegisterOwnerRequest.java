package be.kdg.keepdishgoing.owners.adapter.in.request.owner;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record RegisterOwnerRequest(
        @NotBlank(message = "First name is required")
        @Size(min = 2, max = 50)
        String firstName,

        @NotBlank(message = "Last name is required")
        @Size(min = 2, max = 50)
        String lastName,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Password must be at least 8 characters")
        String password,

        @Positive(message = "Phone number must be positive")
        int phoneNumber,

        String address
) {}