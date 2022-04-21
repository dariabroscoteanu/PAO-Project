package com.company.services;

import com.company.entities.KernelKeylogger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class KernelKeyloggerService implements KernelKeyloggerInterface {
    private List<KernelKeylogger> kernelKeyloggers = new ArrayList<>();
    private static KernelKeyloggerService instance;

    private KernelKeyloggerService(){}

    public static KernelKeyloggerService getInstance(){
        if(instance == null){
            instance = new KernelKeyloggerService();
        }
        return instance;
    }

    public List<KernelKeylogger> getKernelKeyloggers() {
        List<KernelKeylogger> kernelKeyloggersCopy = new ArrayList<>();
        kernelKeyloggersCopy.addAll(this.kernelKeyloggers);
        return kernelKeyloggersCopy;
    }

    public KernelKeylogger getKernelKeyloggerById(int index){
        KernelKeylogger kernelKeylogger = new KernelKeylogger();
        for(int i = 0; i < this.kernelKeyloggers.size(); ++i){
            if(this.kernelKeyloggers.get(i).getId() == index){
                kernelKeylogger = this.kernelKeyloggers.get(i);
            }
        }
        return kernelKeylogger;
    }

    public void updateKernelKeylogger(int index, KernelKeylogger kernelKeylogger){
        for(int i = 0; i < this.kernelKeyloggers.size(); ++i){
            if(this.kernelKeyloggers.get(i).getId() == index){
                this.kernelKeyloggers.remove(i);
                this.kernelKeyloggers.add(i, kernelKeylogger);
                break;
            }
        }
    }

    public void addKernelKeylogger(KernelKeylogger kernelKeylogger){
        this.kernelKeyloggers.add(kernelKeylogger);
    }

    public void deleteKernelKeyloggerById(int index){
        for(int i = 0; i < this.kernelKeyloggers.size(); ++i){
            if(this.kernelKeyloggers.get(i).getId() == index){
                this.kernelKeyloggers.remove(i);
                break;
            }
        }
    }

    public void deteleKernelKeylogger(KernelKeylogger kernelKeylogger){
        for(int i = 0;i < this.kernelKeyloggers.size(); ++i){
            if(this.kernelKeyloggers.get(i).equals(kernelKeylogger)){
                this.kernelKeyloggers.remove(i);
                break;
            }
        }
    }

    public double findRating(KernelKeylogger kernelKeylogger){
        double rating = 0;
        if((kernelKeylogger.getModifiedRegisters().size() > 0) ){
            for (String element : kernelKeylogger.getModifiedRegisters()) {
                if (element.equals("HKLM-run") || element.equals("HKCU-run"))
                    rating += 20;
            }
        }
        if(kernelKeylogger.getUsedFunctions().size() > 0) {
            for (String element : kernelKeylogger.getUsedFunctions()) {
                if (element.equals("CreateFileW") || element.equals("OpenProcess") || element.equals("ReadFile") || element.equals("WriteFile") || element.equals("RegisterHotKey") || element.equals("SetWindowsHookEx"))
                    rating += 30;
            }
        }
        if(kernelKeylogger.getUsedKeys().size() > 0){
            for (String element : kernelKeylogger.getUsedKeys()) {
                if (element.equals("[Up]") || element.equals("[Num Lock]") || element.equals("[Down]") || element.equals("[Right]") || element.equals("[UP]") || element.equals("[Left]") || element.equals("[PageDown]"))
                    rating += 10;
            }
        }
        if(kernelKeylogger.isHidingRecords()){
            rating += 30;
        }
        if(kernelKeylogger.isHidingFiles()){
            rating += 20;
        }
        kernelKeylogger.setRating(rating);
        return rating;
    }

    public KernelKeylogger readKernelkeylogger() throws ParseException {
        Scanner scanner = new Scanner(System.in);
        KernelKeylogger kernelKeylogger = new KernelKeylogger();
        System.out.println("Id");
        try {
            kernelKeylogger.setId(scanner.nextInt());
        } catch (Exception e){
            System.out.println("Provide int");
            kernelKeylogger.setId(scanner.nextInt());
        }

        System.out.println("Name");
        kernelKeylogger.setName(scanner.next());

        System.out.println("Creation Date - dd/mm/yyyy");
        String date;
        try {
            date = scanner.next();
        } catch (Exception e){
            System.out.println("Provide date in format - dd/mm/yyyy");
            date = scanner.next();
        }
        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        kernelKeylogger.setCreationDate(date1);

        System.out.println("Infection Method");
        kernelKeylogger.setInfectionMethod(scanner.next());

        System.out.println("Number of modified registers");
        int nr;
        try {
            nr = scanner.nextInt();
        } catch (Exception e){
            System.out.println("Enter a number");
            nr = scanner.nextInt();
        }
        System.out.println("Modified registers");
        List<String> arr = new ArrayList<>();
        for(int i = 0; i < nr; ++i){
            String str = scanner.next();
            arr.add(str);
        }
        kernelKeylogger.setModifiedRegisters(arr);

        System.out.println("Number of used functions");
        try {
            nr = scanner.nextInt();
        } catch (Exception e){
            System.out.println("Enter a number");
            nr = scanner.nextInt();
        }
        System.out.println("Used Functions");
        ArrayList<String> arr1 = new ArrayList<>();
        for(int i = 0; i < nr; ++i){
            String str = scanner.next();
            arr1.add(str);
        }
        kernelKeylogger.setUsedFunctions(arr1);

        System.out.println("Number of used keys");
        try {
            nr = scanner.nextInt();
        } catch (Exception e){
            System.out.println("Enter a number");
            nr = scanner.nextInt();
        }
        System.out.println("Used keys");
        ArrayList<String> arr2 = new ArrayList<>();
        for(int i = 0; i < nr; ++i){
            String str = scanner.next();
            arr2.add(str);
        }
        kernelKeylogger.setUsedKeys(arr2);
        System.out.println("Is hiding files?");
        try {
            kernelKeylogger.setHidingFiles(scanner.nextBoolean());
        } catch (Exception e){
            System.out.println("Enter a boolean");
            kernelKeylogger.setHidingFiles(scanner.nextBoolean());
        }

        System.out.println("Is hiding records?");
        try {
            kernelKeylogger.setHidingRecords(scanner.nextBoolean());
        } catch (Exception e){
            System.out.println("Enter a boolean");
            kernelKeylogger.setHidingRecords(scanner.nextBoolean());
        }

        findRating(kernelKeylogger);
        return kernelKeylogger;
    }
}
