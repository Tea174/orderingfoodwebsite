package be.kdg.keepdishgoing.owners.adapter.in.web.dish;

import be.kdg.keepdishgoing.owners.adapter.in.request.dish.AddDishRequest;
import be.kdg.keepdishgoing.owners.adapter.in.request.dish.UpdateDishRequest;
import be.kdg.keepdishgoing.owners.adapter.in.response.dish.*;
import be.kdg.keepdishgoing.owners.domain.dish.Dish;
import be.kdg.keepdishgoing.owners.domain.dish.DishId;
import be.kdg.keepdishgoing.owners.domain.dish.FoodTag;
import be.kdg.keepdishgoing.owners.domain.restaurant.RestaurantId;
import be.kdg.keepdishgoing.owners.port.in.dish.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dishes")
public class DishController {

    private final AddDishUseCase addDishUseCase;
    private final UpdateDishUseCase updateDishUseCase;
    private final DeleteDishUseCase deleteDishUseCase;
    private final PublishDishUseCase publishDishUseCase;
    private final GetDishesByRestaurantUseCase getDishesByRestaurantUseCase;

    public DishController(AddDishUseCase addDishUseCase,
                          UpdateDishUseCase updateDishUseCase,
                          DeleteDishUseCase deleteDishUseCase,
                          PublishDishUseCase publishDishUseCase,
                          GetDishesByRestaurantUseCase getDishesByRestaurantUseCase) {
        this.addDishUseCase = addDishUseCase;
        this.updateDishUseCase = updateDishUseCase;
        this.deleteDishUseCase = deleteDishUseCase;
        this.publishDishUseCase = publishDishUseCase;
        this.getDishesByRestaurantUseCase = getDishesByRestaurantUseCase;
    }

    @PostMapping
    public ResponseEntity<DishCreatedResponse> addDish(@Valid @RequestBody AddDishRequest request) {
        var command = new AddDishUseCase.AddDishCommand(
                RestaurantId.of(request.restaurantId()),
                request.name(),
                request.dishType(),
                request.foodTags() != null ?
                        request.foodTags().stream().map(FoodTag::new).collect(Collectors.toList()) :
                        List.of(),
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
            @Valid @RequestBody UpdateDishRequest request) {
        var command = new UpdateDishUseCase.UpdateDishCommand(
                DishId.of(dishId),
                request.name(),
                request.dishType(),
                request.foodTags() != null ?
                        request.foodTags().stream().map(FoodTag::new).collect(Collectors.toList()) :
                        List.of(),
                request.description(),
                request.price(),
                request.pictureURL()
        );

        updateDishUseCase.updateDish(command);
        return ResponseEntity.ok(DishUpdatedResponse.createSuccess());
    }

    @DeleteMapping("/{dishId}")
    public ResponseEntity<DishDeletedResponse> deleteDish(@PathVariable UUID dishId) {
        deleteDishUseCase.deleteDish(DishId.of(dishId));
        return ResponseEntity.ok(DishDeletedResponse.deletedSuccess());
    }

    @PatchMapping("/{dishId}/publish")
    public ResponseEntity<DishStateChangedResponse> publishDish(@PathVariable UUID dishId) {
        publishDishUseCase.publishDish(DishId.of(dishId));
        return ResponseEntity.ok(DishStateChangedResponse.published());
    }

    @PatchMapping("/{dishId}/unpublish")
    public ResponseEntity<DishStateChangedResponse> unpublishDish(@PathVariable UUID dishId) {
        publishDishUseCase.unpublishDish(DishId.of(dishId));
        return ResponseEntity.ok(DishStateChangedResponse.unpublished());
    }

    @PatchMapping("/{dishId}/out-of-stock")
    public ResponseEntity<DishStateChangedResponse> markOutOfStock(@PathVariable UUID dishId) {
        publishDishUseCase.markOutOfStock(DishId.of(dishId));
        return ResponseEntity.ok(DishStateChangedResponse.outOfStock());
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<DishListResponse> getDishesByRestaurant(@PathVariable UUID restaurantId) {
        List<Dish> dishes = getDishesByRestaurantUseCase.getDishesByRestaurant(
                RestaurantId.of(restaurantId)
        );
        return ResponseEntity.ok(DishListResponse.fromDomain(dishes));
    }

    @GetMapping("/{dishId}")
    public ResponseEntity<DishResponse> getDishById(@PathVariable UUID dishId) {
        // need to add GetDishByIdUseCase to your ports
        // For now, this is a placeholder showing the structure
        return ResponseEntity.ok().build();
    }
}
