package com.company.services;



import com.company.entities.Computer;
import com.company.entities.Company;
import com.company.entities.Address;
import com.company.entities.Ransomeware;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CompanyService implements CompanyInterface, CSVReaderWriter<Company>{
    private List<Company> companies = new ArrayList<>();
    private static CompanyService instance;

    private CompanyService(){
        read();
    }

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
                this.companies.add(i, company);
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
//        System.out.println("Id");
//
//        try {
//            company.setId(scanner.nextInt());
//        } catch (Exception e){
//            System.out.println("Enter a number");
//            company.setId(scanner.nextInt());
//        }
        int id = getMaxId() + 1;
        company.setId(id);

        System.out.println("Name");
        company.setName(scanner.nextLine());

        System.out.println("Address");
        Address address = new Address();
        address = addressService.readAddress();
        addressService.addAddress(address);
        company.setAddress(address);
        //scanner.nextLine();
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
            computerService.addComputer(computer);
            arr.add(computer);
        }

        company.setComputers(arr);

        return company;
    }

    public void printMostDangerousMalware(){

    }

    @Override
    public String getFileName(){
        String path = "src/com/company/resources/CSV PAO Daria - Company.csv";
        return path;
    }

    @Override
    public void initList(List<Company> objects) {
        companies = new ArrayList<Company>(objects);
    }

    @Override
    public String convertObjectToString(Company object) {
        String res;
        if(object.getAddress().getId() == -1){
            res = "null";
        } else {
            res = String.valueOf(object.getAddress().getId());
        }
        String line = object.getId() + separator + object.getName() + separator + res + "\n";
        return line;
    }

    public int getMaxId(){
        int max = 0;
        for(int i = 0; i < companies.size(); ++i){
            if(companies.get(i).getId() > max){
                max = companies.get(i).getId();
            }
        }
        return max;
    }

    public List<Company> read() {
        String fileName = this.getFileName();
        File file = new File(fileName);
        String extraFileName = "src/com/company/resources/CSV PAO Daria - Company_Computer.csv";
        File extraFile = new File(extraFileName);

        try {
            BufferedReader bufferedReader = null;
            try {
                bufferedReader = new BufferedReader(new FileReader(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            List<Company> result;

            try {
                List<Company> resultLines = new ArrayList<Company>();
                bufferedReader.readLine(); // skip first line
                String currentLine = bufferedReader.readLine();

                while (true) {
                    if (currentLine == null) {
                        result = resultLines;
                        break;
                    }
                    Company obj = this.processLine(currentLine);
                    resultLines.add(obj);
                    currentLine = bufferedReader.readLine();
                }
                BufferedReader extra = new BufferedReader(new FileReader(extraFile));
                try {
                    extra.readLine();
                    String line = extra.readLine();
                    while (true) {
                        if (line == null) {
                            break;
                        }
                        String[] fields = line.split(separator);
                        int id = Integer.parseInt(fields[0]);
                        if(Objects.equals(fields[1], "null")){
                            fields[1] = "-1";
                        }
                        int computerId = Integer.parseInt(fields[1]);
                        Company company = resultLines.stream()
                                .filter(r -> r.getId() == id)
                                .findAny()
                                .orElse(null);
                        if (company != null) {

                            if (company.getComputers() != null) {
                                List<Computer> computers = company.getComputers();
                                ComputerService computerService = ComputerService.getInstance();
                                Computer computer = computerService.getComputers().stream()
                                        .filter(r -> r.getId() == computerId)
                                        .findAny()
                                        .orElse(null);
                                if (computer != null) {
                                    computers.add(computer);
                                    company.setComputers(computers);
                                }
                            } else {
                                List<Computer> computers = new ArrayList<Computer>();
                                ComputerService computerService = ComputerService.getInstance();
                                Computer computer = computerService.getComputers().stream()
                                        .filter(r -> r.getId() == computerId)
                                        .findAny()
                                        .orElse(null);
                                if (computer != null) {
                                    computers.add(computer);
                                    company.setComputers(computers);
                                }
                                int index = 0;
                                for (Company element : resultLines) {
                                    if (element.getId() == company.getId()) {
                                        resultLines.set(index, company);
                                        break;
                                    }
                                    index += 1;
                                }
                            }

                            line = extra.readLine();
                        }
                    }
                    try {
                        extra.close();
                    } catch (Throwable s) {
                        throw s;
                    }

                    result = resultLines;

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }catch (Throwable anything) {
                    try {
                        bufferedReader.close();
                    } catch (Throwable something) {
                        anything.addSuppressed(something);
                    }
                    throw anything;
                }

                bufferedReader.close();
                initList(result);
                return result;

            } catch (FileNotFoundException e1) {
                System.out.println("File not found");
                initList(Collections.emptyList());
                return Collections.emptyList();
            } catch (IOException | ParseException e2) {
                System.out.println("Cannot read from file");
                initList(Collections.emptyList());
                return Collections.emptyList();
            }
    }

    @Override
    public Company processLine(String line) throws ParseException {
        String[] fields = line.split(separator);
        int id = 0;
        if(Objects.equals(fields[0], "null")){
            id = getMaxId();
        } else {
            try {
                id = Integer.parseInt(fields[0]);
            } catch (Exception e) {
                System.out.println("The id must be an int");
            }
        }

        String name = fields[1];

        //System.out.println(addressId);
        AddressService addressService = AddressService.getInstance();
        int addressId;
        Address address = new Address();
        if(Objects.equals(fields[2], "null")){
            fields[2] = "-1";
        }

        addressId = Integer.parseInt(fields[2]);

        address = new Address();
        try{
            address = addressService.getAddressById(addressId);
        } catch (Exception e){
            System.out.println("The address doesnt exist");
        }

        return new Company(id, name, address, new ArrayList<>());

    }

    public String getAntet(){
        return "";
    }

    public void write(List<Company> objects){
        String fileName = this.getFileName();
        File file = new File(fileName);

        try{
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, false));
            try{
                String CSVline = "Id,Name,Address Id\n";
                bufferedWriter.write(CSVline);
            } catch (Throwable anything){
                throw anything;
            }
            if(objects != null){
                for(Company object : objects){
                    try{
                        String CSVline = this.convertObjectToString(object);
                        bufferedWriter.write(CSVline);
                    } catch (Throwable anything){
                        throw anything;
                    }
                }

            }
            bufferedWriter.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        fileName = "src/com/company/resources/CSV PAO Daria - Company_Computer.csv";
        file = new File(fileName);

        try{
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
            try{
                String CSVline = "Company Id,Computer Id\n";
                bufferedWriter.write(CSVline);
            } catch (Throwable anything){
                throw anything;
            }
            if(objects != null){
                for(Company object : objects){
                    for(Computer computer : object.getComputers()) {
                        try{
                            String CSVline = object.getId() + separator + computer.getId() + "\n";
                            bufferedWriter.write(CSVline);
                        } catch (Throwable anything){
                            throw anything;
                        }
                    }
                }

            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

}
