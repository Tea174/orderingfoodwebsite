package be.kdg.keepdishgoing.restaurants.adapter.in.web.dish;

import be.kdg.keepdishgoing.restaurants.adapter.in.request.dish.AddDishRequest;
import be.kdg.keepdishgoing.restaurants.adapter.in.request.dish.UpdateDishRequest;
import be.kdg.keepdishgoing.restaurants.adapter.in.response.dish.*;
import be.kdg.keepdishgoing.restaurants.domain.dish.Dish;
import be.kdg.keepdishgoing.restaurants.domain.dish.DishId;
import be.kdg.keepdishgoing.common.commonEnum.commonDishEnum.DishType;
import be.kdg.keepdishgoing.common.commonEnum.commonDishEnum.FoodTag;
import be.kdg.keepdishgoing.restaurants.domain.owner.Owner;
import be.kdg.keepdishgoing.restaurants.domain.restaurant.Restaurant;
import be.kdg.keepdishgoing.restaurants.domain.restaurant.RestaurantId;
import be.kdg.keepdishgoing.restaurants.port.in.dish.*;
import be.kdg.keepdishgoing.restaurants.port.in.owner.GetOwnerUseCase;
import be.kdg.keepdishgoing.restaurants.port.in.restaurant.GetRestaurantUseCase;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/dishes")
@AllArgsConstructor
public class DishController {

    private final AddDishUseCase addDishUseCase;
    private final UpdateDishUseCase updateDishUseCase;
    private final DeleteDishUseCase deleteDishUseCase;
    private final PublishDishUseCase publishDishUseCase;
    private final GetDishesByRestaurantUseCase getDishesByRestaurantUseCase;
    private final GetDishUseCase getDishUseCase;
    private final GetOwnerUseCase getOwnerUseCase;
    private final GetRestaurantUseCase getRestaurantUseCase;
    private final FilterDishesUseCase filterDishesUseCase;



    @GetMapping("/published/restaurant/{restaurantId}")
    public List<DishPublishedResponse> getPublishedDishesByRestaurant(@PathVariable String restaurantId) {
        List<GetDishUseCase.PublishedDishDto> dishes =
                getDishUseCase.getPublishedDishesByRestaurant(
                        new RestaurantId(UUID.fromString(restaurantId))
                );

        return dishes.stream()
                .map(dto -> new DishPublishedResponse(
                        dto.dishId().id().toString(),
                        dto.name(),
                        dto.price(),
                        dto.inStock()
                ))
                .toList();
    }

    @GetMapping("/filter/type/{dishType}")
    public List<DishFilteredResponse> getDishesByType(@PathVariable DishType dishType) {
        List<Dish> dishes = filterDishesUseCase.filterDishByType(dishType);
        return dishes.stream()
                .map(dish -> {
                    Restaurant restaurant = getRestaurantUseCase.getRestaurantById(dish.getRestaurantId());
                    return DishFilteredResponse.fromDomain(dish, restaurant.getName());
                })
                .toList();
    }

    @GetMapping("/filter/tag/{foodTag}")
    public List<DishFilteredResponse> getDishesByFoodTag(@PathVariable FoodTag foodTag) {
        List<Dish> dishes = filterDishesUseCase.filterDishesByFoodTagUseCase(foodTag);
        return dishes.stream()
                .map(dish -> {
                    Restaurant restaurant = getRestaurantUseCase.getRestaurantById(dish.getRestaurantId());
                    return DishFilteredResponse.fromDomain(dish, restaurant.getName());
                })
                .toList();
    }




    @PostMapping
    public ResponseEntity<DishCreatedResponse> addDish(
            @Valid @RequestBody AddDishRequest request,
            @AuthenticationPrincipal Jwt jwt) {

        String keycloakSubjectId = jwt.getSubject();
        Owner owner = getOwnerUseCase.getOwnerByKeycloakId(keycloakSubjectId);
        Restaurant restaurant = getRestaurantUseCase.getRestaurantByOwnerId(owner.getOwnerId());

        var command = new AddDishUseCase.AddDishCommand(
                restaurant.getRestaurantId(),
                request.name(),
                request.dishType(),
                request.foodTags(),
                request.description(),
                request.price(),
                request.pictureURL()
        );

        DishId dishId = addDishUseCase.addDish(command);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(DishCreatedResponse.of(dishId.id()));
    }

