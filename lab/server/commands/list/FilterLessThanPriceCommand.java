package ifmo.lab.server.commands.list;

import ifmo.lab.server.commands.Command;
import ifmo.lab.server.controller.ProductController;

public class FilterLessThanPriceCommand implements Command {
    private final ProductController controller;

    public FilterLessThanPriceCommand(ProductController controller) {
        this.controller = controller;
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            System.out.println("Необходимо указать цену.");
            return;
        }
        try {
            float price = Float.parseFloat(args[1]);
            controller.filterLessThanPrice(price).forEach(System.out::println);
        } catch (NumberFormatException e) {
            System.out.println("Указанная цена неверна. Пожалуйста, используйте числовой формат.");
        }
    }

    @Override
    public String description() {
        return "вывести элементы, значение поля price которых меньше заданного";
    }
}
