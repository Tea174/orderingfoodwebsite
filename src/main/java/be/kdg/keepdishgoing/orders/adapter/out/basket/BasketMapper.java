package be.kdg.keepdishgoing.orders.adapter.out.basket;

import be.kdg.keepdishgoing.orders.adapter.out.basket.basketItem.BasketItemJpaEntity;
import be.kdg.keepdishgoing.orders.domain.basket.*;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class BasketMapper {

    public Basket toDomain(BasketJpaEntity entity) {
        return new Basket(
                new BasketId(entity.getBasketId()),
                entity.getCustomerId(),
                entity.getRestaurantId(),
                entity.getItems().stream()
                        .map(this::toBasketItem)
                        .collect(Collectors.toList())
        );
    }

    public BasketJpaEntity toJpaEntity(Basket basket) {
        BasketJpaEntity entity = new BasketJpaEntity();
        entity.setBasketId(basket.getBasketId().id());
        entity.setCustomerId(basket.getCustomerId());
        entity.setRestaurantId(basket.getRestaurantId());

        entity.setItems(basket.getItems().stream()
                .map(item -> toBasketItemJpaEntity(item, entity))
                .collect(Collectors.toList()));

        return entity;
    }

    private BasketItem toBasketItem(BasketItemJpaEntity entity) {
        return new BasketItem(
                entity.getDishId(),
                entity.getDishName(),
                entity.getPrice(),
                entity.getQuantity()
        );
    }

    private BasketItemJpaEntity toBasketItemJpaEntity(BasketItem item, BasketJpaEntity basket) {
        BasketItemJpaEntity entity = new BasketItemJpaEntity();
        entity.setBasket(basket);
        entity.setDishId(item.getDishId());
        entity.setDishName(item.getDishName());
        entity.setPrice(item.getPrice());
        entity.setQuantity(item.getQuantity());
        return entity;
    }
}