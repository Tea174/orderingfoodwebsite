package be.kdg.keepdishgoing.orders.domain.customerRecord;

import java.util.UUID;

public record CustomerProjectorRecord(
        UUID customerId,
        String keycloakId,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String address
) {
    // Helper method for full name
    public String getFullName() {
        return firstName + " " + lastName;
    }
}