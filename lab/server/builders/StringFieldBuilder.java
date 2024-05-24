package ifmo.lab.server.builders;

import ifmo.lab.server.exceptions.ValidationException;
import ifmo.lab.server.validation.Validation;

import java.util.Scanner;


/**
 * The type Name builder.
 */
public class StringFieldBuilder {
    /**
     * Build string.
     *
     * @return the string
     */
    public static String build(String message) {
        String name;
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println(message);
            name = sc.nextLine();
            if (!Validation.validateName(name)){
                throw new ValidationException("Неверный ввод. Он не должен быть пустым.");
            }
            return name;
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
            return build(message);
        }
    }
}
