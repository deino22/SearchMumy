package mummymaze;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class StatisticsFile extends JFrame {
    private File file;
    private File levelFolderFile;


    private int selectLevelFolder() {
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File("."));
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setDialogTitle("Select levels folder");
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            levelFolderFile = fc.getSelectedFile();
        }
        return returnVal;
    }

    public StatisticsFile() {
        file = null;
        levelFolderFile = null;
        int returnSelectLevelFolderVal = selectLevelFolder();
        if (returnSelectLevelFolderVal == JFileChooser.APPROVE_OPTION) {

            final JFileChooser fc = new JFileChooser();
            fc.setDialogTitle("Save statistics file");
            fc.setCurrentDirectory(new File("."));

            int returnVal = fc.showSaveDialog(this);
            String excelExt = ".xls";
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                if (fc.getSelectedFile().exists()) {
                    int confirmVal = JOptionPane.showConfirmDialog(
                            this,
                            "File already exists do you want to overwrite?"
                    );
                    if (confirmVal == JOptionPane.OK_OPTION) {
                        file = fc.getSelectedFile();
                        clearFile();
                    }
                } else {
                    if (!fc.getSelectedFile()
                            .getAbsolutePath()
                            .endsWith(excelExt)) {
                        file = new File(fc.getSelectedFile() + excelExt);
                    } else {
                        file = fc.getSelectedFile();
                    }
                }
            }
        }
    }


    public void clearFile() {
        BufferedWriter w = null;
        try {
            w = new BufferedWriter(new FileWriter(file));
            w.write("");

        } catch (Exception e) {
            System.err.println("Error: " + e);
        } finally {
            try {
                if (w != null) {
                    w.close();
                }
            } catch (IOException ignore) {
            }
        }
    }

    public void appendStringListToFile(List<String> strings) {
        BufferedWriter w = null;
        try {
            w = new BufferedWriter(new FileWriter(file, true));
            for (String string : strings) {
                w.write(string);
            }

        } catch (Exception e) {
            System.err.println("Error: " + e);
        } finally {
            try {
                if (w != null) {
                    w.close();
                }
            } catch (IOException ignore) {
            }
        }
    }

    public File getFile() {
        return file;
    }

    public File getLevelFolderFile() {
        return levelFolderFile;
    }
}
