package be.kdg.keepdishgoing.orders.adapter.in.requestProjector;

import be.kdg.keepdishgoing.deliveries.adapter.out.requestProjector.RequestProjectorJpaAdapter;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RequestProjector {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final RequestProjectorJpaAdapter requestProjectorJpaAdapter;


}
