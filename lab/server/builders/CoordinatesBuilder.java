package ifmo.lab.server.builders;

import ifmo.lab.server.exceptions.ValidationException;
import ifmo.lab.server.models.Coordinates;

import java.util.Scanner;

/**
 * The type Coordinates builder.
 */
public class CoordinatesBuilder {
    /**
     * Build coordinates.
     *
     * @return the coordinates
     */
    public static Coordinates build() {
        double x;
        Integer y;
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Введите координату x (больше -441):");
            x = Double.parseDouble(sc.nextLine());
            if (x <= -441) {
                throw new ValidationException("Значение x должно быть больше -441");
            }
            System.out.println("Введите координату y:");
            y = Integer.parseInt(sc.nextLine());
            if (y == null) {
                throw new ValidationException("Значение y не может быть null");
            }
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
            return build();
        } catch (NumberFormatException e) {
            System.out.println("Координаты должны быть числовыми значениями");
            return build();
        }
        return new Coordinates(x, y);
    }
}
