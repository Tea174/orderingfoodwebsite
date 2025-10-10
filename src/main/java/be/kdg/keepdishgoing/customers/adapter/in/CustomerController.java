package be.kdg.keepdishgoing.customers.adapter.in;


import be.kdg.keepdishgoing.security.KeycloakService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
@AllArgsConstructor
public class CustomerController {
    private final KeycloakService keycloakService;
}
