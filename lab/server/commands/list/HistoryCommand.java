package ifmo.lab.server.commands.list;

import ifmo.lab.server.commands.Command;
import ifmo.lab.server.controller.ProductController;
import ifmo.lab.server.exceptions.ArgumentException;
import ifmo.lab.server.services.HistoryManager;

public class HistoryCommand implements Command {
    private final HistoryManager history;

    public HistoryCommand(HistoryManager history) {
        this.history = history;
    }

    @Override
    public void execute(String[] args) {

        if (args.length > 1) {
            throw new ArgumentException("Команда не должна содержать аргументов");
        }
        history.getHistoryListOfCommands().forEach(System.out::println);
    }

    @Override
    public String description() {
        return "вывести последние 9 команд (без их аргументов)";
    }
}
