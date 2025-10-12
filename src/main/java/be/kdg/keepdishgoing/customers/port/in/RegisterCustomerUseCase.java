package be.kdg.keepdishgoing.customers.port.in;

import be.kdg.keepdishgoing.customers.domain.CustomerId;
import org.springframework.util.Assert;

public interface RegisterCustomerUseCase {
    CustomerId register(RegisterCustomerCommand command);

    record RegisterCustomerCommand(
            String keycloakSubjectId,
            String email,
            String firstName,
            String lastName,
            int phoneNumber,
            String address
    ) {
        public RegisterCustomerCommand {
            Assert.hasText(keycloakSubjectId, "Keycloak subject ID required");
        }
    }
}
