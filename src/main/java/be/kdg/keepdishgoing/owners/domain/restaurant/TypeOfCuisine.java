package be.kdg.keepdishgoing.owners.domain.restaurant;


public enum TypeOfCuisine {
    // European
    ITALIAN("Italian"),
    FRENCH("French"),
    SPANISH("Spanish"),
    GREEK("Greek"),
    PORTUGUESE("Portuguese"),
    GERMAN("German"),
    BRITISH("British"),
    IRISH("Irish"),
    BELGIAN("Belgian"),
    DUTCH("Dutch"),
    SWISS("Swiss"),
    AUSTRIAN("Austrian"),
    POLISH("Polish"),
    RUSSIAN("Russian"),
    UKRAINIAN("Ukrainian"),
    TURKISH("Turkish"),
    SCANDINAVIAN("Scandinavian"),
    SWEDISH("Swedish"),
    NORWEGIAN("Norwegian"),
    DANISH("Danish"),
    FINNISH("Finnish"),

    // Asian
    CHINESE("Chinese"),
    JAPANESE("Japanese"),
    KOREAN("Korean"),
    THAI("Thai"),
    VIETNAMESE("Vietnamese"),
    INDIAN("Indian"),
    PAKISTANI("Pakistani"),
    BANGLADESHI("Bangladeshi"),
    INDONESIAN("Indonesian"),
    MALAYSIAN("Malaysian"),
    SINGAPOREAN("Singaporean"),
    FILIPINO("Filipino"),
    NEPALESE("Nepalese"),
    SRI_LANKAN("Sri Lankan"),
    BURMESE("Burmese"),
    CAMBODIAN("Cambodian"),
    LAOTIAN("Laotian"),
    MONGOLIAN("Mongolian"),
    TIBETAN("Tibetan"),

    // Middle Eastern
    LEBANESE("Lebanese"),
    PERSIAN("Persian"),
    IRAQI("Iraqi"),
    SYRIAN("Syrian"),
    JORDANIAN("Jordanian"),
    ISRAELI("Israeli"),
    MOROCCAN("Moroccan"),
    EGYPTIAN("Egyptian"),
    TUNISIAN("Tunisian"),
    ALGERIAN("Algerian"),

    // African
    ETHIOPIAN("Ethiopian"),
    NIGERIAN("Nigerian"),
    SOUTH_AFRICAN("South African"),
    KENYAN("Kenyan"),
    GHANAIAN("Ghanaian"),
    SENEGALESE("Senegalese"),

    // Americas
    MEXICAN("Mexican"),
    AMERICAN("American"),
    BRAZILIAN("Brazilian"),
    ARGENTINIAN("Argentinian"),
    PERUVIAN("Peruvian"),
    COLOMBIAN("Colombian"),
    VENEZUELAN("Venezuelan"),
    CHILEAN("Chilean"),
    CUBAN("Cuban"),
    JAMAICAN("Jamaican"),
    CARIBBEAN("Caribbean"),
    PUERTO_RICAN("Puerto Rican"),

    // Fusion & Regional
    FUSION("Fusion"),
    MEDITERRANEAN("Mediterranean"),
    LATIN_AMERICAN("Latin American"),
    MIDDLE_EASTERN("Middle Eastern"),
    ASIAN_FUSION("Asian Fusion"),
    PAN_ASIAN("Pan-Asian"),
    SOUTH_AMERICAN("South American"),
    CENTRAL_AMERICAN("Central American"),

    // Specific Styles
    BBQ("BBQ"),
    STEAKHOUSE("Steakhouse"),
    SEAFOOD("Seafood"),
    VEGETARIAN("Vegetarian"),
    VEGAN("Vegan"),
    FAST_FOOD("Fast Food"),
    STREET_FOOD("Street Food"),
    COMFORT_FOOD("Comfort Food"),
    CONTEMPORARY("Contemporary"),
    INTERNATIONAL("International");

    private final String displayName;

    TypeOfCuisine(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    // helper method to get enum from display name
    public static TypeOfCuisine fromDisplayName(String displayName) {
        for (TypeOfCuisine cuisine : values()) {
            if (cuisine.displayName.equalsIgnoreCase(displayName)) {
                return cuisine;
            }
        }
        throw new IllegalArgumentException("Unknown cuisine: " + displayName);
    }
}