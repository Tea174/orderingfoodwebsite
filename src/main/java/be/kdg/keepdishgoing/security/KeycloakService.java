package be.kdg.keepdishgoing.security;

import jakarta.annotation.PreDestroy;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpHeaders;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class KeycloakService {

    private final Keycloak keycloak;
    private final String realm;
    private final String keycloakUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    public KeycloakService(@Value("${keycloak.auth-server-url}") String keycloakUrl,
                           @Value("${keycloak.realm}") String realm,
                           @Value("${keycloak.admin.username}") String adminUsername,
                           @Value("${keycloak.admin.password}") String adminPassword) {
        this.keycloakUrl = keycloakUrl;
        this.realm = realm;
        this.keycloak = KeycloakBuilder.builder()
                .serverUrl(keycloakUrl)
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
            // Check if user exists first
            List<UserRepresentation> existingUsers = keycloak.realm(realm)
                    .users()
                    .search(email, true);

            if (!existingUsers.isEmpty()) {
                throw new RuntimeException("User with email " + email + " already exists");
            }

            // Create user with minimal info (only email/username)
            UserRepresentation user = new UserRepresentation();
            user.setEnabled(true);
            user.setUsername(email);
            user.setEmail(email);
            user.setEmailVerified(true);
            user.setRequiredActions(Collections.emptyList());

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
    public KeycloakTokenResponse authenticate(String email, String password) {
        String tokenUrl = keycloakUrl + "/realms/" + realm + "/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", "keepdishgoing-client");
        body.add("username", email);
        body.add("password", password);
        body.add("grant_type", "password");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, request, Map.class);
            Map<String, Object> responseBody = response.getBody();

            return new KeycloakTokenResponse(
                    (String) responseBody.get("access_token"),
                    (String) responseBody.get("refresh_token"),
                    (Integer) responseBody.get("expires_in")
            );
        } catch (Exception e) {
            throw new RuntimeException("Authentication failed: " + e.getMessage(), e);
        }
    }


    public record KeycloakTokenResponse(String accessToken, String refreshToken, Integer expiresIn) {}



    @PreDestroy
    public void cleanup() {
        if (keycloak != null) {
            keycloak.close();
        }
    }


}