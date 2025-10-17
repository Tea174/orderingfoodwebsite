package be.kdg.keepdishgoing.orders.domain.order;

import be.kdg.keepdishgoing.common.event.DomainEvent;
import be.kdg.keepdishgoing.common.event.orderEvents.OrderCreatedEvent;
import be.kdg.keepdishgoing.orders.domain.basket.Basket;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
public class Order {
    private OrderId orderId;
    private UUID customerId; // null for guest customers

    // Guest customer details
    private String guestName;
    private String guestEmail;
    private String deliveryAddress;

    private UUID restaurantId;
    private Double totalPrice;
    private OrderStatus status;

    private String rejectionReason;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<OrderItem> items;

    private final List<DomainEvent> domainEvents = new ArrayList<>();

    public Order() {}

    public boolean isGuestOrder() {
        return customerId == null;
    }

    public void clearDomainEvents() {
        domainEvents.clear();
    }

    public static Order fromBasketWithGuestDetails(
            Basket basket,
            String guestName,
            String guestEmail,
            String deliveryAddress
    ) {
        if (basket.isEmpty()) {
            throw new IllegalStateException("Cannot create order from empty basket");
        }

        List<OrderItem> orderItems = basket.getItems().stream()
                .map(item -> new OrderItem(
                        item.getDishId(),
                        item.getDishName(),
                        item.getPrice(),
                        item.getQuantity()
                ))
                .collect(Collectors.toList());

        Order order = new Order();
        order.orderId = OrderId.create();
        order.customerId = null; // Guest order
        order.guestName = guestName;
        order.guestEmail = guestEmail;
        order.deliveryAddress = deliveryAddress;
        order.restaurantId = basket.getRestaurantId();
        order.items = orderItems;
        order.totalPrice = basket.calculateTotal();
        order.status = OrderStatus.PENDING;
        order.createdAt = LocalDateTime.now();
        order.updatedAt = LocalDateTime.now();

        order.domainEvents.add(new OrderCreatedEvent(
                LocalDateTime.now(),
                order.orderId.id(),
                null,
                order.restaurantId,
                guestName,
                guestEmail,
                deliveryAddress,
                order.totalPrice
        ));

        return order;
    }

    // Registered user checkout
    public static Order fromBasket(Basket basket) {
        if (basket.isEmpty()) {
            throw new IllegalStateException("Cannot create order from empty basket");
        }

        Order order = createOrder(
                basket.getCustomerId(),
                basket.getRestaurantId(),
                basket.calculateTotal(),
                OrderStatus.PENDING,
                LocalDateTime.now(),
                basket.getItems().stream()
                        .map(item -> new OrderItem(
                                item.getDishId(),
                                item.getDishName(),
                                item.getPrice(),
                                item.getQuantity()
                        ))
                        .collect(Collectors.toList())
        );
        order.domainEvents.add(new OrderCreatedEvent(
                LocalDateTime.now(),
                order.orderId.id(),
                order.customerId,
                order.restaurantId,
                null,
                null,
                null,
                order.totalPrice
        ));

        return order;
    }

    public static Order createOrder(
            UUID customerId,
            UUID restaurantId,
            double totalPrice,
            OrderStatus orderStatus,
            LocalDateTime createdAt,
            List<OrderItem> items
    ) {
        Order order = new Order();
        order.orderId = OrderId.create();
        order.customerId = customerId;
        order.restaurantId = restaurantId;
        order.totalPrice = totalPrice;
        order.status = orderStatus;
        order.createdAt = createdAt;
        order.updatedAt = createdAt;
        order.items = items;
        return order;
    }

    private Double calculateTotal() {
        return items.stream()
                .mapToDouble(OrderItem::getSubtotal)
                .sum();
    }

    public void accept() {
        if (this.status != OrderStatus.PENDING) {
            throw new IllegalStateException("Only pending orders can be accepted");
        }
        this.status = OrderStatus.ACCEPTED;
        this.updatedAt = LocalDateTime.now();
    }

    public void reject(String reason) {
        if (this.status != OrderStatus.PENDING) {
            throw new IllegalStateException("Only pending orders can be rejected");
        }
        this.status = OrderStatus.REJECTED;
        this.rejectionReason = reason;
        this.updatedAt = LocalDateTime.now();
    }

    public void cancel() {
        if (this.status == OrderStatus.DELIVERED || this.status == OrderStatus.CANCELLED) {
            throw new IllegalStateException("Cannot cancel delivered or already cancelled order");
        }
        this.status = OrderStatus.CANCELLED;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateStatus(OrderStatus newStatus) {
        this.status = newStatus;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isPending() {
        return this.status == OrderStatus.PENDING;
    }
}
