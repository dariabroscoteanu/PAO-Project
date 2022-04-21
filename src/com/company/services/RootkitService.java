package com.company.services;

import com.company.entities.Rootkit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class RootkitService {
    private List<Rootkit> rootkits = new ArrayList<>();
    private static RootkitService instance;

    private RootkitService(){}

    public static RootkitService getInstance(){
        if(instance == null){
            instance = new RootkitService();
        }
        return instance;
    }

    public List<Rootkit> getRootkits() {
        List<Rootkit> rootkitsCopy = new ArrayList<>();
        rootkitsCopy.addAll(this.rootkits);
        return rootkitsCopy;
    }

    public Rootkit getRootkitById(int index){
        Rootkit rootkit = new Rootkit();
        for(int i = 0; i < this.rootkits.size(); ++i){
            if(this.rootkits.get(i).getId() == index){
                rootkit = this.rootkits.get(i);
            }
        }
        return rootkit;
    }

    public void updateRootkit(int index, Rootkit rootkit){
        for(int i = 0; i < this.rootkits.size(); ++i){
            if(this.rootkits.get(i).getId() == index){
                this.rootkits.remove(i);
                this.rootkits.add(i, rootkit);
                break;
            }
        }
    }

    public void addRootkit(Rootkit rootkit){
        this.rootkits.add(rootkit);
    }

    public void deleteRootkitById(int index){
        for(int i = 0; i < this.rootkits.size(); ++i){
            if(this.rootkits.get(i).getId() == index){
                this.rootkits.remove(i);
                break;
            }
        }
    }

    public double findRating(Rootkit rootkit){
        double rating = 0;
        if((rootkit.getModifiedRegisters().size() > 0) ){
            for (String element : rootkit.getModifiedRegisters()) {
                if (element.equals("HKLM-run") || element.equals("HKCU-run"))
                    rating += 20;
            }
        }
        List<String> specialImports = Arrays.asList("System Service Descriptor Table", "SSDT", "NtCreateFile");
        if(rootkit.getImports().size() > 0) {
            for (String element : rootkit.getImports()) {
                if (specialImports.contains(element))
                    rating += 100;
            }
        }

        if(rootkit.getConfigFiles().size() > 0) {
            for (String element : rootkit.getConfigFiles()) {
                if (element.equals("ntoskrnl.exe")) {
                    rating *= 2;
                    break;
                }
            }
        }
        rootkit.setRating(rating);
        return rating;
    }

    public void deteleRootkit(Rootkit rootkit){
        for(int i = 0;i < this.rootkits.size(); ++i){
            if(this.rootkits.get(i).equals(rootkit)){
                this.rootkits.remove(i);
                break;
            }
        }
    }

    public Rootkit readRootkit() throws ParseException {
        Scanner scanner = new Scanner(System.in);
        Rootkit rootkit = new Rootkit();
        System.out.println("Id");

        try {
            rootkit.setId(scanner.nextInt());
        } catch (Exception e){
            System.out.println("Provide int");
            rootkit.setId(scanner.nextInt());
        }

        System.out.println("Name");
        rootkit.setName(scanner.next());

        System.out.println("Creation Date");
        String date;
        try {
            date = scanner.next();
        } catch (Exception e){
            System.out.println("Provide date in format - dd/mm/yyyy");
            date = scanner.next();
        }
        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        rootkit.setCreationDate(date1);

        System.out.println("Infection Method");
        rootkit.setInfectionMethod(scanner.next());

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
        rootkit.setModifiedRegisters(arr);

        System.out.println("Number of imports");
        try {
            nr = scanner.nextInt();
        } catch (Exception e){
            System.out.println("Enter a number");
            nr = scanner.nextInt();
        }
        System.out.println("Imports");
        Set<String> arr1 = new HashSet<>();
        for(int i = 0; i < nr; ++i){
            String str = scanner.next();
            arr1.add(str);
        }
        rootkit.setImports(arr1);

        System.out.println("Number of Config Files");
        try {
            nr = scanner.nextInt();
        } catch (Exception e){
            System.out.println("Enter a number");
            nr = scanner.nextInt();
        }
        System.out.println("Config Files");
        Set<String> arr2 = new HashSet<>();
        for(int i = 0; i < nr; ++i){
            String str = scanner.next();
            arr2.add(str);
        }
        rootkit.setConfigFiles(arr2);
        findRating(rootkit);
        return rootkit;
    }


}
