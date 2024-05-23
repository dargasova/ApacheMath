package gui;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.net.URISyntaxException;

public class JFileChooser extends Component {

    private final javax.swing.JFileChooser fileChooser;


    public JFileChooser() throws URISyntaxException {
        try {
            File currentDirectory = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile();
            fileChooser = new javax.swing.JFileChooser(currentDirectory);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public String openFile() {
        fileChooser.setDialogTitle("Выбор файла");
        fileChooser.setFileSelectionMode(javax.swing.JFileChooser.FILES_ONLY);

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Files", "xlsx");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(null);
        if (result == javax.swing.JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            if (selectedFile != null) {
                JOptionPane.showMessageDialog(null, "Выбранный файл: " + selectedFile.getName() + "\nПуть: " + selectedFile.getAbsolutePath());
                return selectedFile.getAbsolutePath();
            }
        }
        return null;
    }
}


