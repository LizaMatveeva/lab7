package ifmo.lab.server.builders;

import ifmo.lab.server.models.UnitOfMeasure;

import java.util.Scanner;

/**
 * The type Unit of measure builder.
 */
public class UnitOfMeasureBuilder {
    /**
     * Build unit of measure.
     *
     * @return the unit of measure
     */
    public static UnitOfMeasure build() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите единицу измерения: (1 - SQUARE_METERS, 2 - LITERS, 3 - MILLILITERS)");
        int choice = Integer.parseInt(sc.nextLine());
        switch (choice) {
            case 1:
                return UnitOfMeasure.SQUARE_METERS;
            case 2:
                return UnitOfMeasure.LITERS;
            case 3:
                return UnitOfMeasure.MILLILITERS;
            default:
                System.out.println("Недопустимое значение. Установлена единица измерения по умолчанию - SQUARE_METERS");
                return UnitOfMeasure.SQUARE_METERS;
        }
    }
}

