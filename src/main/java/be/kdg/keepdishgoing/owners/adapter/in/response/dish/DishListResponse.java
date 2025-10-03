package be.kdg.keepdishgoing.owners.adapter.in.response.dish;

import be.kdg.keepdishgoing.owners.domain.Dish;

import java.util.List;
import java.util.stream.Collectors;

public record DishListResponse(
        List<DishResponse> dishes,
        int count
) {
    public static DishListResponse fromDomain(List<Dish> dishes) {
        List<DishResponse> dishResponses = dishes.stream()
                .map(DishResponse::fromDomain)
                .collect(Collectors.toList());

        return new DishListResponse(dishResponses, dishResponses.size());
    }
}