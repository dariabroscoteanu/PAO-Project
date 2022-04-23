package com.company.services;

import com.company.entities.Address;

import java.util.*;

public class AddressService implements AddressInterface, CSVReaderWriter<Address> {
    private List<Address> addresses = new ArrayList<>();
    private static AddressService instance;

    private AddressService(){
        read();
    }

    public static AddressService getInstance(){
        if(instance == null){
            instance = new AddressService();
        }
        return instance;
    }

    public List<Address> getAddresses() {
        List<Address> addressesCopy = new ArrayList<>();
        addressesCopy.addAll(this.addresses);
        return addressesCopy;
    }

    public Address getAddressById(int index){
        Address address = new Address();
        for(int i = 0; i < this.addresses.size(); ++i){
            if(this.addresses.get(i).getId() == index){
                address = this.addresses.get(i);
            }
        }
        return address;
    }

    public void updateAddress(int index, Address address){
        for(int i = 0; i < this.addresses.size(); ++i){
            if(this.addresses.get(i).getId() == index){
                this.addresses.remove(i);
                this.addresses.add(i, address);
                break;
            }
        }
    }

    public void addAddress(Address address){
        this.addresses.add(address);
    }

    public void deleteRootkitById(int index){
        for(int i = 0; i < this.addresses.size(); ++i){
            if(this.addresses.get(i).getId() == index){
                this.addresses.remove(i);
                break;
            }
        }
    }

    public Address readAddress(){
        Scanner scanner = new Scanner(System.in);
        Address address = new Address();
        System.out.println("Id");
        try {
            address.setId(scanner.nextInt());
        } catch (Exception e){
            System.out.println("Provide int");
            address.setId(scanner.nextInt());
        }

        System.out.println("Address Line");
        address.setAddressLine(scanner.nextLine());

        System.out.println("Street");
        address.setStreet(scanner.nextLine());

        System.out.println("City");
        address.setCity(scanner.nextLine());

        System.out.println("Country");
        address.setCountry(scanner.nextLine());

        return address;
    }

    @Override
    public String getAntet() {
        return "Id,Street,City,Country,Address Line\n";
    }

    @Override
    public String getFileName() {
        String path = "resources/CSV PAO Daria - Address.csv";
        return path;
    }

    @Override
    public String convertObjectToString(Address object) {
        String line = object.getId() + separator + object.getStreet() + separator + object.getCity() + separator + object.getCountry() + separator + object.getAddressLine() + "\n";
        return line;
    }

    @Override
    public Address processLine(String line){
        String[] fields = line.split(separator);
        int id = 0;
        try{
            id = Integer.parseInt(fields[0]);
        } catch (Exception e){
            System.out.println("The id must be an int");
        }
        String street = fields[1];
        String city = fields[2];
        String country = fields[3];
        String addressLine = fields[4];
        return new Address(id, street, city, country, addressLine);
    }

    public void initList(List<Address> objects){
        this.addresses = new ArrayList<Address>(objects);
    }
}
