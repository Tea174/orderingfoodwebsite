package be.kdg.keepdishgoing.owners.port.in.owner;

import be.kdg.keepdishgoing.owners.domain.owner.OwnerId;
import org.springframework.util.Assert;

public interface RegisterOwnerUseCase {
    OwnerId register(RegisterOwnerCommand command);

    record RegisterOwnerCommand(
            String keycloakSubjectId,  // ← Add this
            String email,
            String firstName,
            String lastName,
            int phoneNumber,
            String address
    ) {
        public RegisterOwnerCommand {
            Assert.hasText(keycloakSubjectId, "Keycloak subject ID required");
            Assert.hasText(email, "Email required");
        }
    }
}