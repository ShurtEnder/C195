package model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * IO Class.
 * @author Rene Gomez Student ID: 001467443
 */
public class IOClass {
    /** Filename. Creates a string to point the logs to.*/
    private static String filename = "login_activity.txt";

    /**
     * Insert Logs. Inserts logs into a file.
     * @param logString the log string to set to.
     * @throws IOException
     */
    public static void insertLog(String logString) throws IOException {
        FileWriter fileWriter = new FileWriter(filename, true);
        PrintWriter outputFile = new PrintWriter(fileWriter);
        outputFile.println(logString);
        outputFile.close();
    }

}
