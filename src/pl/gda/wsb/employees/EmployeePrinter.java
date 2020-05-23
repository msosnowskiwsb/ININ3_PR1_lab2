package pl.gda.wsb.employees;

import java.util.ArrayList;
import java.util.Date;

import static pl.gda.wsb.employees.EmployeesDemo.companyName;

public class EmployeePrinter {

    static EmployeeRepository employeeRepository = new EmployeeRepository();

    static void printLoggedEmployees(ArrayList<Employee> loggedEmployees) {
        if (employeeRepository.getEmployees(true).size() > 0) {
            System.out.println("Zalogowani użytkownicy: " + employeeRepository.getEmployees(true).size());
            int i = 0;
            for (Employee employee : employeeRepository.getEmployees(true)) {
                if (i == 5) {
                    System.out.println("...");
                    break;
                }
                i++;
                System.out.println(employee.toString());
            }
        }
    }

    static void printEmployees(ArrayList<Employee> allEmployess) {
        if (employeeRepository.getEmployees().size() == 0) {
            System.out.println("Brak pracowników.");
        } else {
            System.out.println("Liczba pracowników: " + employeeRepository.getEmployees().size());
        }

        if (employeeRepository.getEmployees().size() > 0) {
            int i = 0;
            for (Employee employee : employeeRepository.getEmployees()) {
                if (i == 5) {
                    System.out.println("...");
                    break;
                }
                i++;
                System.out.println(employee.toString());
            }
        }
    }

    static void printWelcomeText() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(companyName).append("\n")
                .append("Hello ").append(DataBase.getOperatorName()).append("\n")
                .append("Aktualna data: ").append(new Date()).append("\n");
        System.out.println(stringBuilder);
    }
}
