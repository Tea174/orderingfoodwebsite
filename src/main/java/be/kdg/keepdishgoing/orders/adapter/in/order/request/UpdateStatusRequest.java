package be.kdg.keepdishgoing.orders.adapter.in.order.request;


import be.kdg.keepdishgoing.common.commonEnum.commonOrderEnum.OrderStatus;

public record UpdateStatusRequest(OrderStatus status) {}