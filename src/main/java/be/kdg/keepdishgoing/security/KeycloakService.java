package be.kdg.keepdishgoing.security;

import jakarta.annotation.PreDestroy;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class KeycloakService {

    private final Keycloak keycloak;
    private final String realm;

    public KeycloakService(@Value("${keycloak.auth-server-url}") String serverUrl,
                           @Value("${keycloak.realm}") String realm,
                           @Value("${keycloak.admin.username}") String adminUsername,
                           @Value("${keycloak.admin.password}") String adminPassword) {
        this.realm = realm;
        this.keycloak = KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm("master")
                .username(adminUsername)
                .password(adminPassword)
                .clientId("admin-cli")
                .build();
    }

    // Create owner - only saves email & password in Keycloak
    public String createOwner(String email, String password) {
        return createUserWithRole(email, password, "owner");
    }

    // Create customer - only saves email & password in Keycloak
    public String createCustomer(String email, String password) {
        return createUserWithRole(email, password, "customer");
    }

    private String createUserWithRole(String email, String password, String roleName) {
        try {
            // Create user with minimal info (only email/username)
            UserRepresentation user = new UserRepresentation();
            user.setEnabled(true);
            user.setUsername(email);
            user.setEmail(email);
            user.setEmailVerified(true);
            // NO firstName/lastName - those are in PostgreSQL

            // Set password
            CredentialRepresentation credential = new CredentialRepresentation();
            credential.setType(CredentialRepresentation.PASSWORD);
            credential.setValue(password);
            credential.setTemporary(false);
            user.setCredentials(Collections.singletonList(credential));

            // Create user
            var response = keycloak.realm(realm).users().create(user);

            if (response.getStatus() != 201) {
                throw new RuntimeException("Failed to create user: " + response.getStatusInfo());
            }

            // Extract Keycloak subject ID from Location header
            String locationHeader = response.getLocation().getPath();
            String userId = locationHeader.substring(locationHeader.lastIndexOf('/') + 1);

            // Assign role
            assignRole(userId, roleName);

            return userId; // Return Keycloak subject ID to save in PostgreSQL

        } catch (Exception e) {
            throw new RuntimeException("Error creating user in Keycloak", e);
        }
    }

    private void assignRole(String userId, String roleName) {
        RoleRepresentation role = keycloak.realm(realm).roles().get(roleName).toRepresentation();
        keycloak.realm(realm).users().get(userId).roles().realmLevel().add(List.of(role));
    }

    @PreDestroy
    public void cleanup() {
        if (keycloak != null) {
            keycloak.close();
        }
    }
}