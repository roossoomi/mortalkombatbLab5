package org.example;

import java.io.*;
import java.nio.file.*;

/**
 * Утилитарный класс для операций с файлами.
 */
public class FileUtil {

    /**
     * Копирует файл из ресурсов в указанный путь назначения, если он там отсутствует.
     *
     * @param resourcePath    путь к файлу ресурса для копирования
     * @param destinationPath путь назначения, куда будет скопирован файл
     */
    public static void copyFileFromResources(String resourcePath, String destinationPath) throws IOException {
        Path destination = Paths.get(destinationPath);
        if (!Files.exists(destination)) {
            try (InputStream resourceStream = FileUtil.class.getResourceAsStream(resourcePath)) {
                if (resourceStream == null) {
                    throw new FileNotFoundException("Ресурс " + resourcePath + " не найден.");
                }
                Files.copy(resourceStream, destination);
            }
        }
    }
}
