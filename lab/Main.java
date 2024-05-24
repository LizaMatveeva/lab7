package ifmo.lab;


import ifmo.lab.client.Authentication;
import ifmo.lab.client.ConsoleUI;
import ifmo.lab.client.CurrentUserManager;
import ifmo.lab.server.builders.ProductBuilder;
import ifmo.lab.server.commands.Invoker;
import ifmo.lab.server.exceptions.FileException;

public class Main {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        try {
            Authentication auth = new Authentication(new CurrentUserManager());

            ConsoleUI session = new ConsoleUI(new Invoker(auth.start()));
            session.start();
            
        } catch (FileException e) {
            System.out.println(e.getMessage());
        }
    }
}