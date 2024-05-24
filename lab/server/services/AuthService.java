package ifmo.lab.server.services;

import ifmo.lab.server.database.UserDatabaseProvider;
import ifmo.lab.server.models.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Random;

public class AuthService {
    private final UserDatabaseProvider userDatabaseProvider;
    private User currentUser;
    private final String pepper = "!13&^dQLC(#";

    public AuthService() {
        this.userDatabaseProvider = new UserDatabaseProvider();
    }

    public boolean login(String username, String password) {
        User user = userDatabaseProvider.getUserByUsername(username);
        if (user != null) {
            String hashedInput = sha512encoding(password + pepper + user.getSalt());
            if (hashedInput != null && hashedInput.equals(user.getPasswordHash())) {
                currentUser = user;
                return true;
            }
        }
        return false;
    }

    public void logout() {
        currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean register(String username, String password) {
        String salt = saltBuilder();
        String hashedPassword = sha512encoding(password + pepper + salt);
        User newUser = new User(username, hashedPassword, salt);
        return userDatabaseProvider.addUser(newUser);
    }

    public List<String> getUsernames() {
        return userDatabaseProvider.getAllUsernames();
    }

    private static String saltBuilder() {
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            stringBuilder.append((char) random.nextInt(33, 126));
        }
        return stringBuilder.toString();
    }

    private static String sha512encoding(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] messageDigest = md.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
