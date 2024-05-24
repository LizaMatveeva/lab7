package ifmo.lab.server.models;

/**
 * The enum Unit of measure.
 */
public enum UnitOfMeasure {
    /**
     * Square meters unit of measure.
     */
    SQUARE_METERS(1),
    /**
     * Liters unit of measure.
     */
    LITERS(2),
    /**
     * Milliliters unit of measure.
     */
    MILLILITERS(3);

    /**
     * The Value.
     */
    int value;

    /**
     * Instantiates a new Unit of measure.
     *
     * @param value the value
     */
    UnitOfMeasure(int value) {
        this.value = value;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public int getValue() {
        return value;
    }
}
