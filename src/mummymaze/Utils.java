package mummymaze;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Utils {
    public static boolean isElementOnLimit(Position elementPosition) {
        int line = elementPosition.getLine();
        int column = elementPosition.getColumn();
        return (line == 1 || line == Constants.SIZE - 2 || column == 1 ||
                column == Constants.SIZE - 2);
    }

    public static void appendToTextFile(String fileName, String string) {
        BufferedWriter w = null;
        try {
            w = new BufferedWriter(new FileWriter(fileName, true));
            w.write(string);

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
}
