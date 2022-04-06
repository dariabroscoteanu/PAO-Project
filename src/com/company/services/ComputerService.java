package services;


import entities.Computer;
import entities.KernelKeylogger;
import entities.Keylogger;
import entities.Ransomeware;
import entities.Rootkit;
import entities.Costumer;
import entities.Employee;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ComputerService {
    private List<Computer> computers = new ArrayList<>();
    private static ComputerService instance;

    private ComputerService(){}

    public static ComputerService getInstance(){
        if(instance == null){
            instance = new ComputerService();
        }
        return instance;
    }

    public List<Computer> getComputers() {
        List<Computer> computersCopy = new ArrayList<>();
        computersCopy.addAll(this.computers);
        return computersCopy;
    }

    public Computer getComputerById(int index){
        Computer computer = new Computer();
        for(int i = 0; i < this.computers.size(); ++i){
            if(this.computers.get(i).getId() == index){
                computer = this.computers.get(i);
            }
        }
        return computer;
    }

    public void updateComputer(int index, Computer computer){
        for(int i = 0; i < this.computers.size(); ++i){
            if(this.computers.get(i).getId() == index){
                this.computers.remove(i);
                this.computers.add(index, computer);
                break;
            }
        }
    }

    public void addComputer(Computer computer){
        this.computers.add(computer);
    }

    public void deleteCostumerById(int index){
        for(int i = 0; i < this.computers.size(); ++i){
            if(this.computers.get(i).getId() == index){
                this.computers.remove(i);
                break;
            }
        }
    }

    public void deteleComputers(Computer computer){
        for(int i = 0;i < this.computers.size(); ++i){
            if(this.computers.get(i).equals(computer)){
                this.computers.remove(i);
                break;
            }
        }
    }

    public double calculateTotalRating(Computer computer){
        final double[] totalRating = {0};
        computer.getMalwares().forEach(malware -> {
            if(malware instanceof Rootkit){
                Rootkit rootkit = ((Rootkit) malware);
                totalRating[0] += rootkit.getRating();
            }
            else if(malware instanceof Ransomeware){
                totalRating[0] += ((Ransomeware) malware).getRating();
            }
            else if(malware instanceof Keylogger){
                totalRating[0] += ((Keylogger) malware).getRating();
            }
            else if(malware instanceof KernelKeylogger){
                totalRating[0] += ((KernelKeylogger) malware).getRating();
            }
        });
        computer.setTotalRating(totalRating[0]);
        return totalRating[0];
    }

    public Computer readComputer() throws ParseException {
        Scanner scanner = new Scanner(System.in);
        RootkitService rootkitService = RootkitService.getInstance();
        RansomewareService ransomewareService = RansomewareService.getInstance();
        KeyloggerService keyloggerService = KeyloggerService.getInstance();
        KernelKeyloggerService kernelKeyloggerService = KernelKeyloggerService.getInstance();
        EmployeeService employeeService = EmployeeService.getInstance();
        CostumerService costumerService = CostumerService.getInstance();
        System.out.println("Id");
        Computer computer = new Computer();
        computer.setId(scanner.nextInt());

        System.out.println("Number of Malwares");
        int nr;
        try {
            nr = scanner.nextInt();
        } catch (Exception e){
            System.out.println("Enter a number");
            nr = scanner.nextInt();
        }
        System.out.println("Malwares");
        ArrayList arr = new ArrayList<>();
        for(int i = 0; i < nr; ++i){
            System.out.println(" 0 - Rootkit");
            System.out.println(" 1 - Ransomeware");
            System.out.println(" 2 - Keylogger");
            System.out.println(" 3 - Kernel Keylogger");
            int opt;
            try {
                opt = scanner.nextInt();
            } catch (Exception e){
                System.out.println("Enter a number");
                opt = scanner.nextInt();
            }
            if(opt == 0) {
                Rootkit rootkit = rootkitService.readRootkit();
                arr.add(rootkit);
            }
            else if(opt == 1){
                Ransomeware  ransomeware = ransomewareService.readRansomeware();
                arr.add(ransomeware);
            }
            else if(opt == 2){
                Keylogger keylogger = keyloggerService.readKeylogger();
                arr.add(keylogger);
            }
            else if(opt == 3){
                KernelKeylogger kernelKeylogger = kernelKeyloggerService.readKernelkeylogger();
                arr.add(kernelKeylogger);
            }
        }
        // TreeSet t = new TreeSet(arr);
        computer.setMalwares(arr);

        System.out.println("Number of Users");
        ArrayList arr1 = new ArrayList<>();
        try {
            nr = scanner.nextInt();
        } catch (Exception e){
            System.out.println("Enter a number");
            nr = scanner.nextInt();
        }
        System.out.println("Users");
        for(int i = 0; i < nr; ++i){
            System.out.println("0 - Costumer");
            System.out.println("1 - Employee");
            int opt;
            try {
                opt = scanner.nextInt();
            } catch (Exception e){
                System.out.println("Enter a number");
                opt = scanner.nextInt();
            }
            if(opt == 0){
                Costumer costumer = costumerService.readCostumer();
                arr1.add(costumer);
            } else if (opt == 1) {
                Employee employee = employeeService.readEmployee();
                arr1.add(employee);
            }
        }
//        TreeSet t1 = new TreeSet(arr);
        computer.setUsers(arr1);

        return computer;
    }
}
