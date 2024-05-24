package ifmo.lab.server.builders;


import java.util.Scanner;


public class UserBuilder {
    public static String getUserName(Scanner reader) {
        String login;
        System.out.println("Введите логин: ");
        login = reader.nextLine();
        return login;
    }

    public static String getPassword(Scanner reader, String message) {
        String pass;
        System.out.println(message);
        pass = reader.nextLine();
        return pass;
    }
}