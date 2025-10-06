package be.kdg.keepdishgoing.restaurants.domain.dish;

public record FoodTag(String value) {
    public FoodTag {
        if(value == null || value.isBlank()) {
            throw new IllegalArgumentException("Food tag value cannot be null or blank");
        }
    }
}
