package ifmo.lab.client;

import ifmo.lab.server.services.AuthService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static ifmo.lab.server.builders.UserBuilder.getPassword;
import static ifmo.lab.server.builders.UserBuilder.getUserName;

public class Authentication {
    private final CurrentUserManager userManager;
    private final AuthService authService;
    private final Scanner reader = new Scanner(System.in);
    private List<String> usernameList;

    public Authentication(CurrentUserManager userManager) {
        this.userManager = userManager;
        this.authService = new AuthService();
        loadUsernames();
    }

    private void loadUsernames() {
        usernameList = authService.getUsernames();
    }

    private void configUserManager(String username) {
        userManager.setUserName(username);
    }

    public CurrentUserManager start() {
        try {
            System.out.println("Добро пожаловать! Выберите что сделать: 1 - войти в аккаунт, 2 - зарегистрироваться, exit - выйти.");
            String choice = reader.nextLine();
            switch (choice) {
                case "1":
                    return login();
                case "2":
                    return registerUser();
                case "exit":
                    System.exit(0);
                    return null;
                default:
                    System.out.println("Неверный выбор, попробуйте снова.");
                    return start();
            }
        } catch (NoSuchElementException e) {
            System.out.println("Завершение программы.");
            System.exit(1);
            return new CurrentUserManager();
        }
    }

    public CurrentUserManager registerUser() {
        String username = getUserName(reader);

        if (usernameList.contains(username)) {
            System.out.println("Пользователь с данным никнеймом существует. Попробуйте еще раз.");
            return start();
        } else {
            String password = getPassword(reader, "Введите пароль:");
            String passwordAgain = getPassword(reader, "Повторите введенный пароль:");
            if (password.equals(passwordAgain)) {
                if (authService.register(username, password)) {
                    System.out.println("Пользователь успешно зарегистрирован.");
                    configUserManager(username);
                    loadUsernames(); // Обновляем список пользователей после регистрации
                } else {
                    System.out.println("Ошибка при регистрации пользователя.");
                    return start();
                }
            } else {
                System.out.println("Пароли не совпадают.");
                return start();
            }
        }
        return userManager;
    }

    public CurrentUserManager login() {
        String username = getUserName(reader);
        if (!usernameList.contains(username)) {
            System.out.println("Неверный логин.");
            return start();
        }
        String password = getPassword(reader, "Введите пароль:");
        if (authService.login(username, password)) {
            System.out.println("Успешный вход!");
            configUserManager(username);
        } else {
            System.out.println("Неверный пароль.");
            return start();
        }
        return userManager;
    }

    public CurrentUserManager getUserManager() {
        return userManager;
    }
}
