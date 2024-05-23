package gui;

import excel.ExcelWriter;
import calculator.Calculator;
import org.apache.commons.math3.exception.MathIllegalArgumentException;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class GUI extends JFrame {

    JButton openButton = new JButton("Открыть файл");
    JButton saveButton = new JButton("Сохранить файл");
    JButton calculateButton = new JButton("Провести расчеты");
    JButton exitButton = new JButton("Выйти из программы");
    JComboBox<String> comboBox = new JComboBox<>();
    dataManager.ExcelDataManager data;
    Calculator calculator = new Calculator();
    gui.JFileChooser jfilechooser = new gui.JFileChooser();
    String path;
    ExcelWriter excelWriter;
    ArrayList<ArrayList<Double>> list;
    ArrayList<ArrayList<?>> results = new ArrayList<>();
    GridBagConstraints gbc = new GridBagConstraints();

    public GUI() throws URISyntaxException {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            System.err.println("Не удалось настроить внешний вид системы");
        }

        setLayout(new GridBagLayout());
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;

        setPreferredSize(new Dimension(800, 600));

        addComponent(openButton, 0);
        addComponent(comboBox, 1);
        addComponent(calculateButton, 2);
        addComponent(saveButton, 3);
        addComponent(exitButton, 4);

        Dimension buttonSize = new Dimension(300, 60);
        openButton.setPreferredSize(buttonSize);
        saveButton.setPreferredSize(buttonSize);
        calculateButton.setPreferredSize(buttonSize);
        exitButton.setPreferredSize(buttonSize);
        Dimension comboBoxSize = new Dimension(300, 40);
        comboBox.setPreferredSize(comboBoxSize);

        Color buttonColor = new Color(0, 128, 255);
        openButton.setBackground(buttonColor);
        openButton.setForeground(Color.WHITE);
        saveButton.setBackground(buttonColor);
        saveButton.setForeground(Color.WHITE);
        calculateButton.setBackground(buttonColor);
        calculateButton.setForeground(Color.WHITE);
        exitButton.setBackground(buttonColor);
        exitButton.setForeground(Color.WHITE);

        openButton.addActionListener(e -> {
            try {
                jfilechooser = new JFileChooser();
            } catch (URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
            path = jfilechooser.openFile();

            if (path == null) {
                JOptionPane.showMessageDialog(null, "Файл не выбран", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }

            comboBox.removeAllItems();
            for (String sheet : new Sheets(path).getNames()) {
                comboBox.addItem(sheet);
            }
        });


        calculateButton.addActionListener(e -> {
            try {
                data = new dataManager.ExcelDataManager(path, comboBox.getSelectedIndex());
                list = data.getDataArray();

                if (list.isEmpty() || list.getFirst().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Лист данных пуст или содержит недостаточно информации для расчетов", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                calculator.calculate(list);
                results = calculator.fillResults();
                JOptionPane.showMessageDialog(null, "Расчеты выполнены успешно!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Ошибка при чтении данных из файла: " + ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            } catch (MathIllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, "Ошибка при расчетах: " + ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Произошла непредвиденная ошибка: " + ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });


        saveButton.addActionListener(e -> {
            try {
                if (results.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Нет данных для сохранения", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                excelWriter = new ExcelWriter(path, results, calculator);
                JOptionPane.showMessageDialog(null, "Файл успешно сохранен!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Ошибка при сохранении файла: " + ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Произошла непредвиденная ошибка: " + ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });


        exitButton.addActionListener(e -> System.exit(0));

        setTitle("GUI");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
        pack();
    }


    private void addComponent(Component component, int gridy) {
        gbc.gridx = 0;
        gbc.gridy = gridy;
        add(component, gbc);
    }

    public static void main(String[] args) throws URISyntaxException {
        new GUI();
    }
}