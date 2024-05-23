package gui;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Sheets {

    private FileInputStream file;
    private XSSFWorkbook workbook;
    private ArrayList<String> names;

    public Sheets(String filePath) {
        try {
            file = new FileInputStream(filePath);
            workbook = new XSSFWorkbook(file);
            names = namesSheets();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Ошибка: Файл не найден");
            throw new RuntimeException(e);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Ошибка: Ошибка при чтении книги Excel");
            throw new RuntimeException(e);
        } catch (org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException e) {
            JOptionPane.showMessageDialog(null, "Ошибка: Неверный формат файла");
            names = new ArrayList<>();
        } finally {
            try {
                if (file != null) {
                    file.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private ArrayList<String> namesSheets() {
        ArrayList<String> sheetNames = new ArrayList<>();
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            XSSFSheet sheet = workbook.getSheetAt(i);
            sheetNames.add(sheet.getSheetName());
        }
        return sheetNames;
    }

    public String[] getNames() {
        return names.toArray(new String[0]);
    }
}
