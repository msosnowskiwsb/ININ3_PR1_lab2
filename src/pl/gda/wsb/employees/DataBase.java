package pl.gda.wsb.employees;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class DataBase {
    protected String fileName = System.getProperty("user.dir") + "\\utils\\db.txt";

    protected void saveToFile(ArrayList<Employee> employeesListToSave) {
        FileWriter fw = null;
        try {
            fw = new FileWriter(fileName, false);
            for (Employee employee : employeesListToSave) {
                fw.write(employee.toString() + "\n");
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("Błąd zapisu pliku!");
        }
    }

    protected Scanner getFileScanner() {
        File file = new File(fileName);
        Scanner fileScanner = null;
        try {
            fileScanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("Błąd pobrania pliku!");
            return null;
        }
        return fileScanner;
    }

    static String getOperatorName(){
        return "Mateusz";
    }
}
