package excel;

import calculator.Calculator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ExcelWriter {

    private final String outputPath;
    private final ArrayList<ArrayList<String>> results;
    private final ArrayList<ArrayList<Double>> cov;
    private final String nameCov;

    public ExcelWriter(String filePath, ArrayList<ArrayList<String>> results, Calculator calculator) {
        this.results = results;
        this.cov = calculator.getCovariation();
        this.nameCov = calculator.getCovariationName();
        this.outputPath = "output/Результат.xlsx";
    }

    private void ensureOutputDirectoryExists() {
        File directory = new File("output");
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public void writeIntoExcel() throws IOException {
        ensureOutputDirectoryExists();
        XSSFWorkbook workbook;
        File file = new File(outputPath);
        if (!file.exists()) {
            workbook = new XSSFWorkbook();
        } else {
            FileInputStream fileIn = new FileInputStream(outputPath);
            workbook = new XSSFWorkbook(fileIn);
            fileIn.close();
        }

        XSSFSheet sheet = workbook.getSheet("Результаты");
        if (sheet == null) {
            sheet = workbook.createSheet("Результаты");
        } else {
            clearSheet(sheet);
        }

        XSSFSheet sheetCov = workbook.getSheet("Ковариационная матрица");
        if (sheetCov == null) {
            sheetCov = workbook.createSheet("Ковариационная матрица");
        } else {
            clearSheet(sheetCov);
        }

        sheet.setColumnWidth(0, 25 * 256);
        for (int i = 1; i < results.getFirst().size(); i++) {
            sheet.setColumnWidth(i, 40 * 256);
        }

        for (int i = 0; i < cov.getFirst().size(); i++) {
            sheetCov.setColumnWidth(i, 15 * 256);
        }

        writeResults(sheet);
        writeCovariation(sheetCov);

        FileOutputStream fileOut = new FileOutputStream(outputPath);
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();
    }


    public void clearSheet(XSSFSheet sheet) {
        int lastRowNum = sheet.getLastRowNum();
        for (int i = lastRowNum; i >= 0; i--) {
            Row row = sheet.getRow(i);
            if (row != null) {
                sheet.removeRow(row);
            }
        }
    }


    private void writeResults(XSSFSheet sheet) {
        int rowIndex = 0;
        for (ArrayList<String> sublist : results) {
            Row row = sheet.createRow(rowIndex++);
            int colIndex = 0;
            for (String element : sublist) {
                Cell cell = row.createCell(colIndex++);
                cell.setCellValue(element);
            }
        }
    }


    private void writeCovariation(XSSFSheet sheetCov) {
        int rowCov = 0;
        Row row2 = sheetCov.createRow(rowCov++);
        Cell name = row2.createCell(0);
        name.setCellValue(nameCov);

        for (ArrayList<Double> row : cov) {
            Row row1 = sheetCov.createRow(rowCov++);
            int colCov = 0;
            for (Double el : row) {
                Cell cell = row1.createCell(colCov++);
                cell.setCellValue(el);
            }
        }
    }
}