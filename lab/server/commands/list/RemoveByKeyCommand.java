package ifmo.lab.server.commands.list;

import ifmo.lab.server.commands.Command;
import ifmo.lab.server.controller.ProductController;

public class RemoveByKeyCommand implements Command {
    private final ProductController controller;

    public RemoveByKeyCommand(ProductController controller) {
        this.controller = controller;
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            System.out.println("Необходимо указать ключ.");
            return;
        }
        try {
            int key = Integer.parseInt(args[1]);
            System.out.println(controller.removeByKey(key));
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат ключа. Используйте целочисленное значение.");
        }
    }

    @Override
    public String description() {
        return "удалить элемент из коллекции по его ключу";
    }
}
