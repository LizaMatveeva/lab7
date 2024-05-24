package ifmo.lab.server.commands.list;

import ifmo.lab.server.commands.Command;
import ifmo.lab.server.controller.ProductController;
import ifmo.lab.server.exceptions.ArgumentException;

/**
 * The type Clear command.
 */
public class ClearCommand implements Command {
    private final ProductController controller;

    /**
     * Instantiates a new Clear command.
     *
     * @param controller the controller
     */
    public ClearCommand(ProductController controller) {
        this.controller = controller;
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            throw new ArgumentException("Команда не должна содержать аргументов.");
        }
        System.out.println(controller.clear());
    }

    @Override
    public String description() {
        return "очищает коллекцию.";
    }
}
