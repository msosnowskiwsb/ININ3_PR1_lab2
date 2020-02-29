package pl.gda.wsb.employees;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmployeesDemo {

    static String companyName = "WSB Gdańsk";

    static ArrayList<String> employees = new ArrayList<>();
    static ArrayList<String> loggedEmployees = new ArrayList<>();
    private static DataBase dataBase;
    private static EmployeeRepository employeeRepository;

    public static void main(String[] args) {
        dataBase = new DataBase();
        employeeRepository = new EmployeeRepository();

        Scanner fileScanner = dataBase.getFileScanner();
        if (fileScanner == null) return;

        Pattern pattern = Pattern.compile("^(true|false) - (.+)$");
        while (fileScanner.hasNextLine()) {
            String employee = fileScanner.nextLine();
            Matcher matcher = pattern.matcher(employee);
            if (matcher.matches()) {
                employeeRepository.getEmployees().add(employee);
                if (Boolean.parseBoolean(matcher.group(1))) {
                    employeeRepository.getEmployees(true).add(matcher.group(2));
                }
            }
        }

        printWelcomeText();

        printEmployees();

        printLoggedEmployees();

        employeeRepository.readEmployeeNameAndChangeStatus();

    }

    private static void printLoggedEmployees() {
        if (employeeRepository.getEmployees(true).size() > 0) {
            System.out.println("Zalogowani użytkownicy: " + employeeRepository.getEmployees(true).size());
            int i = 0;
            for (String employee : employeeRepository.getEmployees(true)) {
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
        if (employeeRepository.getEmployees().size() == 0) {
            System.out.println("Brak pracowników.");
        } else {
            System.out.println("Liczba pracowników: " + employeeRepository.getEmployees().size());
        }

        if (employeeRepository.getEmployees().size() > 0) {
            int i = 0;
            for (String employee : employeeRepository.getEmployees()) {
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
                .append("Hello ").append(DataBase.getOperatorName()).append("\n")
                .append("Aktualna data: ").append(new Date()).append("\n");
        System.out.println(stringBuilder);
    }

}
