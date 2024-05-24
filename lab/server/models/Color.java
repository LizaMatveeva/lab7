package ifmo.lab.server.models;

/**
 * The enum Color.
 */
public enum Color {
    /**
     * Green color.
     */
    GREEN(1),
    /**
     * Yellow color.
     */
    YELLOW(2),
    /**
     * Orange color.
     */
    ORANGE(3),
    /**
     * Brown color.
     */
    BROWN(4);

    /**
     * The Value.
     */
    int value;

    /**
     * Instantiates a new Color.
     *
     * @param value the value
     */
    Color(int value) {
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
