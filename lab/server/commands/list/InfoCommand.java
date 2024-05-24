package ifmo.lab.server.commands.list;

import ifmo.lab.server.commands.Command;
import ifmo.lab.server.controller.ProductController;
import ifmo.lab.server.exceptions.ArgumentException;

/**
 * The type Info command.
 */
public class InfoCommand implements Command {
    private final ProductController controller;

    /**
     * Instantiates a new Info command.
     *
     * @param controller the controller
     */
    public InfoCommand(ProductController controller) {
        this.controller = controller;
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            throw new ArgumentException("Команда не должна содержать аргументов.");
        }
        System.out.println(controller.info());
    }

    @Override
    public String description() {
        return "выводит информацию о базе данных.";
    }
}
