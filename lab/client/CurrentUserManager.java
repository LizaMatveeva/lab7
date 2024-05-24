package ifmo.lab.client;

public class CurrentUserManager {
    private String userName;

    /**
     * Instantiates a new User manager.
     *
     * @param userName the username
     */
    public CurrentUserManager(String userName) {
        this.userName = userName;
    }

    /**
     * Instantiates a new User manager.
     */
    public CurrentUserManager() {
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets username.
     *
     * @param userName the username
     * @return the username
     */
    public CurrentUserManager setUserName(String userName) {
        this.userName = userName;
        return this;
    }


}
