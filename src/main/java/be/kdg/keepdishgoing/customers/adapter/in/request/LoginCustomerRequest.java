package be.kdg.keepdishgoing.customers.adapter.in.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginCustomerRequest(@NotBlank @Email String email, @NotBlank String password)  {
}
