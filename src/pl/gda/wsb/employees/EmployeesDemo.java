package pl.gda.wsb.employees;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmployeesDemo {

    static String companyName = "WSB Gdańsk";

    static ArrayList<Employee> employees = new ArrayList<>();
    static ArrayList<Employee> loggedEmployees = new ArrayList<>();
    private static DataBase dataBase;
    private static EmployeeRepository employeeRepository;

    public static void main(String[] args) {
        dataBase = new DataBase();
        employeeRepository = new EmployeeRepository();

        Scanner fileScanner = dataBase.getFileScanner();
        if (fileScanner == null) return;

        Pattern pattern = Pattern.compile("^(true|false) - (.+) - (.+)$");
        while (fileScanner.hasNextLine()) {
            String line_from_file = fileScanner.nextLine();
            Matcher matcher = pattern.matcher(line_from_file);
            if (matcher.matches()) {
                Employee employee = new Employee(
                        Boolean.parseBoolean(matcher.group(1)),
                        matcher.group(2),
                        matcher.group(3)
                );
                System.out.println(employee.toString());

                employeeRepository.getEmployees().add(employee);
                if (Boolean.parseBoolean(matcher.group(1))) {
                    employeeRepository.getEmployees(true).add(employee);
                }
            }
        }

        EmployeePrinter.printWelcomeText();

        EmployeePrinter.printEmployees(employeeRepository.getEmployees());

        EmployeePrinter.printLoggedEmployees(employeeRepository.getEmployees(true));

        employeeRepository.readEmployeeNameAndChangeStatus(employeeRepository.getEmployees());

    }

}
