// adapter/out/order/OrderJpaAdapter.java
package be.kdg.keepdishgoing.orders.adapter.out.order;

import be.kdg.keepdishgoing.orders.domain.order.Order;
import be.kdg.keepdishgoing.orders.domain.order.OrderId;
import be.kdg.keepdishgoing.common.commonEnum.commonOrderEnum.OrderStatus;
import be.kdg.keepdishgoing.orders.port.out.order.LoadOrderPort;
import be.kdg.keepdishgoing.orders.port.out.order.SaveOrderPort;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class OrderJpaAdapter implements SaveOrderPort, LoadOrderPort {

    private final OrderJpaRepository orderJpaRepository;
    private final OrderMapper orderMapper;

    @Override
    public void saveOrder(Order order) {
        OrderJpaEntity entity = orderMapper.toJpaEntity(order);
        orderJpaRepository.save(entity);
    }

    @Override
    public Optional<Order> loadOrder(OrderId orderId) {
        return orderJpaRepository.findById(orderId.id())
                .map(orderMapper::toDomain);
    }

    @Override
    public List<Order> loadOrdersByCustomer(UUID customerId, int page, int pageSize) {
        return orderJpaRepository.findByCustomerIdOrderByCreatedAtDesc(
                        customerId,
                        PageRequest.of(page, pageSize)
                ).stream()
                .map(orderMapper::toDomain)
                .toList();
    }

    @Override
    public List<Order> loadOrdersByRestaurant(UUID restaurantId) {
        return orderJpaRepository.findByRestaurantId(restaurantId)
                .stream()
                .map(orderMapper::toDomain)
                .toList();
    }

    @Override
    public List<Order> loadPendingOrdersByRestaurant(UUID restaurantId) {
        return orderJpaRepository.findByRestaurantIdAndStatus(
                        restaurantId,
                        OrderStatus.PENDING
                ).stream()
                .map(orderMapper::toDomain)
                .toList();
    }
}