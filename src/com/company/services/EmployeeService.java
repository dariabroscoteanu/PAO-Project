package com.company.services;

import com.company.entities.Address;
import com.company.entities.Employee;
import com.company.entities.Ransomeware;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class EmployeeService implements EmployeeInterface, CSVReaderWriter<Employee> {
    private List<Employee> employees = new ArrayList<>();
    private static EmployeeService instance;

    private EmployeeService(){
        employees = read();
    }

    public static EmployeeService getInstance(){
        if(instance == null){
            instance = new EmployeeService();
        }
        return instance;
    }

    public List<Employee> getEmployees() {
        List<Employee> employeesCopy = new ArrayList<>();
        employeesCopy.addAll(this.employees);
        Collections.sort(employeesCopy, new EmployeeSalaryComparator());
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
                this.employees.add(i, employee);
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
        try {
            employee.setId(scanner.nextInt());
        } catch (Exception e){
            System.out.println("Provide int");
            employee.setId(scanner.nextInt());
        }

        System.out.println("Name");
        employee.setName(scanner.nextLine());

        System.out.println("Email");
        employee.setEmail(scanner.nextLine());

        System.out.println("Address");
        Address address = addressService.readAddress();
        addressService.addAddress(address);
        employee.setAddress(address);

        System.out.println("Position");
        employee.setPosition(scanner.nextLine());

        System.out.println("Salary");
        try {
            employee.setSalary(scanner.nextDouble());
        } catch (Exception e){
            System.out.println("Provide double");
            employee.setSalary(scanner.nextDouble());
        }

        return employee;
    }

    @Override
    public String getFileName() {
        String path = "resources/CSV PAO Daria - Employee.csv";
        return path;
    }

    @Override
    public String getAntet(){
        return "Id,Name,Email,Position,Salary,Address Id\n";
    }

    @Override
    public String convertObjectToString(Employee object) {
        String line = object.getId() + separator + object.getName() + separator + object.getEmail() + separator + object.getPosition() + separator + object.getSalary() + separator + object.getAddress().getId() +  "\n";
        return line;
    }

    @Override
    public void initList(List<Employee> objects) {
        employees = new ArrayList<Employee>(objects);
    }

    @Override
    public Employee processLine(String line){
        String[] fields = line.split(separator);
        int id = 0;
        try{
            id = Integer.parseInt(fields[0]);
        } catch (Exception e){
            System.out.println("The id must be an int");
        }
        String name = fields[1];
        String email = fields[2];
        String position = fields[3];
        double salary = 0.0;
        try{
            salary = Double.parseDouble(fields[4]);
        } catch (Exception e){
            System.out.println("The salary must be a double");
        }
        int addressId = Integer.parseInt(fields[5]);
        //System.out.println(addressId);
        AddressService addressService = AddressService.getInstance();
        Address address = new Address();
        try{
            address = addressService.getAddressById(addressId);
        } catch (Exception e){
            System.out.println("The address doesnt exist");
        }
        return new Employee(id, address, name, email, position, salary);
    }


}
