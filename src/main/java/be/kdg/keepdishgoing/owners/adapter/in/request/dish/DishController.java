package be.kdg.keepdishgoing.owners.adapter.in.request.dish;

import be.kdg.keepdishgoing.owners.domain.*;
import be.kdg.keepdishgoing.owners.port.in.dish.*;
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

    // Add new dish
    @PostMapping
    public ResponseEntity<DishResponse> addDish(@RequestBody AddDishRequest request) {
        var command = new AddDishUseCase.AddDishCommand(
                RestaurantId.of(request.restaurantId()),
                request.name(),
                request.dishType(),
                request.foodTags().stream().map(FoodTag::new).collect(Collectors.toList()),
                request.description(),
                request.price(),
                request.pictureURL()
        );

        DishId dishId = addDishUseCase.addDish(command);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new DishResponse(dishId.id(), "Dish created successfully"));
    }

    // Update existing dish
    @PutMapping("/{dishId}")
    public ResponseEntity<Void> updateDish(@PathVariable UUID dishId,
                                           @RequestBody UpdateDishRequest request) {
        var command = new UpdateDishUseCase.UpdateDishCommand(
                DishId.of(dishId),
                request.name(),
                request.dishType(),
                request.foodTags().stream().map(FoodTag::new).collect(Collectors.toList()),
                request.description(),
                request.price(),
                request.pictureURL()
        );

        updateDishUseCase.updateDish(command);
        return ResponseEntity.ok().build();
    }

    // Delete dish
    @DeleteMapping("/{dishId}")
    public ResponseEntity<Void> deleteDish(@PathVariable UUID dishId) {
        deleteDishUseCase.deleteDish(DishId.of(dishId));
        return ResponseEntity.noContent().build();
    }

    // Publish dish
    @PatchMapping("/{dishId}/publish")
    public ResponseEntity<Void> publishDish(@PathVariable UUID dishId) {
        publishDishUseCase.publishDish(DishId.of(dishId));
        return ResponseEntity.ok().build();
    }

    // Unpublish dish
    @PatchMapping("/{dishId}/unpublish")
    public ResponseEntity<Void> unpublishDish(@PathVariable UUID dishId) {
        publishDishUseCase.unpublishDish(DishId.of(dishId));
        return ResponseEntity.ok().build();
    }

    // Mark out of stock
    @PatchMapping("/{dishId}/out-of-stock")
    public ResponseEntity<Void> markOutOfStock(@PathVariable UUID dishId) {
        publishDishUseCase.markOutOfStock(DishId.of(dishId));
        return ResponseEntity.ok().build();
    }

    // Get all dishes for a restaurant
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<DishDTO>> getDishesByRestaurant(@PathVariable UUID restaurantId) {
        List<Dish> dishes = getDishesByRestaurantUseCase.getDishesByRestaurant(
                RestaurantId.of(restaurantId)
        );

        List<DishDTO> dishDTOs = dishes.stream()
                .map(this::toDishDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dishDTOs);
    }

    private DishDTO toDishDTO(Dish dish) {
        return new DishDTO(
                dish.getDishId().id(),
                dish.getRestaurantId().id(),
                dish.getName(),
                dish.getDishType(),
                dish.getFoodTags().stream().map(FoodTag::value).collect(Collectors.toList()),
                dish.getDescription(),
                dish.getPrice(),
                dish.getPictureURL(),
                dish.getState()
        );
    }

    // DTOs
    record AddDishRequest(
            UUID restaurantId,
            String name,
            DishType dishType,
            List<String> foodTags,
            String description,
            double price,
            String pictureURL
    ) {}

    record UpdateDishRequest(
            String name,
            DishType dishType,
            List<String> foodTags,
            String description,
            double price,
            String pictureURL
    ) {}

    record DishResponse(UUID dishId, String message) {}

    record DishDTO(
            UUID dishId,
            UUID restaurantId,
            String name,
            DishType dishType,
            List<String> foodTags,
            String description,
            double price,
            String pictureURL,
            DishState state
    ) {}
}