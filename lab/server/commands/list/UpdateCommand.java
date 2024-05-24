package ifmo.lab.server.commands.list;

import ifmo.lab.server.builders.ProductBuilder;
import ifmo.lab.server.commands.Command;
import ifmo.lab.server.controller.ProductController;
import ifmo.lab.server.models.Product;


/**
 * The type Update command.
 */
public class UpdateCommand implements Command {
    private final ProductController controller;

    /**
     * Instantiates a new Update command.
     *
     * @param controller the controller
     */
    public UpdateCommand(ProductController controller) {
        this.controller = controller;
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 1) {
            System.out.println("Необходимо указать ID и данные продукта для обновления.");
            return;
        }
        try {
            long id = Long.parseLong(args[1]);
            if (controller.getAllProducts().values().stream().filter(p -> p.getId()==id).findAny().get().getCreatedBy().equals(controller.getCurrentUser())){
                Product product = ProductBuilder.build();
                Product updatedProduct = controller.update(id, product);
                if (updatedProduct != null) {
                    System.out.println("Продукт успешно обновлен: " + updatedProduct);
                } else {
                    System.out.println("Продукт с ID " + id + " не найден.");
                }
            } else {
                System.out.println("Этот продукт вам не пренадлежит");
            }
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат ID. Используйте числовой формат.");
        } catch (Exception e) {
            System.out.println("Ошибка при обновлении продукта: " + e.getMessage());
        }
    }

    @Override
    public String description() {
        return "обновить существующий объект по id.";
    }
}
