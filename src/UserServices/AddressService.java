package UserServices;

import MalwareClasses.Rootkit;
import MalwareServices.RootkitService;
import UserClasses.Address;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AddressService {
    private List<Address> addresses = new ArrayList<>();
    private static AddressService instance;

    private AddressService(){}

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
                this.addresses.add(index, address);
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
        address.setId(scanner.nextInt());

        System.out.println("Address Line");
        address.setAddressLine(scanner.next());

        System.out.println("Street");
        address.setStreet(scanner.next());

        System.out.println("City");
        address.setCity(scanner.next());

        System.out.println("Country");
        address.setCountry(scanner.next());

        return address;
    }
}
