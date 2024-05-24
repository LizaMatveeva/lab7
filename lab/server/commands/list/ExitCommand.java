package ifmo.lab.server.commands.list;

import ifmo.lab.server.commands.Command;
import ifmo.lab.server.exceptions.ArgumentException;

/**
 * The type Exit command.
 */
public class ExitCommand implements Command {

    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            throw new ArgumentException("Команда не должна содержать аргументов.");
        }
    }

    @Override
    public String description() {
        return "завершить программу.";
    }
}
