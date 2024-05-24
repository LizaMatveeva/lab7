package ifmo.lab.server.commands.list;

import ifmo.lab.server.builders.ProductBuilder;
import ifmo.lab.server.commands.Command;
import ifmo.lab.server.controller.ProductController;
import ifmo.lab.server.exceptions.ArgumentException;
import ifmo.lab.server.models.Product;

/**
 * The type Remove lower command.
 */
public class RemoveLowerCommand implements Command {
    private final ProductController controller;

    /**
     * Instantiates a new Remove lower command.
     *
     * @param controller the controller
     */
    public RemoveLowerCommand(ProductController controller) {
        this.controller = controller;
    }

    @Override
    public void execute(String[] args) {
        if (args.length > 1) {
            System.out.println("Команда не должна содержать аргументов.");
            return;
        }
        System.out.println("Необходимо предоставить данные продукта для сравнения.");
        try {
            Product product = ProductBuilder.build();
            if (controller.removeLower(product)){
                System.out.println("Успешно удалены элементы!");
            } else {
                System.out.println("Не найдено элементов для удаления.");
            }
        } catch (Exception e) {
            System.out.println("Ошибка при разборе данных продукта: " + e.getMessage());
        }
    }

    @Override
    public String description() {
        return "удалить из коллекции все элементы, меньшие, чем заданный.";
    }
}
