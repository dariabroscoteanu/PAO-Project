package com.company.services;



import com.company.entities.Address;
import com.company.entities.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CostumerService {
    private List<Customer> costumers = new ArrayList<>();
    private static CostumerService instance;

    private CostumerService(){}

    public static CostumerService getInstance(){
        if(instance == null){
            instance = new CostumerService();
        }
        return instance;
    }

    public List<Customer> getCostumers() {
        List<Customer> costumersCopy = new ArrayList<>();
        costumersCopy.addAll(this.costumers);
        return costumersCopy;
    }

    public Customer getCostumerById(int index){
        Customer costumer = new Customer();
        for(int i = 0; i < this.costumers.size(); ++i){
            if(this.costumers.get(i).getId() == index){
                costumer = this.costumers.get(i);
            }
        }
        return costumer;
    }

    public void updateCostumer(int index, Customer costumer){
        for(int i = 0; i < this.costumers.size(); ++i){
            if(this.costumers.get(i).getId() == index){
                this.costumers.remove(i);
                this.costumers.add(index, costumer);
                break;
            }
        }
    }

    public void addCostumer(Customer costumer){
        this.costumers.add(costumer);
    }

    public void deleteCostumerById(int index){
        for(int i = 0; i < this.costumers.size(); ++i){
            if(this.costumers.get(i).getId() == index){
                this.costumers.remove(i);
                break;
            }
        }
    }

    public void deteleCostumer(Customer costumer){
        for(int i = 0;i < this.costumers.size(); ++i){
            if(this.costumers.get(i).equals(costumer)){
                this.costumers.remove(i);
                break;
            }
        }
    }

    public Customer readCostumer(){
        Scanner scanner = new Scanner(System.in);
        AddressService addressService = AddressService.getInstance();
        Customer costumer = new Customer();
        System.out.println("Id");
        try {
            costumer.setId(scanner.nextInt());
        } catch (Exception e){
            System.out.println("Provide int");
            costumer.setId(scanner.nextInt());
        }

        System.out.println("Name");
        costumer.setName(scanner.next());

        System.out.println("Email");
        costumer.setEmail(scanner.next());

        System.out.println("Address");
        Address address = addressService.readAddress();
        costumer.setAddress(address);

        System.out.println("Usage");
        costumer.setUsage(scanner.next());

        System.out.println("Taxes");
        try {
            costumer.setTaxes(scanner.nextDouble());
        } catch (Exception e){
            System.out.println("Provide double");
            costumer.setTaxes(scanner.nextDouble());
        }
        return costumer;
    }
}
