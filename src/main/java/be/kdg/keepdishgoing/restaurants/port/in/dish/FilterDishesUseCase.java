package be.kdg.keepdishgoing.restaurants.port.in.dish;

import be.kdg.keepdishgoing.restaurants.domain.dish.Dish;
import be.kdg.keepdishgoing.common.commonEnum.commonDishEnum.DishType;
import be.kdg.keepdishgoing.common.commonEnum.commonDishEnum.FoodTag;

import java.util.List;

public interface FilterDishesUseCase {
    List<Dish> filterDishByType(DishType dishType);
    List<Dish> filterDishesByFoodTagUseCase (FoodTag foodTag);
}
