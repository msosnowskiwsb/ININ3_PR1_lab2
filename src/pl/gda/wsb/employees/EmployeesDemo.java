package pl.gda.wsb.employees;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmployeesDemo {

    static String companyName = "WSB Gdańsk";
    static String fileName = System.getProperty("user.dir") + "\\utils\\db.txt";

    static ArrayList<String> employees = new ArrayList<>();
    static ArrayList<String> loggedEmployees = new ArrayList<>();

    public static void main(String[] args) {
        String operatorName = args[0];

        Scanner fileScanner = getFileScanner();
        if (fileScanner == null) return;

        Pattern pattern = Pattern.compile("^(true|false) - (.+)$");
        while (fileScanner.hasNextLine()) {
            String employee = fileScanner.nextLine();
            Matcher matcher = pattern.matcher(employee);
            if (matcher.matches()) {
                employees.add(employee);
                if (Boolean.parseBoolean(matcher.group(1))) {
                    loggedEmployees.add(matcher.group(2));
                }
            }
        }

        printWelcomeText();

        printEmployees();

        printLoggedEmployees();


        System.out.println("\nPodaj imię i nazwisko (exit = koniec): ");
        Scanner inScanner = new Scanner(System.in);
        while (inScanner.hasNextLine()) {
            String text = inScanner.nextLine();
            if (text.equals("exit")) {

                FileWriter fw = null;
                try {
                    fw = new FileWriter(fileName, false);
                    for (String employee : getEmployees()) {
                        fw.write(employee + "\n");
                    }
                    fw.close();
                } catch (IOException e) {
                    System.out.println("Błąd zapisu pliku!");
                }
                break;
            }

            int i = 0;
            boolean searched = false;
            Pattern patternSearch = Pattern.compile("^(true|false) - " + text + " - (.+)$");

            for (String employee : getEmployees()) {
                Matcher matcher = patternSearch.matcher(employee);
                if (matcher.matches()) {
                    searched = true;
                    boolean isLogged = Boolean.parseBoolean(matcher.group(1));
                    getEmployees().remove(i);
                    getEmployees().add(i, employee.replace(matcher.group(1), isLogged ? "false" : "true"));
                    break;
                }
                i++;
            }

            if (searched) {
                System.out.println("Zmieniono status dla pracownika: " + text);
            } else {
                System.out.println("Błędnie podane imię i nazwisko!");
            }
        }

    }

    private static void printLoggedEmployees() {
        if (getEmployees(true).size() > 0) {
            System.out.println("Zalogowani użytkownicy: ");
            int i = 0;
            for (String employee : getEmployees(true)) {
                if (i == 5) {
                    System.out.println("...");
                    break;
                }
                i++;
                System.out.println(employee);
            }
        }
    }

    private static void printEmployees() {
        if (getEmployees().size() == 0) {
            System.out.println("Brak pracowników.");
        } else {
            System.out.println("Liczba pracowników: " + getEmployees().size());
        }

        if (getEmployees().size() > 0) {
            int i = 0;
            for (String employee : getEmployees()) {
                if (i == 5) {
                    System.out.println("...");
                    break;
                }
                i++;
                System.out.println(employee);
            }
        }
    }

    private static void printWelcomeText() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(companyName).append("\n")
                .append("Hello ").append(getOperatorName()).append("\n")
                .append("Aktualna data: ").append(new Date()).append("\n");
        System.out.println(stringBuilder);
    }

    private static Scanner getFileScanner() {
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

    private static ArrayList<String> getEmployees(Boolean onlyLogged){
        return onlyLogged ? loggedEmployees : employees;
    }

    private static ArrayList<String> getEmployees(){
        return employees;
    }

    private static String getOperatorName(){
        return "Mateusz";
    }
}
