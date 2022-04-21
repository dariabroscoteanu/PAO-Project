package com.company.services;



import com.company.entities.Address;
import com.company.entities.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CustomerService implements CustomerInterface{
    private List<Customer> customers = new ArrayList<>();
    private static CustomerService instance;

    private CustomerService(){}

    public static CustomerService getInstance(){
        if(instance == null){
            instance = new CustomerService();
        }
        return instance;
    }

    public List<Customer> getCustomers() {
        List<Customer> customersCopy = new ArrayList<>();
        customersCopy.addAll(this.customers);
        return customersCopy;
    }

    public Customer getCustomerById(int index){
        Customer customer = new Customer();
        for(int i = 0; i < this.customers.size(); ++i){
            if(this.customers.get(i).getId() == index){
                customer = this.customers.get(i);
            }
        }
        return customer;
    }

    public void updateCustomer(int index, Customer customer){
        for(int i = 0; i < this.customers.size(); ++i){
            if(this.customers.get(i).getId() == index){
                this.customers.remove(i);
                this.customers.add(i, customer);
                break;
            }
        }
    }

    public void addCustomer(Customer customer){
        this.customers.add(customer);
    }

    public void deleteCustomerById(int index){
        for(int i = 0; i < this.customers.size(); ++i){
            if(this.customers.get(i).getId() == index){
                this.customers.remove(i);
                break;
            }
        }
    }

    public void deteleCustomer(Customer customer){
        for(int i = 0;i < this.customers.size(); ++i){
            if(this.customers.get(i).equals(customer)){
                this.customers.remove(i);
                break;
            }
        }
    }

    public Customer readCustomer(){
        Scanner scanner = new Scanner(System.in);
        AddressService addressService = AddressService.getInstance();
        Customer customer = new Customer();
        System.out.println("Id");
        try {
            customer.setId(scanner.nextInt());
        } catch (Exception e){
            System.out.println("Provide int");
            customer.setId(scanner.nextInt());
        }

        System.out.println("Name");
        customer.setName(scanner.next());

        System.out.println("Email");
        customer.setEmail(scanner.next());

        System.out.println("Address");
        Address address = addressService.readAddress();
        customer.setAddress(address);

        System.out.println("Usage");
        customer.setUsage(scanner.next());

        System.out.println("Taxes");
        try {
            customer.setTaxes(scanner.nextDouble());
        } catch (Exception e){
            System.out.println("Provide double");
            customer.setTaxes(scanner.nextDouble());
        }
        return customer;
    }
}
