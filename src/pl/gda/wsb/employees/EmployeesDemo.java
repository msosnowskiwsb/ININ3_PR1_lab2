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

                boolean status = Boolean.parseBoolean(matcher.group(1));
                String employeeName = matcher.group(2);
                String position = matcher.group(3);

                switch (position) {
                    case "dyrektor": {
                        Employee employee = new Director(status, employeeName);
                        employeeRepository.getEmployees().add(employee);
                        if (status) {
                            employeeRepository.getEmployees(true).add(employee);
                        }
                        break;
                    } case "handlowiec": {
                        Employee employee = new Seller(status, employeeName);
                        employeeRepository.getEmployees().add(employee);
                        if (status) {
                            employeeRepository.getEmployees(true).add(employee);
                        }
                        break;
                    } case "kierowca": {
                        Employee employee = new Driver(status, employeeName);
                        employeeRepository.getEmployees().add(employee);
                        if (status) {
                            employeeRepository.getEmployees(true).add(employee);
                        }
                        break;
                    }
                }

            }
        }

        EmployeePrinter.printWelcomeText();

        EmployeePrinter.printEmployees(employeeRepository.getEmployees());

        EmployeePrinter.printLoggedEmployees(employeeRepository.getEmployees(true));

        employeeRepository.readEmployeeNameAndChangeStatus(employeeRepository.getEmployees());

    }


}
