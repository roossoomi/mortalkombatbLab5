package org.example;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ExcelHandler {

    private File getExcelFile() throws URISyntaxException, FileNotFoundException {
        URL resource = getClass().getClassLoader().getResource("Results.xlsx");
        if (resource == null) {
            throw new FileNotFoundException("Файл Results.xlsx не найден в ресурсах");
        }
        return Paths.get(resource.toURI()).toFile();
    }

    public void writeToExcel(ArrayList<Result> results) throws IOException, URISyntaxException {
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
            // Сохраняем файл в папке resources
            File file = getExcelFile();
            try (FileOutputStream fos = new FileOutputStream(file)) {
                book.write(fos);
            }
        }
    }

    public ArrayList<Result> readFromExcel() throws IOException, URISyntaxException {
        ArrayList<Result> results = new ArrayList<>();
        File file = getExcelFile();
        try (InputStream is = new FileInputStream(file)) {
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
