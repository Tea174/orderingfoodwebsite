package be.kdg.keepdishgoing.orders.adapter.in.order;

import be.kdg.keepdishgoing.customers.domain.Customer;
import be.kdg.keepdishgoing.customers.port.in.GetCustomerUseCase;
import be.kdg.keepdishgoing.orders.adapter.in.order.request.RejectRequest;
import be.kdg.keepdishgoing.orders.adapter.in.order.request.UpdateStatusRequest;
import be.kdg.keepdishgoing.orders.adapter.in.order.response.OrderResponse;
import be.kdg.keepdishgoing.orders.adapter.out.customer.CustomerProjectorEntity;
import be.kdg.keepdishgoing.orders.adapter.out.customer.CustomerProjectorJpaRepository;
import be.kdg.keepdishgoing.orders.adapter.out.restaurant.RestaurantProjectorEntity;
import be.kdg.keepdishgoing.orders.adapter.out.restaurant.RestaurantProjectorJpaRepository;
import be.kdg.keepdishgoing.orders.domain.order.Order;
import be.kdg.keepdishgoing.orders.domain.order.OrderId;
import be.kdg.keepdishgoing.orders.port.in.order.*;
import be.kdg.keepdishgoing.restaurants.domain.owner.Owner;
import be.kdg.keepdishgoing.restaurants.domain.restaurant.Restaurant;
import be.kdg.keepdishgoing.restaurants.port.in.owner.GetOwnerUseCase;
import be.kdg.keepdishgoing.restaurants.port.in.restaurant.GetRestaurantUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {

    private final GetOrderUseCase getOrderUseCase;
    private final GetOrdersByCustomerUseCase getOrdersByCustomerUseCase;
    private final GetOrdersByRestaurantUseCase getOrdersByRestaurantUseCase;
    private final UpdateOrderStatusUseCase updateOrderStatusUseCase;
    private final CancelOrderUseCase cancelOrderUseCase;
    private final AcceptOrderUseCase acceptOrderUseCase;
    private final RejectOrderUseCase rejectOrderUseCase;
    private final RestaurantProjectorJpaRepository restaurantRepository;
    private final CustomerProjectorJpaRepository customerRepository;

    private void verifyRestaurantOwnership(UUID restaurantId, Jwt jwt) {
        String keycloakId = jwt.getSubject();
        RestaurantProjectorEntity restaurant = restaurantRepository
                .findByOwnerKeycloakId(keycloakId)
                .orElseThrow(() -> new SecurityException("RestaurantProjectorRecord not found for owner"));

        if (!restaurant.getRestaurantId().equals(restaurantId)) {
            throw new SecurityException("You don't have permission for this restaurant");
        }
    }

    private void verifyCustomership(UUID customerId, Jwt jwt) {
        String keycloakId = jwt.getSubject();
        CustomerProjectorEntity customer = customerRepository
                .findByKeycloakId(keycloakId)
                .orElseThrow(() -> new SecurityException("Customer not found"));

        if (!customer.getCustomerId().equals(customerId)) {
            throw new SecurityException("You don't have permission to access this customer's data");
        }
    }

    @PostMapping("/{restaurantId}/orders/{orderId}/accept")
    public ResponseEntity<Void> acceptOrder(
            @PathVariable UUID restaurantId,
            @PathVariable UUID orderId,
            @AuthenticationPrincipal Jwt jwt) {

        verifyRestaurantOwnership(restaurantId, jwt);
        acceptOrderUseCase.acceptOrder(
                new AcceptOrderUseCase.AcceptOrderCommand(OrderId.of(orderId), restaurantId)
        );
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{restaurantId}/orders/{orderId}/reject")
    public ResponseEntity<Void> rejectOrder(
            @PathVariable UUID restaurantId,
            @PathVariable UUID orderId,
            @RequestBody RejectRequest request,
            @AuthenticationPrincipal Jwt jwt) {

        verifyRestaurantOwnership(restaurantId, jwt);
        rejectOrderUseCase.rejectOrder(
                new RejectOrderUseCase.RejectOrderCommand(OrderId.of(orderId), restaurantId, request.reason())
        );
        return ResponseEntity.ok().build();
    }

    // Customer Get single order
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable UUID orderId,
                                                  @PathVariable UUID customerId,
                                                  @AuthenticationPrincipal Jwt jwt) {
        verifyCustomership(customerId, jwt);
        Order order = getOrderUseCase.getOrder(new OrderId(orderId));
        return ResponseEntity.ok(OrderResponse.fromDomain(order));
    }

    // Customer views their orders
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderResponse>> getCustomerOrders(@PathVariable UUID customerId,
                                                                 @AuthenticationPrincipal Jwt jwt) {
        verifyCustomership(customerId, jwt);
        List<Order> orders = getOrdersByCustomerUseCase.getOrders(customerId);
        return ResponseEntity.ok(orders.stream()
                .map(OrderResponse::fromDomain)
                .toList());
    }

    // RestaurantProjectorRecord owner views  orders
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<OrderResponse>> getRestaurantOrders(@PathVariable UUID restaurantId,
                                                                   @AuthenticationPrincipal Jwt jwt) {
        verifyRestaurantOwnership(restaurantId, jwt);
        List<Order> orders = getOrdersByRestaurantUseCase.getOrders(restaurantId);
        return ResponseEntity.ok(orders.stream()
                .map(OrderResponse::fromDomain)
                .toList());
    }

    // RestaurantProjectorRecord updates order status
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<String> updateOrderStatus(
            @PathVariable UUID orderId,
            @PathVariable UUID restaurantId,
            @RequestBody UpdateStatusRequest request,
            @AuthenticationPrincipal Jwt jwt) {
        verifyRestaurantOwnership(restaurantId, jwt);
        updateOrderStatusUseCase.updateStatus(new OrderId(orderId), request.status());
        return ResponseEntity.ok("Order status updated");
    }

    // customer Cancel order
    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<String> cancelOrder(@PathVariable UUID orderId,
                                              @PathVariable UUID customerId,
                                              @AuthenticationPrincipal Jwt jwt) {
        verifyCustomership(customerId, jwt);
        cancelOrderUseCase.cancel(new OrderId(orderId));
        return ResponseEntity.ok("Order cancelled");
    }
}