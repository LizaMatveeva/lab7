package ifmo.lab.server.commands.list;

import ifmo.lab.server.commands.Command;
import ifmo.lab.server.controller.ProductController;

public class RemoveGreaterKeyCommand implements Command {
    private final ProductController controller;

    public RemoveGreaterKeyCommand(ProductController controller) {
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
            if (controller.removeGreaterKey(key)){
                System.out.println("Успешно удалены.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат ключа. Используйте целочисленное значение.");
        }
    }

    @Override
    public String description() {
        return "удалить из коллекции все элементы, ключ которых превышает заданный";
    }
}
