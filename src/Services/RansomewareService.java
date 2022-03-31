package Services;

import Entities.Ransomeware;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class RansomewareService {
    private List<Ransomeware> ransomewares = new ArrayList<>();
    private static RansomewareService instance;

    private RansomewareService(){}

    public static RansomewareService getInstance(){
        if(instance == null){
            instance = new RansomewareService();
        }
        return instance;
    }

    public List<Ransomeware> getRansomewares() {
        List<Ransomeware> ransomewaresCopy = new ArrayList<>();
        ransomewaresCopy.addAll(this.ransomewares);
        return ransomewaresCopy;
    }

    public Ransomeware getRansomewareById(int index){
        Ransomeware ransomeware = new Ransomeware();
        for(int i = 0; i < this.ransomewares.size(); ++i){
            if(this.ransomewares.get(i).getId() == index){
                ransomeware = this.ransomewares.get(i);
            }
        }
        return ransomeware;
    }

    public void updateRansomeware(int index, Ransomeware ransomeware){
        for(int i = 0; i < this.ransomewares.size(); ++i){
            if(this.ransomewares.get(i).getId() == index){
                this.ransomewares.remove(i);
                this.ransomewares.add(index, ransomeware);
                break;
            }
        }
    }

    public void addRansomeware(Ransomeware ransomeware){
        this.ransomewares.add(ransomeware);
    }

    public void deleteRansomewareById(int index){
        for(int i = 0; i < this.ransomewares.size(); ++i){
            if(this.ransomewares.get(i).getId() == index){
                this.ransomewares.remove(i);
                break;
            }
        }
    }

    public void deteleRansomeware(Ransomeware ransomeware){
        for(int i = 0;i < this.ransomewares.size(); ++i){
            if(this.ransomewares.get(i).equals(ransomeware)){
                this.ransomewares.remove(i);
                break;
            }
        }
    }

    public double findRating(Ransomeware ransomeware){
        double rating = 0;
        if((ransomeware.getModifiedRegisters().size() > 0) ){
            for (String element : ransomeware.getModifiedRegisters()) {
                if (element.equals("HKLM-run") || element.equals("HKCU-run"))
                    rating += 20;
            }
        }
        rating += ransomeware.getEncryptionRating() +  ransomeware.getHidingRating();
        ransomeware.setRating(rating);
        return rating;
    }

    public Ransomeware readRansomeware() throws ParseException {
        Scanner scanner = new Scanner(System.in);
        Ransomeware ransomeware = new Ransomeware();
        System.out.println("Id");
        try {
            ransomeware.setId(scanner.nextInt());
        } catch (Exception e){
            System.out.println("Provide int");
            ransomeware.setId(scanner.nextInt());
        }


        System.out.println("Name");
        ransomeware.setName(scanner.next());

        System.out.println("Creation Date");
        String date;
        try {
            date = scanner.next();
        } catch (Exception e){
            System.out.println("Provide date in format - dd/mm/yyyy");
            date = scanner.next();
        }
        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        ransomeware.setCreationDate(date1);

        System.out.println("Infection Method");
        ransomeware.setInfectionMethod(scanner.next());

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
        ransomeware.setModifiedRegisters(arr);

        System.out.println("Encryption Rating");
        try {
            ransomeware.setEncryptionRating(scanner.nextDouble());
        } catch (Exception e){
            System.out.println("Enter a double");
            ransomeware.setEncryptionRating(scanner.nextDouble());
        }


        System.out.println("Hiding Rating");
        try {
            ransomeware.setHidingRating(scanner.nextDouble());
        } catch (Exception e){
            System.out.println("Enter a double");
            ransomeware.setHidingRating(scanner.nextDouble());
        }

        findRating(ransomeware);

        return ransomeware;
    }
}
