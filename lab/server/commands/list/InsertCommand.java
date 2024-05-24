package ifmo.lab.server.commands.list;

import ifmo.lab.server.builders.ProductBuilder;
import ifmo.lab.server.commands.Command;
import ifmo.lab.server.controller.ProductController;
import ifmo.lab.server.models.Product;

public class InsertCommand implements Command {
    private final ProductController controller;

    public InsertCommand(ProductController controller) {
        this.controller = controller;
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            System.out.println("Необходимо указать ключ и заполнить продукт.");
            return;
        }
        try {
            int key = Integer.parseInt(args[1]);
            if (controller.getAllProducts().containsKey(key)){
                System.out.println("Объект с данным ключом существует.");
            } else {
                Product product = ProductBuilder.build();
                System.out.println(controller.insert(key, product));
            }
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат ключа. Используйте целочисленное значение.");
        } catch (Exception e) {
            System.out.println("Ошибка при создании продукта: " + e.getMessage());
        }
    }

    @Override
    public String description() {
        return "добавить новый элемент с заданным ключом";
    }
}
