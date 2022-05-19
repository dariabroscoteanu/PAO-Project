package com.company.repository;

import com.company.config.DatabaseConfiguration;
//
import com.company.entities.Address;
import com.company.entities.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {

    private static CustomerRepository instance;

    private CustomerRepository(){}

    public static CustomerRepository getInstance(){
        if(instance == null){
            instance = new CustomerRepository();
        }
        return instance;
    }

    private Customer maptoCustomer(ResultSet resultSet){
        try {
            if (resultSet.next()) {
                int addressid = resultSet.getInt(6);
                AddressRepository addressRepository = AddressRepository.getInstance();
                // (int id, Address address, String name, String email, String usage, double taxes)
                return new Customer(resultSet.getInt(1),addressRepository.getAddressById(addressid), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4), resultSet.getDouble(5));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    public void createTable(){
        String createSql = "CREATE TABLE IF NOT EXISTS customer " +
                "(id int PRIMARY KEY AUTO_INCREMENT, " +
                "name varchar(50), " +
                "email varchar(50), " +
                "usageCustomer varchar(50), " +
                "taxes double, " +
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

    public void insertCustomer(Customer customer){
        String insertSql = "INSERT INTO customer(name, email, usageCustomer, taxes, addressId) " + "VALUES ( ?, ?, ?, ?, ?)";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getEmail());
            preparedStatement.setString(3, customer.getUsage());
            preparedStatement.setDouble(4, customer.getTaxes());
            preparedStatement.setInt(5, customer.getAddress().getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Customer getCustomerById(int id) {
        String selectSql = "SELECT * FROM customer WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            return maptoCustomer(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Customer getCustomerByName(String name) {
        String selectSql = "SELECT * FROM customer WHERE name=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();
            return maptoCustomer(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateCustomerName(String name, int id){
        String updateSql = "UPDATE customer SET name=? WHERE id=?";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCustomer(int id){
        String deleteSql = "DELETE FROM customer WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayCustomers(){
        String selectSql = "SELECT * FROM customer";
        // customer(id, name, email, usage, taxes, addressId)
        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement()) { //try with resources
            ResultSet resultSet = stmt.executeQuery(selectSql);
            while (resultSet.next()) {
                System.out.println("Id:" + resultSet.getInt(1));
                System.out.println("Name:" + resultSet.getString(2));
                System.out.println("Email:" + resultSet.getString(3));
                System.out.println("Usage:" + resultSet.getString(4));
                System.out.println("Taxes:" + resultSet.getDouble(5));
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
