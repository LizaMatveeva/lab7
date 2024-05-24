package ifmo.lab.server.commands.list;

import ifmo.lab.server.commands.Command;
import ifmo.lab.server.controller.ProductController;
import ifmo.lab.server.exceptions.ArgumentException;
import ifmo.lab.server.models.Product;

import java.util.Map;

/**
 * The type Show command.
 */
public class ShowCommand implements Command {
    private final ProductController controller;

    /**
     * Instantiates a new Show command.
     *
     * @param controller the controller
     */
    public ShowCommand(ProductController controller) {
        this.controller = controller;
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            throw new ArgumentException("Команда не должна содержать аргументов.");
        }
        Map<Integer, Product> products = controller.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("Коллекция пуста.");
        } else {
            for (Map.Entry<Integer, Product> entry : products.entrySet()) {
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }
        }
    }

    @Override
    public String description() {
        return "выводит все объекты.";
    }
}
