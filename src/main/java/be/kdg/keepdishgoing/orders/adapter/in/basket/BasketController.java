package be.kdg.keepdishgoing.orders.adapter.in.basket;

import be.kdg.keepdishgoing.customers.domain.Customer;
import be.kdg.keepdishgoing.customers.port.in.GetCustomerUseCase;
import be.kdg.keepdishgoing.orders.adapter.in.basket.request.AddItemRequest;
import be.kdg.keepdishgoing.orders.adapter.in.basket.request.GuestCheckoutRequest;
import be.kdg.keepdishgoing.orders.adapter.in.basket.response.CheckoutResponse;
import be.kdg.keepdishgoing.orders.domain.order.OrderId;
import be.kdg.keepdishgoing.orders.port.in.basket.AddItemToBasketUseCase;
import be.kdg.keepdishgoing.orders.port.in.basket.CheckoutBasketUseCase;
import be.kdg.keepdishgoing.orders.port.in.basket.CreateGuestBasketUseCase;
import be.kdg.keepdishgoing.orders.port.in.basket.ValidateBasketUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/baskets")
@AllArgsConstructor
public class BasketController {

    private final AddItemToBasketUseCase addItemToBasketUseCase;
    private final ValidateBasketUseCase validateBasketUseCase;
    private final CheckoutBasketUseCase checkoutBasketUseCase;
    private final CreateGuestBasketUseCase createGuestBasketUseCase;
    private final GetCustomerUseCase getCustomerUseCase; // From Restaurant BC

    // Create guest basket & add items (no auth)
    @PostMapping("/guest")
    public ResponseEntity<UUID> createGuestBasket() {
        UUID basketId = createGuestBasketUseCase.create();
        return ResponseEntity.ok(basketId);
    }

    @PostMapping("/guest/{basketId}/items")
    public ResponseEntity<String> addItemToGuestBasket(
            @PathVariable UUID basketId,
            @RequestBody AddItemRequest request) {
        addItemToBasketUseCase.addItemToGuestBasket(basketId,
                request.restaurantId(), request.dishId(), request.quantity());
        return ResponseEntity.ok("Item added");
    }

    @PostMapping("/checkout/guest/{basketId}")
    public ResponseEntity<CheckoutResponse> checkoutAsGuest(
            @PathVariable UUID basketId,
            @RequestBody GuestCheckoutRequest request) {

        OrderId orderId = checkoutBasketUseCase.checkoutAsGuest(
                basketId,
                new CheckoutBasketUseCase.GuestCheckoutDetails(
                        request.name(),
                        request.email(),
                        request.deliveryAddress()
                )
        );
        return ResponseEntity.ok(new CheckoutResponse(orderId.id()));
    }

    @PostMapping("/{customerId}/items")
    public ResponseEntity<String> addItem(
            @PathVariable UUID customerId,
            @RequestBody AddItemRequest request,
            @AuthenticationPrincipal Jwt jwt) {
        verifyCustomership(customerId, jwt);
        addItemToBasketUseCase.addItem(customerId, request.restaurantId(),
                request.dishId(), request.quantity());
        return ResponseEntity.ok("Item added");
    }

    @GetMapping("/{customerId}/validate")
    public ResponseEntity<ValidateBasketUseCase.ValidationResult> validateBasket(
            @PathVariable UUID customerId,
            @AuthenticationPrincipal Jwt jwt) {
        verifyCustomership(customerId, jwt);
        return ResponseEntity.ok(validateBasketUseCase.validateBasket(customerId));
    }

    @PostMapping("/checkout/{customerId}")
    public ResponseEntity<CheckoutResponse> checkout(@PathVariable UUID customerId,
                                                     @AuthenticationPrincipal Jwt jwt) {
        verifyCustomership(customerId, jwt);
        OrderId orderId = checkoutBasketUseCase.checkout(customerId);
        return ResponseEntity.ok(new CheckoutResponse(orderId.id()));
    }

    private void verifyCustomership(UUID customerId, Jwt jwt) {
        String keycloakSubjectId = jwt.getSubject();
        Customer customer = getCustomerUseCase.getCustomerByKeycloakId(keycloakSubjectId);

        if (customer == null) {
            throw new SecurityException("Customer not found for this user");
        }

        if (!customer.getCustomerId().equals(customerId)) {
            throw new SecurityException("You don't have permission to access this customer's data");
        }
    }
}


