package ifmo.lab.server.builders;

import ifmo.lab.server.models.Color;

import java.util.Scanner;

/**
 * The type Color builder.
 */
public class ColorBuilder {
    /**
     * Build color.
     *
     * @return the color
     */
    public static Color build() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите цвет: (1 - GREEN, 2 - YELLOW, 3 - ORANGE, 4 - BROWN)");
        int choice = Integer.parseInt(sc.nextLine());
        switch (choice) {
            case 1:
                return Color.GREEN;
            case 2:
                return Color.YELLOW;
            case 3:
                return Color.ORANGE;
            case 4:
                return Color.BROWN;
            default:
                System.out.println("Недопустимое значение. Установлен цвет по умолчанию - GREEN");
                return Color.GREEN;
        }
    }
}
