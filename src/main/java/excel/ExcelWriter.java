package excel;

import calculator.Calculator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ExcelWriter {

    private final String outputPath;
    private final ArrayList<ArrayList<?>> results;
    private final ArrayList<ArrayList<Double>> cov;
    private final String nameCov;

    public ExcelWriter(String filePath, ArrayList<ArrayList<?>> result, Calculator calculator) throws IOException {
        this.results = result;
        this.cov = calculator.getCovariation();
        this.nameCov = calculator.getCovariationName();
        this.outputPath = "output/Результат.xlsx";

        ensureOutputDirectoryExists();
        writeIntoExcel();
    }

    private void ensureOutputDirectoryExists() {
        File directory = new File("output");
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    private void writeIntoExcel() {
        try (XSSFWorkbook workbook = new XSSFWorkbook();
             FileOutputStream fileOut = new FileOutputStream(outputPath)) {

            XSSFSheet sheet = workbook.createSheet("Результаты");
            XSSFSheet sheetCov = workbook.createSheet("Ковариационная матрица");

            writeResults(sheet);
            writeCovariation(sheetCov);

            workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeResults(XSSFSheet sheet) {
        int rowIndex = 0;
        for (ArrayList<?> sublist : results) {
            Row row = sheet.createRow(rowIndex++);
            int colIndex = 0;
            for (Object element : sublist) {
                Cell cell = row.createCell(colIndex++);
                if (element instanceof Double) {
                    cell.setCellValue((Double) element);
                } else {
                    cell.setCellValue(element.toString());
                }
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
