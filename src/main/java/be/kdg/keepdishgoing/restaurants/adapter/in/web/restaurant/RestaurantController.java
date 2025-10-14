package be.kdg.keepdishgoing.restaurants.adapter.in.web.restaurant;


import be.kdg.keepdishgoing.restaurants.adapter.in.request.restautant.CreateRestaurantRequest;
import be.kdg.keepdishgoing.restaurants.adapter.in.response.restaurant.CreateRestaurantResponse;
import be.kdg.keepdishgoing.restaurants.adapter.in.response.restaurant.RestaurantByTypeOfCuisineResponse;
import be.kdg.keepdishgoing.restaurants.domain.owner.Owner;
import be.kdg.keepdishgoing.restaurants.domain.restaurant.Restaurant;
import be.kdg.keepdishgoing.restaurants.domain.restaurant.RestaurantId;
import be.kdg.keepdishgoing.common.commonEnum.commonRestaurantEnum.TypeOfCuisine;
import be.kdg.keepdishgoing.restaurants.port.in.dish.AddDishUseCase;
import be.kdg.keepdishgoing.restaurants.port.in.owner.GetOwnerUseCase;
import be.kdg.keepdishgoing.restaurants.port.in.restaurant.CreateRestaurantUseCase;
import be.kdg.keepdishgoing.restaurants.port.in.restaurant.FilterRestaurantByTypeOfCuisine;
import be.kdg.keepdishgoing.restaurants.port.in.restaurant.GetRestaurantUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final CreateRestaurantUseCase createRestaurantUseCase;
    private final GetOwnerUseCase getOwnerUseCase;
    private final GetRestaurantUseCase getRestaurantUseCase;
    private final FilterRestaurantByTypeOfCuisine  filterRestaurantByTypeOfCuisine;

    public RestaurantController(CreateRestaurantUseCase createRestaurantUseCase, GetOwnerUseCase getOwnerUseCase, GetRestaurantUseCase getRestaurantUseCase, FilterRestaurantByTypeOfCuisine filterRestaurantByTypeOfCuisine) {
        this.createRestaurantUseCase = createRestaurantUseCase;
        this.getOwnerUseCase = getOwnerUseCase;
        this.getRestaurantUseCase = getRestaurantUseCase;
        this.filterRestaurantByTypeOfCuisine = filterRestaurantByTypeOfCuisine;
    }

    @PostMapping
    public ResponseEntity<CreateRestaurantResponse> createRestaurant(
            @Valid @RequestBody CreateRestaurantRequest request,
            @AuthenticationPrincipal Jwt jwt) {

        String keycloakSubjectId = jwt.getSubject();
        Owner owner = getOwnerUseCase.getOwnerByKeycloakId(keycloakSubjectId);

        // Map DishRequest to AddDishCommand
        List<AddDishUseCase.AddDishCommand> dishCommands = request.dishes() != null
                ? request.dishes().stream()
                .map(dish -> new AddDishUseCase.AddDishCommand(
                        null, // restaurantId will be set in service
                        dish.name(),
                        dish.dishType(),
                        dish.foodTags(),
                        dish.description(),
                        dish.price(),
                        dish.pictureURL()
                ))
                .toList()
                : List.of();

        CreateRestaurantUseCase.CreateRestaurantCommand command = new CreateRestaurantUseCase.CreateRestaurantCommand(
                owner.getOwnerId(),
                owner.getKeycloakSubjectId(),
                request.name(),
                request.address(),
                request.email(),
                request.pictureURL(),
                request.cuisine(),
                request.preparationTime(),
                request.openingTime(),
                request.minPrice(),
                request.maxPrice(),
                request.estimatedDeliveryTime(),
                dishCommands
        );

        RestaurantId id = createRestaurantUseCase.createRestaurant(command);
        Restaurant restaurant = getRestaurantUseCase.getRestaurantById(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(CreateRestaurantResponse.fromDomain(restaurant));
    }

    @GetMapping("/type/{typeOfCuisine}")
    public List<RestaurantByTypeOfCuisineResponse> getRestaurantsByType(@PathVariable TypeOfCuisine typeOfCuisine) {
        return filterRestaurantByTypeOfCuisine.filterRestaurantByTypeOfCuisine(typeOfCuisine)
                .stream()
                .map(RestaurantByTypeOfCuisineResponse::fromDomain)
                .toList();
    }


}