package ifmo.lab.server.builders;

import ifmo.lab.server.models.Location;

import java.util.Scanner;

/**
 * The type Location builder.
 */
public class LocationBuilder {
    /**
     * Build location.
     *
     * @return the location
     */
    public static Location build() {
        int x;
        double y, z;
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Введите координату x:");
            x = Integer.parseInt(sc.nextLine());
            System.out.println("Введите координату y:");
            y = Double.parseDouble(sc.nextLine());
            System.out.println("Введите координату z:");
            z = Double.parseDouble(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Координаты должны быть числовыми значениями");
            return build();
        }
        return new Location(x, y, z);
    }
}
