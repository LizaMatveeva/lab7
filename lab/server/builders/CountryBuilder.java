package ifmo.lab.server.builders;

import ifmo.lab.server.models.Country;

import java.util.Scanner;

/**
 * The type Country builder.
 */
public class CountryBuilder {
    /**
     * Build country.
     *
     * @return the country
     */
    public static Country build() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите страну: (1 - GERMANY, 2 - CHINA, 3 - VATICAN, 4 - SOUTH_KOREA, 5 - JAPAN)");
        int choice = Integer.parseInt(sc.nextLine());
        switch (choice) {
            case 1:
                return Country.GERMANY;
            case 2:
                return Country.CHINA;
            case 3:
                return Country.VATICAN;
            case 4:
                return Country.SOUTH_KOREA;
            case 5:
                return Country.JAPAN;
            default:
                System.out.println("Недопустимое значение. Установлена страна по умолчанию - GERMANY");
                return Country.GERMANY;
        }
    }
}

