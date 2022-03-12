package model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class IOClass {
    private static String filename = "login_activity.txt";

    public static void insertLog(String logString) throws IOException {
        FileWriter fileWriter = new FileWriter(filename, true);
        PrintWriter outputFile = new PrintWriter(fileWriter);
        outputFile.println(logString);
        outputFile.close();
    }

}
