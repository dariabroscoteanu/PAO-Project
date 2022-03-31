package Services;



import Entities.Computer;
import Entities.Company;
import Entities.Address;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CompanyService {
    private List<Company> companies = new ArrayList<>();
    private static CompanyService instance;

    private CompanyService(){}

    public static CompanyService getInstance(){
        if(instance == null){
            instance = new CompanyService();
        }
        return instance;
    }

    public List<Company> getCompanies() {
        List<Company> companiesCopy = new ArrayList<>();
        companiesCopy.addAll(this.companies);
        return companiesCopy;
    }

    public Company getCompanyById(int index){
        Company company = new Company();
        for(int i = 0; i < this.companies.size(); ++i){
            if(this.companies.get(i).getId() == index){
                company = this.companies.get(i);
            }
        }
        return company;
    }

    public void updateCompany(int index, Company company){
        for(int i = 0; i < this.companies.size(); ++i){
            if(this.companies.get(i).getId() == index){
                this.companies.remove(i);
                this.companies.add(index, company);
                break;
            }
        }
    }

    public void addCompanies(Company company){
        this.companies.add(company);
    }

    public void deleteCompanyById(int index){
        for(int i = 0; i < this.companies.size(); ++i){
            if(this.companies.get(i).getId() == index){
                this.companies.remove(i);
                break;
            }
        }
    }

    public void deteleCompany(Company company){
        for(int i = 0;i < this.companies.size(); ++i){
            if(this.companies.get(i).equals(company)){
                this.companies.remove(i);
                break;
            }
        }
    }

    public Company readCompany() throws ParseException {
        Scanner scanner = new Scanner(System.in);
        AddressService addressService = AddressService.getInstance();
        ComputerService computerService = ComputerService.getInstance();
        Company company = new Company();
        System.out.println("Id");

        try {
            company.setId(scanner.nextInt());
        } catch (Exception e){
            System.out.println("Enter a number");
            company.setId(scanner.nextInt());
        }


        System.out.println("Name");
        company.setName(scanner.next());

        System.out.println("Address");
        Address address = new Address();
        address = addressService.readAddress();
        company.setAddress(address);

        System.out.println("Number of Computers");
        int nr ;
        try {
            nr = scanner.nextInt();
        } catch (Exception e){
            System.out.println("Enter a number");
            nr = scanner.nextInt();
        }
        System.out.println("Computers");
        ArrayList arr = new ArrayList<>();
        for(int i = 0; i < nr; ++i){
            Computer computer = computerService.readComputer();
            arr.add(computer);
        }

        company.setComputers(arr);

        return company;
    }
}
