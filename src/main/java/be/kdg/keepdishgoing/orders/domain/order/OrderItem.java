package be.kdg.keepdishgoing.orders.domain.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class OrderItem {
    private UUID dishId;
    private String dishName;
    private Double price;
    private int quantity;

    public Double getSubtotal() {
        return price * quantity;
    }
}