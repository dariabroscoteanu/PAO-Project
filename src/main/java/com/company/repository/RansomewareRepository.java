package com.company.repository;

import com.company.config.DatabaseConfiguration;
import com.company.entities.Ransomeware;
import com.company.services.RansomewareService;


import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class RansomewareRepository {

    private static RansomewareRepository instance;

    private RansomewareRepository(){}

    public static RansomewareRepository getInstance(){
        if(instance == null){
            instance = new RansomewareRepository();
        }
        return instance;
    }

    private Ransomeware maptoRansomeware(ResultSet resultSet){
        try {
            if (resultSet.next()) {

                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                Date date = null;
                try{
                    date = new SimpleDateFormat("dd/MM/yyyy").parse(resultSet.getString(3));
                } catch (ParseException e){
                    e.printStackTrace();
                }
                String infectionMethod = resultSet.getString(4);
                String str = resultSet.getString(5);
                int nr = 0;
                for(int i = 0;i < str.length(); ++i){
                    if(str.charAt(i) == '#')
                        ++nr;
                }
                String[] registers = str.split("#", nr);
                List<String> regs = new ArrayList<>(Arrays.asList(registers));
                double encryption = resultSet.getDouble(6);
                double hiding = resultSet.getDouble(7);
                // Ransomeware(int id, double rating, Date creationDate, String name,
                // String infectionMethod, ArrayList<String> modifiedRegisters, double encryptionRating, double hidingRating)
                Ransomeware ransomeware = new Ransomeware(id, 0.0, date, name, infectionMethod, (ArrayList<String>) regs, encryption, hiding);
                RansomewareService ransomewareService = RansomewareService.getInstance();
                ransomewareService.findRating(ransomeware);
                return ransomeware;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    public void createTable(){
        // (int id, Address address, String name, String email, String position, double salary)
        String createSql = "CREATE TABLE IF NOT EXISTS ransomeware " +
                "(id int PRIMARY KEY AUTO_INCREMENT, " +
                "name varchar(50), " +
                "creationDate varchar(50), " +
                "infectionMethod varchar(50), " +
                "modifiedRegisters varchar(250), " +
                "encryptionRating double, " +
                "hidingRating double" +
                ")";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertRansomeware(Ransomeware ransomeware){
        String insertSql = "INSERT INTO ransomeware(name, creationDate, infectionMethod, modifiedRegisters, encryptionRating, hidingRating) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {
            preparedStatement.setString(1, ransomeware.getName());
            Date date = ransomeware.getCreationDate();
            String dateString = new SimpleDateFormat("dd/MM/yyyy").format(date);
            preparedStatement.setString(2, dateString);
            preparedStatement.setString(3, ransomeware.getInfectionMethod());
            String registers = "";
            if(ransomeware.getModifiedRegisters().size() > 0){
                List<String> regs = ransomeware.getModifiedRegisters();
                for(int i = 0; i < regs.size() - 1; ++i){
                    registers += regs.get(i) + "#";
                }
                registers += regs.get(regs.size() - 1);
            }
            preparedStatement.setString(4, registers);
            preparedStatement.setDouble(5, ransomeware.getEncryptionRating());
            preparedStatement.setDouble(6, ransomeware.getHidingRating());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Ransomeware getRansomewareById(int id) {
        String selectSql = "SELECT * FROM ransomeware WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            return maptoRansomeware(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Ransomeware getRansomewareByName(String name) {
        String selectSql = "SELECT * FROM ransomeware WHERE name=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();
            return maptoRansomeware(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateRansomewareName(String name, int id){
        String updateSql = "UPDATE ransomeware SET name=? WHERE id=?";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteRansomeware(int id){
        String deleteSql = "DELETE FROM ransomeware WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayRansomeware(){
        String selectSql = "SELECT * FROM ransomeware";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement()) { //try with resources
            ResultSet resultSet = stmt.executeQuery(selectSql);
            while (resultSet.next()) {
                System.out.println("Id:" + resultSet.getInt(1));
                System.out.println("Name:" + resultSet.getString(2));
                System.out.println("Creation Date:" + resultSet.getString(3));
                System.out.println("Infection Method:" + resultSet.getString(4));
                System.out.println("Encryption Rating:" + resultSet.getDouble(6));
                System.out.println("Hiding Rating:" + resultSet.getDouble(7));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
