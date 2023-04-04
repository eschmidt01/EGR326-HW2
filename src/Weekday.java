public enum Weekday {
    MONDAY("Monday"),
    TUESDAY("Tuesday"),
    WEDNESDAY("Wednesday"),
    THURSDAY("Thursday"),
    FRIDAY("Friday");

    private final String displayName;

    Weekday(String displayName) {
        this.displayName = displayName;
    }

    public static Weekday fromString(String s) {
        s = s.trim().toLowerCase();
        switch (s.toLowerCase()) {
            case "monday":
            case "m":
                return MONDAY;
            case "tuesday":
            case "tu":
            case "t":
                return TUESDAY;
            case "wednesday":
            case "w":
                return WEDNESDAY;
            case "thursday":
            case "th":
            case "r":
                return THURSDAY;
            case "friday":
            case "f":
                return FRIDAY;
            default:
                throw new IllegalArgumentException("Invalid weekday string: " + s);
        }
    }
    

    public String toShortName() {
        return displayName.substring(0, 1);
    }

    @Override
    public String toString() {
        return displayName.substring(0, 1).toUpperCase() + displayName.substring(1).toLowerCase();
    }
}
