package be.kdg.keepdishgoing.deliveries.domain;

import be.kdg.keepdishgoing.owners.domain.Dish;
import be.kdg.keepdishgoing.owners.domain.Owner;

import java.util.List;

public class Order {
    private int orderId;
    private List<Dish> dishes;
    private String Address;
    private Owner owner;
}
