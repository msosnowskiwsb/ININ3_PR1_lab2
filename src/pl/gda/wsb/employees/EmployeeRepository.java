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
            String employeeNameFromUser = inScanner.nextLine();
            if (employeeNameFromUser.equals("exit")) {

                dataBase.saveToFile(employeeList);
                break;
            }

            int i = 0;
            boolean searched = false;
            Pattern patternSearch = Pattern.compile("^(true|false) - " + employeeNameFromUser + " - (.+)$");

            for (Employee employee : getEmployees()) {
                Matcher matcher = patternSearch.matcher(employee.toString());
                if (matcher.matches()) {
                    searched = true;
                    boolean isLogged = Boolean.parseBoolean(matcher.group(1));
                    employeeList.get(i).setLogged(!isLogged);
                    //getEmployees().remove(i);
                   // getEmployees().add(i, employee.replace(matcher.group(1), isLogged ? "false" : "true"));
                    break;
                }
                i++;
            }

            if (searched) {
                System.out.println("Zmieniono status dla pracownika: " + employeeNameFromUser);
            } else {
                System.out.println("Błędnie podane imię i nazwisko!");
            }
        }
    }

    ArrayList<Employee> getEmployees(Boolean onlyLogged){
        return onlyLogged ? EmployeesDemo.loggedEmployees : EmployeesDemo.employees;
    }

    ArrayList<Employee> getEmployees(){
        return EmployeesDemo.employees;
    }
}
