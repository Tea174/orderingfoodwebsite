package be.kdg.keepdishgoing.orders.adapter.out.restaurant;

import be.kdg.keepdishgoing.restaurants.domain.dish.DishId;
import be.kdg.keepdishgoing.restaurants.port.in.dish.GetDishUseCase;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class RestaurantClient {

    private final GetDishUseCase getDishUseCase;

    public DishDto getDish(UUID dishId) {
        var dto = getDishUseCase.getPublishedDish(new DishId(dishId));
        return new DishDto(
                dto.dishId().id(),
                dto.name(),
                dto.price(),
                dto.inStock(),
                dto.restaurantId().id()
        );
    }

    public record DishDto(
            UUID dishId,
            String name,
            Double price,
            boolean inStock,
            UUID restaurantId
    ) {}
}