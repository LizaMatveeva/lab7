package ifmo.lab.server.builders;

import java.util.Scanner;

/**
 * The type Integer builder.
 */
public class DoubleBuilder {
    /**
     * Build int.
     *
     * @param message the message
     * @return the int
     */
    public static double build(String message) {
        try {
            System.out.println(message);
            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();
            if (input.equals("null") || input.trim().equals("")){
                return -1;
            }
            double value = Double.parseDouble(input);
            if (value <= 0) {
                System.out.println("Значение должно быть больше нуля.");
                return build(message);
            }
            return value;
        } catch (NumberFormatException e) {
            System.out.println("Это поле принимает числовое значение.");
            return build(message);
        }
    }
}
