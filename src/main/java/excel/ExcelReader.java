package excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;


public class ExcelReader {

    FileInputStream file;
    XSSFWorkbook workbook;
    XSSFSheet sheet;
    String filePath;

    public ExcelReader(String filePath, int index) throws IOException {
        file = new FileInputStream(filePath);
        workbook = new XSSFWorkbook(file);
        sheet = workbook.getSheetAt(index);
        this.filePath = filePath;
    }

    public ArrayList<ArrayList<Double>> readExcel() {
        ArrayList<ArrayList<Double>> result = new ArrayList<>();
        try {
            Iterator<Row> rowIterator = sheet.iterator();
            if (rowIterator.hasNext()) {
                Row headerRow = rowIterator.next();
                int numberOfColumns = headerRow.getLastCellNum();

                for (int i = 0; i < numberOfColumns; i++) {
                    result.add(new ArrayList<>());
                }

                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    for (int i = 0; i < numberOfColumns; i++) {
                        Cell cell = row.getCell(i);
                        if (cell != null) {
                            if (cell.getCellType() == CellType.NUMERIC) {
                                result.get(i).add(cell.getNumericCellValue());
                            }
                        }
                    }
                }
            }
            workbook.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}

