package be.kdg.keepdishgoing.customers.adapter.in;


import be.kdg.keepdishgoing.customers.adapter.in.request.LoginCustomerRequest;
import be.kdg.keepdishgoing.customers.adapter.in.request.RegisterCustomerRequest;
import be.kdg.keepdishgoing.customers.adapter.in.response.owner.CustomerLoginResponse;
import be.kdg.keepdishgoing.customers.adapter.in.response.owner.CustomerRegisteredResponse;
import be.kdg.keepdishgoing.customers.domain.Customer;
import be.kdg.keepdishgoing.customers.domain.CustomerId;
import be.kdg.keepdishgoing.customers.port.in.GetCustomerUseCase;
import be.kdg.keepdishgoing.customers.port.in.RegisterCustomerUseCase;
import be.kdg.keepdishgoing.common.security.KeycloakService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
@AllArgsConstructor
public class CustomerController {
    private final KeycloakService keycloakService;
    private final RegisterCustomerUseCase registerCustomerUseCase;
    private final GetCustomerUseCase getCustomerUseCase;

    @PostMapping("/register")
    public ResponseEntity<CustomerRegisteredResponse> register(
            @Valid @RequestBody RegisterCustomerRequest request) {
        String keycloakSubjectId = keycloakService.createCustomer(
                request.email(),
                request.password()
        );
        RegisterCustomerUseCase.RegisterCustomerCommand command = new RegisterCustomerUseCase.RegisterCustomerCommand(
                keycloakSubjectId,
                request.email(),
                request.firstName(),
                request.lastName(),
                request.phoneNumber(),
                request.address()
        );

        CustomerId customerId = registerCustomerUseCase.register(command);
        Customer customer = getCustomerUseCase.getCustomerById(customerId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CustomerRegisteredResponse.fromDomain(customer));
    }

    @PostMapping("/login")
    public ResponseEntity<CustomerLoginResponse> login(@Valid @RequestBody LoginCustomerRequest request) {
           var tokenResponse = keycloakService.authenticate(request.email(), request.password());
           Customer customer = getCustomerUseCase.getCustomerByEmail(request.email());
           System.out.println(customer);
           return ResponseEntity.ok(new CustomerLoginResponse(
                   tokenResponse.accessToken(),
                   tokenResponse.refreshToken(),
                   tokenResponse.expiresIn()
           ));
    }


}
