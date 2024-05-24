package ifmo.lab.server.commands.list;

import ifmo.lab.server.commands.Command;
import ifmo.lab.server.controller.ProductController;
import ifmo.lab.server.exceptions.ArgumentException;

/**
 * The type Save command.
 */
public class SaveCommand implements Command {
    private final ProductController controller;

    /**
     * Instantiates a new Save command.
     *
     * @param controller the controller
     */
    public SaveCommand(ProductController controller) {
        this.controller = controller;
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            throw new ArgumentException("Команда не должна содержать аргументов.");
        }
        controller.save();
        System.out.println("Успешно сохранено!");
    }

    @Override
    public String description() {
        return "сохранить коллекцию в файл";
    }
}
