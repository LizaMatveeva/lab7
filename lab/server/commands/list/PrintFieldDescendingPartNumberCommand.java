package ifmo.lab.server.commands.list;

import ifmo.lab.server.commands.Command;
import ifmo.lab.server.controller.ProductController;

public class PrintFieldDescendingPartNumberCommand implements Command {
    private final ProductController controller;

    public PrintFieldDescendingPartNumberCommand(ProductController controller) {
        this.controller = controller;
    }

    @Override
    public void execute(String[] args) {
        controller.printFieldDescendingPartNumber().forEach(System.out::println);
    }

    @Override
    public String description() {
        return "вывести значения поля partNumber всех элементов в порядке убывания";
    }
}
