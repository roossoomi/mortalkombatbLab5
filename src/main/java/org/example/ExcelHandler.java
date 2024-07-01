package org.example;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Класс ExcelHandler предназначен для работы с Excel файлом Results.xlsx.
 * Поддерживает операции записи и чтения данных из Excel файла.
 */
public class ExcelHandler {

    /** Путь к ресурсному файлу Results.xlsx */
    private static final String RESOURCE_PATH = "/Results.xlsx";

    /** Путь для сохранения файла Results.xlsx в рабочей директории */
    private static final String DESTINATION_PATH = "Results.xlsx";

    /**
     * Конструктор класса ExcelHandler. При инициализации копирует файл Results.xlsx
     * из ресурсов в рабочую директорию, если он там отсутствует.
     */
    public ExcelHandler() throws IOException {
        FileUtil.copyFileFromResources(RESOURCE_PATH, DESTINATION_PATH);
    }

    /**
     * Возвращает путь к файлу Results.xlsx в рабочей директории.
     *
     * @return объект типа Path, представляющий путь к файлу Results.xlsx
     */
    private Path getExcelFilePath() {
        return Paths.get(DESTINATION_PATH);
    }

    /**
     * Возвращает поток InputStream для файла Results.xlsx.
     *
     */
    private InputStream getExcelFileAsStream() throws FileNotFoundException {
        Path path = getExcelFilePath();
        if (!Files.exists(path)) {
            throw new FileNotFoundException("Файл Results.xlsx не найден в рабочей директории");
        }
        return new FileInputStream(path.toFile());
    }

    /**
     * Записывает данные результатов в Excel файл Results.xlsx.
     *
     * @param results список объектов Result для записи в Excel
     */
    public void writeToExcel(ArrayList<Result> results) throws IOException {
        try (XSSFWorkbook book = new XSSFWorkbook()) {
            XSSFSheet sheet = book.createSheet("Результаты ТОП 10");
            XSSFRow r = sheet.createRow(0);
            r.createCell(0).setCellValue("№");
            r.createCell(1).setCellValue("Имя");
            r.createCell(2).setCellValue("Количество баллов");
            for (int i = 0; i < results.size(); i++) {
                if (i < 10) {
                    XSSFRow r2 = sheet.createRow(i + 1);
                    r2.createCell(0).setCellValue(i + 1);
                    r2.createCell(1).setCellValue(results.get(i).getName());
                    r2.createCell(2).setCellValue(results.get(i).getPoints());
                }
            }
            // Сохраняем файл в рабочую директорию
            Path filePath = getExcelFilePath();
            try (FileOutputStream fos = new FileOutputStream(filePath.toFile())) {
                book.write(fos);
            }
        }
    }

    /**
     * Считывает данные из Excel файла Results.xlsx и возвращает список объектов Result.
     */
    public ArrayList<Result> readFromExcel() throws IOException {
        ArrayList<Result> results = new ArrayList<>();
        try (InputStream is = getExcelFileAsStream()) {
            try (XSSFWorkbook book = new XSSFWorkbook(is)) {
                XSSFSheet sh = book.getSheetAt(0);
                for (int i = 1; i <= sh.getLastRowNum(); i++) {
                    results.add(new Result(sh.getRow(i).getCell(1).getStringCellValue(), (int) sh.getRow(i).getCell(2).getNumericCellValue()));
                }
            }
        }
        return results;
    }
}
