package be.kdg.keepdishgoing.orders.domain.basket;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class BasketItem {
    private UUID dishId;
    private String dishName;
    private Double price;
    private int quantity;

    public BasketItem(UUID dishId, String dishName, Double price, int quantity) {
        this.dishId = dishId;
        this.dishName = dishName;
        this.price = price;
        this.quantity = quantity;
    }

    public Double getSubtotal() {
        return price * quantity;
    }

    public void increaseQuantity(int amount) {
        this.quantity += amount;
    }

    public BasketItem() {
    }
}