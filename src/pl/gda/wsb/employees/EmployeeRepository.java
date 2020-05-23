package pl.gda.wsb.employees;

import netscape.security.UserTarget;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmployeeRepository {
    DataBase dataBase = new DataBase();

    void readEmployeeNameAndChangeStatus(ArrayList<Employee> employeeList) {
        System.out.println("\nPodaj imię i nazwisko (exit = koniec): ");
        Scanner inScanner = new Scanner(System.in);
        while (inScanner.hasNextLine()) {

            try {
                Employee foundEmployee = searchEmployee(employeeList,inScanner.nextLine());
                foundEmployee.setLogged(!foundEmployee.isLogged());
                System.out.println("Zmieniono status dla pracownika " + foundEmployee.getName() + " na " +
                        foundEmployee.isLogged() + ".\n\n Podaj imię i nazwisko (exit = koniec):");
            } catch (WrongEmployee wrongEmployee) {
                //wrongEmployee.printStackTrace();
            }
        }
    }

    private Employee searchEmployee(ArrayList<Employee> employeeList, String employeeNameFromUser) throws WrongEmployee{
        if (employeeNameFromUser.equals("exit")) {
            dataBase.saveToFile(employeeList);
            System.exit(0);
        }

        Pattern patternSearch = Pattern.compile("^(true|false) - " + employeeNameFromUser + " - (.+)$");

        for (Employee employee : getEmployees()) {
            Matcher matcher = patternSearch.matcher(employee.toString());
            if (matcher.matches()) {
                return employee;
            }
        }
        throw new WrongEmployee();
    }

    ArrayList<Employee> getEmployees(Boolean onlyLogged){
        return onlyLogged ? EmployeesDemo.loggedEmployees : EmployeesDemo.employees;
    }

    ArrayList<Employee> getEmployees(){
        return EmployeesDemo.employees;
    }
}
