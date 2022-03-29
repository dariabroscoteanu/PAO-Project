package UserServices;

import UserClasses.Address;
import UserClasses.Costumer;
import UserClasses.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeeService {
    private List<Employee> employees = new ArrayList<>();
    private static EmployeeService instance;

    private EmployeeService(){}

    public static EmployeeService getInstance(){
        if(instance == null){
            instance = new EmployeeService();
        }
        return instance;
    }

    public List<Employee> getEmployees() {
        List<Employee> employeesCopy = new ArrayList<>();
        employeesCopy.addAll(this.employees);
        return employeesCopy;
    }

    public Employee getEmployeeById(int index){
        Employee employee = new Employee();
        for(int i = 0; i < this.employees.size(); ++i){
            if(this.employees.get(i).getId() == index){
                employee = this.employees.get(i);
            }
        }
        return employee;
    }

    public void updateEmployee(int index, Employee employee){
        for(int i = 0; i < this.employees.size(); ++i){
            if(this.employees.get(i).getId() == index){
                this.employees.remove(i);
                this.employees.add(index, employee);
                break;
            }
        }
    }

    public void addEmployees(Employee employee){
        this.employees.add(employee);
    }

    public void deleteEmployeeById(int index){
        for(int i = 0; i < this.employees.size(); ++i){
            if(this.employees.get(i).getId() == index){
                this.employees.remove(i);
                break;
            }
        }
    }

    public void deteleEmployees(Employee employee){
        for(int i = 0;i < this.employees.size(); ++i){
            if(this.employees.get(i).equals(employee)){
                this.employees.remove(i);
                break;
            }
        }
    }

    public Employee readEmployee(){
        Scanner scanner = new Scanner(System.in);
        AddressService addressService = AddressService.getInstance();
        Employee employee = new Employee();
        System.out.println("Id");
        employee.setId(scanner.nextInt());

        System.out.println("Name");
        employee.setName(scanner.next());

        System.out.println("Email");
        employee.setEmail(scanner.next());

        System.out.println("Address");
        Address address = addressService.readAddress();
        employee.setAddress(address);

        System.out.println("Position");
        employee.setPosition(scanner.next());

        System.out.println("Salary");
        employee.setSalary(scanner.nextDouble());

        return employee;
    }
}