    @PutMapping("/{dishId}")
    public ResponseEntity<DishUpdatedResponse> updateDish(
            @PathVariable UUID dishId,
            @Valid @RequestBody UpdateDishRequest request,
            @AuthenticationPrincipal Jwt jwt) {

        verifyOwnership(DishId.of(dishId), jwt);

        var command = new UpdateDishUseCase.UpdateDishCommand(
                DishId.of(dishId),
                request.name(),
                request.dishType(),
                request.foodTags(),
                request.description(),
                request.price(),
                request.pictureURL()
        );

        updateDishUseCase.updateDish(command);
        return ResponseEntity.ok(DishUpdatedResponse.createSuccess());
    }

    @DeleteMapping("/{dishId}")
    public ResponseEntity<DishDeletedResponse> deleteDish(
            @PathVariable UUID dishId,
            @AuthenticationPrincipal Jwt jwt) {

        verifyOwnership(DishId.of(dishId), jwt);
        deleteDishUseCase.deleteDish(DishId.of(dishId));
        return ResponseEntity.ok(DishDeletedResponse.deletedSuccess());
    }

    @PatchMapping("/{dishId}/publish")
    public ResponseEntity<DishStateChangedResponse> publishDish(
            @PathVariable UUID dishId,
            @AuthenticationPrincipal Jwt jwt) {

        verifyOwnership(DishId.of(dishId), jwt);
        publishDishUseCase.publishDish(DishId.of(dishId));
        return ResponseEntity.ok(DishStateChangedResponse.published());
    }

    @PatchMapping("/{dishId}/unpublish")
    public ResponseEntity<DishStateChangedResponse> unpublishDish(
            @PathVariable UUID dishId,
            @AuthenticationPrincipal Jwt jwt) {

        verifyOwnership(DishId.of(dishId), jwt);
        publishDishUseCase.unpublishDish(DishId.of(dishId));
        return ResponseEntity.ok(DishStateChangedResponse.unpublished());
    }

    @PatchMapping("/{dishId}/out-of-stock")
    public ResponseEntity<DishStockChangedResponse> markOutOfStock(
            @PathVariable UUID dishId,
            @AuthenticationPrincipal Jwt jwt) {

        verifyOwnership(DishId.of(dishId), jwt);
        publishDishUseCase.markOutOfStock(DishId.of(dishId));
        return ResponseEntity.ok(DishStockChangedResponse.outOfStock());
    }

    @PatchMapping("/{dishId}/back-in-stock")
    public ResponseEntity<DishStockChangedResponse> markBackInStock(
            @PathVariable UUID dishId,
            @AuthenticationPrincipal Jwt jwt) {

        verifyOwnership(DishId.of(dishId), jwt);
        publishDishUseCase.markBackInStock(DishId.of(dishId));
        return ResponseEntity.ok(DishStockChangedResponse.backInStock());
    }

    @GetMapping
    public ResponseEntity<DishListResponse> getMyDishes(@AuthenticationPrincipal Jwt jwt) {
        String keycloakSubjectId = jwt.getSubject();
        Owner owner = getOwnerUseCase.getOwnerByKeycloakId(keycloakSubjectId);
        Restaurant restaurant = getRestaurantUseCase.getRestaurantByOwnerId(owner.getOwnerId());

        List<Dish> dishes = getDishesByRestaurantUseCase.getDishesByRestaurant(
                restaurant.getRestaurantId()
        );
        return ResponseEntity.ok(DishListResponse.fromDomain(dishes));
    }

    private void verifyOwnership(DishId dishId, Jwt jwt) {
        // Get authenticated owner
        String keycloakSubjectId = jwt.getSubject();
        Owner owner = getOwnerUseCase.getOwnerByKeycloakId(keycloakSubjectId);
        Restaurant ownerRestaurant = getRestaurantUseCase.getRestaurantByOwnerId(owner.getOwnerId());

        // Get the dish
        Dish dish = getDishUseCase.getDishById(dishId);

        // Verify dish belongs to owner's restaurant
        if (!dish.getRestaurantId().equals(ownerRestaurant.getRestaurantId())) {
            throw new SecurityException("You don't have permission to access this dish");
        }
    }
}