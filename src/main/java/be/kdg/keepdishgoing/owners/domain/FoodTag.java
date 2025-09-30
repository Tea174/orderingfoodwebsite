package be.kdg.keepdishgoing.owners.domain;

public record FoodTag(String value) {
    public FoodTag {
        if(value == null || value.isBlank()) {
            throw new IllegalArgumentException("Food tag value cannot be null or blank");
        }
    }
}
