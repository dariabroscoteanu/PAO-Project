package UserServices;



import UserClasses.Address;
import UserClasses.Costumer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CostumerService {
    private List<Costumer> costumers = new ArrayList<>();
    private static CostumerService instance;

    private CostumerService(){}

    public static CostumerService getInstance(){
        if(instance == null){
            instance = new CostumerService();
        }
        return instance;
    }

    public List<Costumer> getCostumers() {
        List<Costumer> costumersCopy = new ArrayList<>();
        costumersCopy.addAll(this.costumers);
        return costumersCopy;
    }

    public Costumer getCostumerById(int index){
        Costumer costumer = new Costumer();
        for(int i = 0; i < this.costumers.size(); ++i){
            if(this.costumers.get(i).getId() == index){
                costumer = this.costumers.get(i);
            }
        }
        return costumer;
    }

    public void updateCostumer(int index, Costumer costumer){
        for(int i = 0; i < this.costumers.size(); ++i){
            if(this.costumers.get(i).getId() == index){
                this.costumers.remove(i);
                this.costumers.add(index, costumer);
                break;
            }
        }
    }

    public void addCostumer(Costumer costumer){
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

    public void deteleCostumer(Costumer costumer){
        for(int i = 0;i < this.costumers.size(); ++i){
            if(this.costumers.get(i).equals(costumer)){
                this.costumers.remove(i);
                break;
            }
        }
    }

    public Costumer readCostumer(){
        Scanner scanner = new Scanner(System.in);
        AddressService addressService = AddressService.getInstance();
        Costumer costumer = new Costumer();
        System.out.println("Id");
        costumer.setId(scanner.nextInt());

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
        costumer.setTaxes(scanner.nextDouble());

        return costumer;
    }
}
