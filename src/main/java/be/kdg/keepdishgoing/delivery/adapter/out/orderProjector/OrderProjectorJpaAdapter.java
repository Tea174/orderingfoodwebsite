package be.kdg.keepdishgoing.delivery.adapter.out.orderProjector;

import be.kdg.keepdishgoing.delivery.domain.orderRecord.OrderProjectorRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class OrderProjectorJpaAdapter {
    private final OrderProjectorJpaRepository repository;
    private final Logger logger = LoggerFactory.getLogger(OrderProjectorJpaAdapter.class);


    public OrderProjectorJpaAdapter(OrderProjectorJpaRepository repository) {
        this.repository = repository;
    }

    public void save(OrderProjectorRecord orderProjectorRecord) {
        logger.debug("Saving order record {}", orderProjectorRecord);
        OrderProjectorEntity orderProjectorEntity = new OrderProjectorEntity();
        orderProjectorEntity.setOrderId(orderProjectorRecord.orderId());
        orderProjectorEntity.setRestaurantId(orderProjectorRecord.restaurantId());
        orderProjectorEntity.setCustomerID(orderProjectorRecord.customerID());
        logger.debug("Saving order record {}", orderProjectorEntity);
        repository.save(orderProjectorEntity);


    }


}
