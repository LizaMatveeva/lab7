package ifmo.lab.server.validation;

import ifmo.lab.server.exceptions.FileException;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

import static ifmo.lab.server.utils.Parser.tildaResolver;

/**
 * The type Validation.
 */
public class Validation {
    /**
     * Validate file exist.
     *
     * @param file the file
     */
    public static void validateFileExist(File file) {
        if (!Files.exists(file.toPath())) {
            throw new FileException("Файл не существует.");
        }
    }

    /**
     * Validate file read.
     *
     * @param file the file
     */
    public static void validateFileRead(File file) {
        if (!Files.isReadable(file.toPath())) {
            throw new FileException("Файл недоступен для чтения.");
        }
    }

    /**
     * Validate file write.
     *
     * @param file the file
     */
    public static void validateFileWrite(File file) {
        if (!Files.isWritable(file.toPath())) {
            throw new FileException("Файл недоступен для записи.");
        }
    }

    /**
     * Validate file name.
     *
     * @param fileName the file name
     */
    public static void validateFileName(String fileName) {
        try {
            (new File(fileName.replace("~", ""))).toPath();
        } catch (InvalidPathException e) {
            throw new FileException("Некорректное имя файла.");
        }
    }

    /**
     * Validate file directory.
     *
     * @param fileName the file name
     */
    public static void validateFileDirectory(String fileName) {
        if (Files.isDirectory(Paths.get(fileName))) {
            throw new FileException("Файл является директорией.");
        }
    }

    /**
     * Validate file.
     *
     * @param fileName the file name
     */
    public static void validateFile(String fileName) {
        fileName = tildaResolver(fileName);
        validateFileName(fileName);
        File file = new File(fileName);
        validateFileDirectory(fileName);
        validateFileExist(file);
        validateFileRead(file);
        validateFileWrite(file);
    }



    /**
     * Validate name boolean.
     *
     * @param name the name
     * @return the boolean
     */
    public static boolean validateName(String name) {
        return (name != null && !name.isBlank());
    }

    /**
     * Validate id boolean.
     *
     * @param id the id
     * @return the boolean
     */
    public boolean validateId(Long id) {
        return (id != null && id > 0);
    }
}
