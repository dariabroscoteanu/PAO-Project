package com.company.repository;

import com.company.config.DatabaseConfiguration;
import com.company.entities.Address;
import com.company.services.AddressService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressRepository {

    private static AddressRepository instance;

    private AddressRepository(){}

    public static AddressRepository getInstance(){
        if(instance == null){
            instance = new AddressRepository();
        }
        return instance;
    }

    private Address maptoAddress(ResultSet resultSet){
        try {
            if (resultSet.next()) {
                return new Address(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                                   resultSet.getString(4), resultSet.getString(5));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    public void createTable() {
        String createSql = "CREATE TABLE IF NOT EXISTS address " +
                "(id int PRIMARY KEY AUTO_INCREMENT, " +
                "street varchar(50), " +
                "city varchar(50), " +
                "country varchar(50), " +
                "addressLine varchar(50) " +
                ")";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertAddress(Address address){
        String insertSql = "INSERT INTO address(street, city, country, addressLine) " + "VALUES (?, ?, ?, ?)";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {
            preparedStatement.setString(1, address.getStreet());
            preparedStatement.setString(2, address.getCity());
            preparedStatement.setString(3, address.getCountry());
            preparedStatement.setString(4, address.getAddressLine());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Address getAddressById(int id) {
        String selectSql = "SELECT * FROM address WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            return maptoAddress(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Address getAddressByStreet(String street) {
        String selectSql = "SELECT * FROM address WHERE street=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
            preparedStatement.setString(1, street);

            ResultSet resultSet = preparedStatement.executeQuery();
            return maptoAddress(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateAddressStreet(String street, int id){
        String updateSql = "UPDATE address SET street=? WHERE id=?";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {
            preparedStatement.setString(1, street);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAddress(int id){
        String deleteSql = "DELETE FROM address WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayAddresses(){
        String selectSql = "SELECT * FROM address";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement()) { //try with resources
            ResultSet resultSet = stmt.executeQuery(selectSql);
            while (resultSet.next()) {
                System.out.println("Id:" + resultSet.getInt(1));
                System.out.println("Street:" + resultSet.getString(2));
                System.out.println("City:" + resultSet.getString(3));
                System.out.println("Country:" + resultSet.getString(4));
                System.out.println("Address Line:" + resultSet.getString(5));
                System.out.println("------------------------------------------------------------- ");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayIdAddresses(){
        String selectSql = "SELECT id FROM address";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement()) { //try with resources
            ResultSet resultSet = stmt.executeQuery(selectSql);
            while (resultSet.next()) {
                System.out.println("Id:" + resultSet.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
