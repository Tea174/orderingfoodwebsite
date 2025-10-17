package be.kdg.keepdishgoing.restaurants.adapter.out.orderProjector;

import be.kdg.keepdishgoing.restaurants.domain.orderRecord.OrderProjectorRecord;
import be.kdg.keepdishgoing.restaurants.port.out.orderProjector.LoadOrderProjectorPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class OrderProjectorJpaAdapter implements LoadOrderProjectorPort {
    private final OrderProjectorJpaRepository repository;
    private final Logger logger = LoggerFactory.getLogger(OrderProjectorJpaAdapter.class);

    public OrderProjectorJpaAdapter(OrderProjectorJpaRepository repository) {
        this.repository = repository;
    }

    public void save(OrderProjectorRecord orderProjectorRecord) {
        logger.debug("Saving order record {}", orderProjectorRecord);
        OrderProjectorEntity entity = new OrderProjectorEntity();
        entity.setId(UUID.randomUUID());
        entity.setOrderId(orderProjectorRecord.orderId());
        entity.setRestaurantId(orderProjectorRecord.restaurantId());
        logger.debug("Saved order record {}", entity);
        repository.save(entity);
    }

    @Override
    public Optional<OrderProjectorRecord> findByOrderId(UUID orderId) {
        return repository.findById(orderId)
                .map(entity -> new OrderProjectorRecord(
                        entity.getOrderId(),
                        entity.getRestaurantId()
                ));
    }
}