package pl.gda.wsb.employees;

public class Director extends Employee {

    public Director(boolean logged, String name) {
        super(logged, name);
    }

    @Override
    public String getPosition() {
        return "dyrektor";
    }
}
