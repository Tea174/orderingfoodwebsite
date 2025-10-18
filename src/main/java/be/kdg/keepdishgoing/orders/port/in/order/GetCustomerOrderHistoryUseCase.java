package be.kdg.keepdishgoing.orders.port.in.order;

import be.kdg.keepdishgoing.orders.domain.order.OrderId;
import be.kdg.keepdishgoing.common.commonEnum.commonOrderEnum.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface GetCustomerOrderHistoryUseCase {
    List<OrderHistoryItem> getOrderHistory(GetOrderHistoryQuery query);

    record GetOrderHistoryQuery(UUID customerId, Integer page, Integer pageSize) {
        public GetOrderHistoryQuery {
            page = page != null ? page : 0;
            pageSize = pageSize != null ? pageSize : 20;
        }
    }

    record OrderHistoryItem(
            OrderId orderId,
            String restaurantName,
            OrderStatus status,
            double totalAmount,
            LocalDateTime createdAt
    ) {}
}