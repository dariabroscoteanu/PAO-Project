package Service;

import Company.Company;
import Company.CompanyService;
import Computer.Computer;
import Computer.ComputerService;
import MalwareClasses.KernelKeylogger;
import MalwareClasses.Keylogger;
import MalwareClasses.Ransomeware;
import MalwareClasses.Rootkit;
import MalwareServices.KernelKeyloggerService;
import MalwareServices.KeyloggerService;
import MalwareServices.RansomewareService;
import MalwareServices.RootkitService;
import UserClasses.Address;
import UserClasses.Costumer;
import UserClasses.Employee;
import UserServices.CostumerService;
import UserServices.EmployeeService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Service {
    private RootkitService rootkitService = RootkitService.getInstance();
    private RansomewareService ransomewareService = RansomewareService.getInstance();
    private KeyloggerService keyloggerService = KeyloggerService.getInstance();
    private KernelKeyloggerService kernelKeyloggerService = KernelKeyloggerService.getInstance();

    private EmployeeService employeeService = EmployeeService.getInstance();
    private CostumerService costumerService = CostumerService.getInstance();

    private ComputerService computerService = ComputerService.getInstance();

    private CompanyService companyService = CompanyService.getInstance();

    private static Service instance;

    private Scanner scanner = new Scanner(System.in);

    private Service(){}

    public static Service getInstance(){
        if(instance == null){
            instance = new Service();
        }
        return instance;
    }


    public void printUserMenu(){
        System.out.println(" 0 - Employee");
        System.out.println(" 1 - Costumer");
        System.out.println(" 2 - Exit");
    }

    public void userMenu(){
        while(true){
            printUserMenu();
            int option = scanner.nextInt();
            if (option == 0) {
                employeeMenu();
            } else if (option == 1) {
                costumerMenu();
            } else if (option == 2) {
                break;
            }
        }
    }


    public void costumerMenu() {
        while(true) {
            printOptions();
            int option = scanner.nextInt();
            if (option == 0) {
                for (int i = 0; i < costumerService.getCostumers().size(); ++i) {
                    System.out.println(costumerService.getCostumers().get(i).toString());
                }
            } else if (option == 1) {
                int index = scanner.nextInt();
                System.out.println(costumerService.getCostumerById(index).toString());
            } else if (option == 2) {
                Costumer costumer = costumerService.readCostumer();
                costumerService.addCostumer(costumer);
            } else if (option == 3) {
                int index = scanner.nextInt();
                Costumer costumer = costumerService.readCostumer();
                costumerService.updateCostumer(index, costumer);
            } else if (option == 4) {
                int index = scanner.nextInt();
                costumerService.deleteCostumerById(index);
            } else if (option == 5) {
                break;
            }
        }
    }


    public void employeeMenu() {
        while(true) {
            printOptions();
            int option = scanner.nextInt();
            if (option == 0) {
                for (int i = 0; i < employeeService.getEmployees().size(); ++i) {
                    System.out.println(employeeService.getEmployees().get(i).toString());
                }
            } else if (option == 1) {
                int index = scanner.nextInt();
                System.out.println(employeeService.getEmployeeById(index).toString());
            } else if (option == 2) {
                Employee employee = employeeService.readEmployee();
                employeeService.addEmployees(employee);
            } else if (option == 3) {
                int index = scanner.nextInt();
                Employee employee = employeeService.readEmployee();
                employeeService.updateEmployee(index, employee);
            } else if (option == 4) {
                int index = scanner.nextInt();
                employeeService.deleteEmployeeById(index);
            } else if (option == 5) {
                break;
            }
        }
    }

    public void printMalwareMenu(){
        System.out.println(" 0 - Rootkit");
        System.out.println(" 1 - Ransomeware");
        System.out.println(" 2 - Keylogger");
        System.out.println(" 3 - Kernel Keylogger");
        System.out.println(" 4 - Exit");
    }

    public void malwareMenu() throws ParseException {
        while(true){
            printMalwareMenu();
            int option = scanner.nextInt();
            if (option == 0) {
                rootkitMenu();
            } else if (option == 1) {
                ransomewareMenu();
            } else if (option == 2) {
                keyloggerMenu();
            } else if (option == 3) {
                kernelKeyloggerMenu();
            } else if (option == 4) {
                break;
            }
        }
    }

    public void keyloggerMenu() throws ParseException {
        while(true) {
            printOptions();
            int option = scanner.nextInt();
            if (option == 0) {
                for (int i = 0; i < keyloggerService.getKeyloggers().size(); ++i) {
                    System.out.println(keyloggerService.getKeyloggers().get(i).toString());
                }
            } else if (option == 1) {
                int index = scanner.nextInt();
                keyloggerService.getKeyloggerById(index);
            } else if (option == 2) {
                Keylogger keylogger = keyloggerService.readKeylogger();
                keyloggerService.addKeylogger(keylogger);
            } else if (option == 3) {
                int index = scanner.nextInt();
                Keylogger keylogger = keyloggerService.readKeylogger();
                keyloggerService.updateKeylogger(index, keylogger);
            } else if (option == 4) {
                int index = scanner.nextInt();
                keyloggerService.deleteKeyloggerById(index);
            } else if (option == 5) {
                break;
            }
        }
    }

    public void kernelKeyloggerMenu() throws ParseException {
        while(true) {
            printOptions();
            int option = scanner.nextInt();
            if (option == 0) {
                for (int i = 0; i < kernelKeyloggerService.getKernelKeyloggers().size(); ++i) {
                    System.out.println(kernelKeyloggerService.getKernelKeyloggers().get(i).toString());
                }
            } else if (option == 1) {
                int index = scanner.nextInt();
                kernelKeyloggerService.getKernelKeyloggerById(index);
            } else if (option == 2) {
                KernelKeylogger kernelKeylogger = kernelKeyloggerService.readKernelkeylogger();
                kernelKeyloggerService.addKernelKeylogger(kernelKeylogger);
            } else if (option == 3) {
                int index = scanner.nextInt();
                KernelKeylogger kernelKeylogger = kernelKeyloggerService.readKernelkeylogger();
                kernelKeyloggerService.updateKernelKeylogger(index, kernelKeylogger);
            } else if (option == 4) {
                int index = scanner.nextInt();
                kernelKeyloggerService.deleteKernelKeyloggerById(index);
            } else if (option == 5) {
                break;
            }
        }
    }

    public void ransomewareMenu() throws ParseException {
        while(true) {
            printOptions();
            int option = scanner.nextInt();
            if (option == 0) {
                for (int i = 0; i < ransomewareService.getRansomewares().size(); ++i) {
                    System.out.println(ransomewareService.getRansomewares().get(i).toString());
                }
            } else if (option == 1) {
                int index = scanner.nextInt();
                ransomewareService.getRansomewareById(index);
            } else if (option == 2) {
                Ransomeware ransomeware = new Ransomeware();
                ransomewareService.addRansomeware(ransomeware);
            } else if (option == 3) {
                int index = scanner.nextInt();
                Ransomeware ransomeware = ransomewareService.readRansomeware();
                ransomewareService.updateRansomeware(index, ransomeware);
            } else if (option == 4) {
                int index = scanner.nextInt();
                ransomewareService.deleteRansomewareById(index);
            } else if (option == 5) {
                break;
            }
        }
    }

    public void rootkitMenu() throws ParseException {
        while(true) {
            printOptions();
            int option = scanner.nextInt();
            if (option == 0) {
                for (int i = 0; i < rootkitService.getRootkits().size(); ++i) {
                    System.out.println(rootkitService.getRootkits().get(i).toString());
                }
            } else if (option == 1) {
                int index = scanner.nextInt();
                rootkitService.getRootkitById(index);
            } else if (option == 2) {
                Rootkit rootkit = rootkitService.readRootkit();
                rootkitService.addRootkit(rootkit);
            } else if (option == 3) {
                int index = scanner.nextInt();
                Rootkit rootkit = rootkitService.readRootkit();
                rootkitService.updateRootkit(index, rootkit);
            } else if (option == 4) {
                int index = scanner.nextInt();
                rootkitService.deleteRootkitById(index);
            } else if (option == 5) {
                break;
            }
        }
    }

    public void computerMenu() throws ParseException {
        while(true) {
            printOptions();
            int option = scanner.nextInt();
            if (option == 0) {
                for (int i = 0; i < computerService.getComputers().size(); ++i) {
                    for(int j = 0; j < computerService.getComputers().get(i).getMalwares().size(); ++j){
                        computerService.getComputers().get(i).getMalwares().forEach(malware -> {
                            if(malware instanceof Rootkit){
                                Rootkit rootkit = (Rootkit) malware;
                                System.out.println(rootkit.toString());
                            }
                            else if(malware instanceof Ransomeware){
                                Ransomeware ransomeware = (Ransomeware) malware;
                                System.out.println(ransomeware.toString());
                            }
                            else if(malware instanceof Keylogger){
                                if( malware instanceof KernelKeylogger){
                                    KernelKeylogger kernelKeylogger = (KernelKeylogger) malware;
                                    System.out.println(kernelKeylogger.toString());
                                }
                                else {
                                    Keylogger keylogger = (Keylogger) malware;
                                    System.out.println(keylogger.toString());
                                }
                            }
                        });
                    }

                    for(int j = 0; j < computerService.getComputers().get(i).getUsers().size(); ++j){
                        computerService.getComputers().get(i).getUsers().forEach(user -> {
                            if(user instanceof Costumer){
                                Costumer costumer = (Costumer) user;
                                System.out.println(costumer.toString());
                            }
                            else if(user instanceof Employee){
                                Employee employee = (Employee) user;
                                System.out.println(employee.toString());
                            }
                        });
                    }
                }
            } else if (option == 1) {
                int index = scanner.nextInt();
                Computer computer = computerService.getComputerById(index);
                for(int j = 0; j < computer.getMalwares().size(); ++j){
                    computer.getMalwares().forEach(malware -> {
                        if(malware instanceof Rootkit){
                            Rootkit rootkit = (Rootkit) malware;
                            System.out.println(rootkit.toString());
                        }
                        else if(malware instanceof Ransomeware){
                            Ransomeware ransomeware = (Ransomeware) malware;
                            System.out.println(ransomeware.toString());
                        }
                        else if(malware instanceof Keylogger){
                            if( malware instanceof KernelKeylogger){
                                KernelKeylogger kernelKeylogger = (KernelKeylogger) malware;
                                System.out.println(kernelKeylogger.toString());
                            }
                            else {
                                Keylogger keylogger = (Keylogger) malware;
                                System.out.println(keylogger.toString());
                            }
                        }
                    });
                }

                for(int j = 0; j < computer.getUsers().size(); ++j){
                    computer.getUsers().forEach(user -> {
                        if(user instanceof Costumer){
                            Costumer costumer = (Costumer) user;
                            System.out.println(costumer.toString());
                        }
                        else if(user instanceof Employee){
                            Employee employee = (Employee) user;
                            System.out.println(employee.toString());
                        }
                    });
                }
            } else if (option == 2) {
                Computer computer = computerService.readComputer();
                computerService.addComputer(computer);
            } else if (option == 3) {
                int index = scanner.nextInt();
                Computer computer = computerService.readComputer();
                computerService.updateComputer(index, computer);
            } else if (option == 4) {
                int index = scanner.nextInt();
                computerService.deleteCostumerById(index);
            } else if (option == 5) {
                break;
            }
        }
    }
    public void printOptions(){
        System.out.println(" 0 - Get All");
        System.out.println(" 1 - Get By Id");
        System.out.println(" 2 - Add");
        System.out.println(" 3 - Update");
        System.out.println(" 4 - Delete");
        System.out.println(" 5 - Exit");
    }

    public void printMenu(){
        System.out.println(" 0 - View Menu");
        System.out.println(" 1 - Malwares");
        System.out.println(" 2 - Users");
        System.out.println(" 3 - Computers");
        System.out.println(" 4 - Companies");
        System.out.println(" 5 - Exit");

    }

    public void companyMenu() throws ParseException {
        while(true) {
            printOptions();
            int option = scanner.nextInt();
            if (option == 0) {
                for (int i = 0; i < companyService.getCompanies().size(); ++i) {
                    System.out.println("Name: " + companyService.getCompanies().get(i).getName());
                    System.out.println("Address: " + companyService.getCompanies().get(i).getAddress().toString());
                    for(int j = 0; j < companyService.getCompanies().get(i).getComputers().size(); ++j){
                        companyService.getCompanies().get(i).getComputers().get(j).getMalwares().forEach(malware -> {
                            if(malware instanceof Rootkit){
                                Rootkit rootkit = (Rootkit) malware;
                                System.out.println(rootkit.toString());
                            }
                            else if(malware instanceof Ransomeware){
                                Ransomeware ransomeware = (Ransomeware) malware;
                                System.out.println(ransomeware.toString());
                            }
                            else if(malware instanceof Keylogger){
                                if( malware instanceof KernelKeylogger){
                                    KernelKeylogger kernelKeylogger = (KernelKeylogger) malware;
                                    System.out.println(kernelKeylogger.toString());
                                }
                                else {
                                    Keylogger keylogger = (Keylogger) malware;
                                    System.out.println(keylogger.toString());
                                }
                            }
                        });


                    for(int k = 0; k < companyService.getCompanies().get(i).getComputers().get(j).getUsers().size(); ++k){
                        companyService.getCompanies().get(i).getComputers().get(j).getUsers().forEach(user -> {
                            if(user instanceof Costumer){
                                Costumer costumer = (Costumer) user;
                                System.out.println(costumer.toString());
                            }
                            else if(user instanceof Employee){
                                Employee employee = (Employee) user;
                                System.out.println(employee.toString());
                            }
                        });
                    }
                    }

                }
            } else if (option == 1) {
                int index = scanner.nextInt();
                Company company = companyService.getCompanyById(index);
                System.out.println("Name: " + company.getName());
                System.out.println("Address: " + company.getAddress().toString());
                for(int i = 0; i < company.getComputers().size(); ++i) {
                    for (int j = 0; j < company.getComputers().get(i).getMalwares().size(); ++j) {
                        company.getComputers().get(i).getMalwares().forEach(malware -> {
                            if(malware instanceof Rootkit){
                                Rootkit rootkit = (Rootkit) malware;
                                System.out.println(rootkit.toString());
                            }
                            else if(malware instanceof Ransomeware){
                                Ransomeware ransomeware = (Ransomeware) malware;
                                System.out.println(ransomeware.toString());
                            }
                            else if(malware instanceof Keylogger){
                                if( malware instanceof KernelKeylogger){
                                    KernelKeylogger kernelKeylogger = (KernelKeylogger) malware;
                                    System.out.println(kernelKeylogger.toString());
                                }
                                else {
                                    Keylogger keylogger = (Keylogger) malware;
                                    System.out.println(keylogger.toString());
                                }
                            }
                        });

                    for (int k = 0; k < company.getComputers().get(i).getUsers().size(); ++k) {
                        company.getComputers().get(i).getUsers().forEach(user -> {
                            if(user instanceof Costumer){
                                Costumer costumer = (Costumer) user;
                                System.out.println(costumer.toString());
                            }
                            else if(user instanceof Employee){
                                Employee employee = (Employee) user;
                                System.out.println(employee.toString());
                            }
                        });
                    }
                    }
                }
            } else if (option == 2) {
                Company company = companyService.readCompany();
                companyService.addCompanies(company);
            } else if (option == 3) {
                int index = scanner.nextInt();
                Company company = companyService.readCompany();
                companyService.updateCompany(index, company);
            } else if (option == 4) {
                int index = scanner.nextInt();
                companyService.deleteCompanyById(index);
            } else if (option == 5) {
                break;
            }
        }
    }

    public void menu() throws ParseException {
        while(true){
            printMenu();
            int option = scanner.nextInt();
            if(option == 0){
                printMenu();
            }
            else if(option == 1){
                malwareMenu();
            }
            else if(option == 2){
                userMenu();
            }
            else if(option == 3){
                computerMenu();
            }
            else if(option == 4){
                companyMenu();
            }
            else if(option == 5){
                break;
            }
        }
    }
}
