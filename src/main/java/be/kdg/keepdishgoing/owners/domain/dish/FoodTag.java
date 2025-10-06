package be.kdg.keepdishgoing.owners.domain.dish;

public record FoodTag(String value) {
    public FoodTag {
        if(value == null || value.isBlank()) {
            throw new IllegalArgumentException("Food tag value cannot be null or blank");
        }
    }
}
