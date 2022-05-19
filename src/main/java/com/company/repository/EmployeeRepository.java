package com.company.repository;

import com.company.config.DatabaseConfiguration;
import com.company.entities.Address;
import com.company.entities.Customer;
import com.company.entities.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {

    private static EmployeeRepository instance;

    private EmployeeRepository(){}

    public static EmployeeRepository getInstance(){
        if(instance == null){
            instance = new EmployeeRepository();
        }
        return instance;
    }

    private Employee maptoEmployee(ResultSet resultSet){
        try {
            if (resultSet.next()) {
                int addressid = resultSet.getInt(6);
                AddressRepository addressRepository = AddressRepository.getInstance();
                // (int id, Address address, String name, String email, String usage, double taxes)
                return new Employee(resultSet.getInt(1),addressRepository.getAddressById(addressid), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4), resultSet.getDouble(5));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    public void createTable(){
        // (int id, Address address, String name, String email, String position, double salary)
        String createSql = "CREATE TABLE IF NOT EXISTS employee " +
                "(id int PRIMARY KEY AUTO_INCREMENT, " +
                "name varchar(50), " +
                "email varchar(50), " +
                "position varchar(50), " +
                "salary double, " +
                "addressId int, " +
                "FOREIGN KEY (addressId) REFERENCES address(id) " +
                ")";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertEmployee(Employee employee){
        String insertSql = "INSERT INTO employee(name, email, position, salary, addressId) " + "VALUES (?, ?, ?, ?, ?)";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getEmail());
            preparedStatement.setString(3, employee.getPosition());
            preparedStatement.setDouble(4, employee.getSalary());
            preparedStatement.setInt(5, employee.getAddress().getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Employee getEmployeeById(int id) {
        String selectSql = "SELECT * FROM employee WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            return maptoEmployee(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Employee getEmployeeByName(String name) {
        String selectSql = "SELECT * FROM employee WHERE name=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();
            return maptoEmployee(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateEmployeeName(String name, int id){
        String updateSql = "UPDATE employee SET name=? WHERE id=?";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteEmployee(int id){
        String deleteSql = "DELETE FROM employee WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayEmployee(){
        String selectSql = "SELECT * FROM employee";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement()) { //try with resources
            ResultSet resultSet = stmt.executeQuery(selectSql);
            while (resultSet.next()) {
                System.out.println("Id:" + resultSet.getInt(1));
                System.out.println("Name:" + resultSet.getString(2));
                System.out.println("Email:" + resultSet.getString(3));
                System.out.println("Position:" + resultSet.getString(4));
                System.out.println("Salary:" + resultSet.getDouble(5));
                int addressId = resultSet.getInt(6);
                AddressRepository addressRepository = AddressRepository.getInstance();
                Address address = addressRepository.getAddressById(addressId);
                if(address != null)
                    System.out.println("Address:" + address.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
