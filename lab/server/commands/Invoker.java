package ifmo.lab.server.commands;

import ifmo.lab.client.CurrentUserManager;
import ifmo.lab.server.commands.list.*;
import ifmo.lab.server.controller.ProductController;
import ifmo.lab.server.controller.ProductControllerImpl;
import ifmo.lab.server.exceptions.ArgumentException;
import ifmo.lab.server.exceptions.FileException;
import ifmo.lab.server.exceptions.ValidationException;
import ifmo.lab.server.services.HistoryManager;
import ifmo.lab.server.services.ScriptManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import static ifmo.lab.server.utils.Parser.tildaResolver;


/**
 * Класс инвокер для паттерна команды. Вызывает метод execute от введеной строки, валидирует её, отлавливает ошибки возникающие при выполнении команд.
 * Содержит Map, где ключ -- название, а значение -- класс команды.
 *
 */
public class Invoker {
    private static Map<String, Command> commandMap = new HashMap<>();
    private final ProductController controller;
    private final ScriptManager scriptManager;
    private final HistoryManager history;
    private final String fileName;
    private BufferedReader reader;
    private final CurrentUserManager userManager;

    /**
     * При создании сущности, создается контроллер, создается скрипт менеджер и обрабатывается путь до файла (filename)
     * Происходит инициализация Map'ы команд.
     *
     * @param filename the filename
     */
    public Invoker(String filename) {
        this.reader = reader == null ? new BufferedReader(new InputStreamReader(System.in)) : reader;
        this.fileName = tildaResolver(filename);
        controller = new ProductControllerImpl(fileName);
        scriptManager = new ScriptManager(null);
        this.history = new HistoryManager(9);
        userManager = new CurrentUserManager();
        init();
    }
    public Invoker(CurrentUserManager userManager) {
        this.reader = reader == null ? new BufferedReader(new InputStreamReader(System.in)) : reader;
        this.fileName = null;
        controller = new ProductControllerImpl(userManager);
        scriptManager = new ScriptManager(null);
        this.history = new HistoryManager(9);
        this.userManager = userManager;
        init();
    }

    /**
     * Метод инициализации команд, добавленные здесь, будут видны пользователю.
     */
    public void init() {
        addCommandToMap("help", new HelpCommand());
        addCommandToMap("info", new InfoCommand(controller));
        addCommandToMap("show", new ShowCommand(controller));
        addCommandToMap("insert", new InsertCommand(controller));
        addCommandToMap("update", new UpdateCommand(controller));
        addCommandToMap("remove_key", new RemoveByKeyCommand(controller));
        addCommandToMap("clear", new ClearCommand(controller));
        addCommandToMap("save", new SaveCommand(controller));
        addCommandToMap("execute_script", new ExecuteScriptCommand(this, scriptManager));
        addCommandToMap("exit", new ExitCommand());
        addCommandToMap("remove_lower", new RemoveLowerCommand(controller));
        addCommandToMap("history", new HistoryCommand(history));
        addCommandToMap("remove_greater_key", new RemoveGreaterKeyCommand(controller));
        addCommandToMap("filter_contains_name", new FilterContainsNameCommand(controller));
        addCommandToMap("filter_less_than_price", new FilterLessThanPriceCommand(controller));
        addCommandToMap("print_field_descending_part_number", new PrintFieldDescendingPartNumberCommand(controller));
    }

    /**
     * Метод для обработки введеной строки:
     *  если первый элемент (по разбиению на пробелы) совпадает с названим существующей команды, то вызывается .execute у соответствующей команды
     *  если нет, то "Команда не найдена."
     *
     * Обработка ошибок: при вызове .execute у команды, могут вылетать ошибки ArgumentException etc.
     * Тут они обрабатываются и выводятся пользователю.
     *
     * @param input the input
     */
    public void execute(String input) {
        try {
            input = input.trim();
            String[] commandArray = input.split(" ");
            String command = commandArray[0];
            for (Map.Entry<String, Command> pair : Invoker.getCommandMap().entrySet()) {
                if (pair.getKey().equals(command)) {
                    history.addCommandToHistory(command);
                    pair.getValue().execute(commandArray);
                }
            }
            if (!Invoker.getCommandMap().containsKey(command)) {
                System.out.println("Команда не найдена.");
            }
        } catch (ArgumentException | ValidationException | FileException e) {
            System.out.println(e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Нет аргумента.");
        } catch (NoSuchElementException e) {
            System.out.println("Завершение программы...");
            System.exit(1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets reader.
     *
     * @return the reader
     */
    public BufferedReader getReader() {
        return reader;
    }

    /**
     * Sets reader.
     *
     * @param reader the reader
     */
    public void setReader(BufferedReader reader) {
        this.reader = reader;
    }

    /**
     * Gets command map.
     *
     * @return the command map
     */
    public static Map<String, Command> getCommandMap() {
        return commandMap;
    }

    /**
     * Sets command map.
     *
     * @param commandMap the command map
     */
    public static void setCommandMap(Map<String, Command> commandMap) {
        Invoker.commandMap = commandMap;
    }

    /**
     * Add command to map.
     *
     * @param commandName the command name
     * @param command     the command
     */
    public void addCommandToMap(String commandName, Command command) {
        commandMap.put(commandName, command);
    }
}