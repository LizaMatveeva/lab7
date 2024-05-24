package ifmo.lab.server.models;

/**
 * The enum Country.
 */
public enum Country {
    /**
     * Germany country.
     */
    GERMANY(1),
    /**
     * China country.
     */
    CHINA(2),
    /**
     * Vatican country.
     */
    VATICAN(3),
    /**
     * South Korea country.
     */
    SOUTH_KOREA(4),
    /**
     * Japan country.
     */
    JAPAN(5);

    /**
     * The Value.
     */
    int value;

    /**
     * Instantiates a new Country.
     *
     * @param value the value
     */
    Country(int value) {
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
