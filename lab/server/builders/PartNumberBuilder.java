package ifmo.lab.server.builders;

/**
 * The type Name builder.
 */
public class PartNumberBuilder {
    /**
     * Build string.
     *
     * @return the string
     */
    public static String build(String message) {
        String partNumber = StringFieldBuilder.build(message);
        if (partNumber.length() < 25 || partNumber.length() > 48){
            System.out.println("Длина строки должна быть больше 25, меньше 48.");
            return build(message);
        } else {
            return partNumber;
        }
    }
}
