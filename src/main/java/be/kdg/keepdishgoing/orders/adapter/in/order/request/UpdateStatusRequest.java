package be.kdg.keepdishgoing.orders.adapter.in.order.request;


import be.kdg.keepdishgoing.orders.domain.order.OrderStatus;

public record UpdateStatusRequest(OrderStatus status) {}