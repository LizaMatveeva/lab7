package ifmo.lab.server.commands.list;

import ifmo.lab.server.commands.Command;
import ifmo.lab.server.controller.ProductController;
import ifmo.lab.server.exceptions.ArgumentException;

public class FilterContainsNameCommand implements Command {
    private final ProductController controller;

    public FilterContainsNameCommand(ProductController controller) {
        this.controller = controller;
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 2) {
            throw new ArgumentException("Команда содержит 1 аргумент - подстроку.");
        }
        controller.filterContainsName(args[1]).forEach(System.out::println);
    }

    @Override
    public String description() {
        return "Вывести элементы, значение поля name которых содержит заданную подстроку";
    }
}
