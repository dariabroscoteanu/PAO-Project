package com.company;

import com.company.entities.Address;
import com.company.entities.Computer;
import com.company.entities.Employee;
import com.company.entities.Ransomeware;
import com.company.repository.AddressRepository;
import com.company.services.*;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws ParseException, IOException {
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.println("0 - For CSV Menu");
            System.out.println("1 - For Database Menu");
            System.out.println("2 - Exit");
            int option;
            while(true){
                String line = scanner.nextLine();
                try {
                    option = Integer.parseInt(line);
                    if (option >= 0 && option <= 2) {
                        break;
                    } else {
                        System.out.println("Enter a number between 0 and 4");
                    }
                } catch (Exception e){
                    System.out.println("Enter a number between 0 and 2");
                }
            }
            if(option == 0){
                Service service = Service.getInstance();
                service.menu();
            } else if(option == 1) {
                Service service = Service.getInstance();
                service.databaseMenu();
            } else{
                break;
            }
        }
    }
}
