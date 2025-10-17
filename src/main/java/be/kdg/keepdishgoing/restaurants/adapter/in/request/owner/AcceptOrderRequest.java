package be.kdg.keepdishgoing.restaurants.adapter.in.request.owner;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record AcceptOrderRequest(
        @Min(value = 5, message = "Preparation time must be at least 5 minutes")
        @Max(value = 120, message = "Preparation time cannot exceed 120 minutes")
        Integer preparationTimeMinutes  // Optional field
) {
    public AcceptOrderRequest {
        // Default to 20 minutes if not provided
        if (preparationTimeMinutes == null) {
            preparationTimeMinutes = 20;
        }
    }
}