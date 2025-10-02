package be.kdg.keepdishgoing.owners.domain;

public record TypeOfCuisine(String value) {
    public TypeOfCuisine {
        if(value == null || value.isBlank()) {
            throw new IllegalArgumentException("Value cannot be null or blank");
        }
    }
}
