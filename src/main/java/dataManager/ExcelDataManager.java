package dataManager;

import excel.ExcelReader;

import java.io.IOException;
import java.util.ArrayList;

public class ExcelDataManager {

    private final ArrayList<ArrayList<Double>> dataArray;

    public ExcelDataManager(String path, int index) throws IOException {
        ExcelReader excelReader = new ExcelReader(path, index);
        this.dataArray = excelReader.readExcel();
    }

    public ArrayList<ArrayList<Double>> getDataArray() {
        return dataArray;
    }
}
