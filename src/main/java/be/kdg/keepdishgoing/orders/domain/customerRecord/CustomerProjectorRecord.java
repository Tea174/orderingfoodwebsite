package be.kdg.keepdishgoing.orders.domain.customerRecord;

import java.util.UUID;

public record CustomerProjectorRecord(
        UUID customerId,
        String keycloak
) {

}
