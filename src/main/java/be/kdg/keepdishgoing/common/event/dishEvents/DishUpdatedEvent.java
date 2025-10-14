package be.kdg.keepdishgoing.common.event.dishEvents;

import be.kdg.keepdishgoing.common.commonEnum.commonDishEnum.DishType;
import be.kdg.keepdishgoing.common.commonEnum.commonDishEnum.FoodTag;
import be.kdg.keepdishgoing.common.event.DomainEvent;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record DishUpdatedEvent(
        LocalDateTime eventPit,
        UUID dishId,
        UUID restaurantId,
        String name,
        DishType dishType,
        List<FoodTag> foodTags,
        String description,
        double price,
        String pictureURL
) implements DomainEvent {}