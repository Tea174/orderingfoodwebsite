//package be.kdg.keepdishgoing.restaurants.adapter.out;
//
//import be.kdg.keepdishgoing.restaurants.domain.dish.DishId;
//import be.kdg.keepdishgoing.restaurants.port.in.dish.GetPublishedDishesUseCase;
//import org.springframework.stereotype.Service;
//
//import java.util.UUID;
//
//@Service
//public class RestaurantClient {
//
//    private final GetPublishedDishesUseCase getPublishedDishesUseCase;
//
//    public RestaurantClient(GetPublishedDishesUseCase getPublishedDishesUseCase) {
//        this.getPublishedDishesUseCase = getPublishedDishesUseCase;
//    }
//
//    public DishDto getDish(UUID dishId) {
//        var dto = getPublishedDishesUseCase.getPublishedDish(new DishId(dishId));
//        return new DishDto(
//                dto.dishId().id(),
//                dto.name(),
//                dto.price(),
//                dto.inStock(),
//                dto.restaurantId().id()
//        );
//    }
//
//    public record DishDto(
//            UUID dishId,
//            String name,
//            Double price,
//            boolean inStock,
//            UUID restaurantId
//    ) {}
//}