package Services;

import Entities.Keylogger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class KeyloggerService {
    private List<Keylogger> keyloggers = new ArrayList<>();
    private static KeyloggerService instance;

    private KeyloggerService(){}

    public static KeyloggerService getInstance(){
        if(instance == null){
            instance = new KeyloggerService();
        }
        return instance;
    }

    public List<Keylogger> getKeyloggers() {
        List<Keylogger> keyloggersCopy = new ArrayList<>();
        keyloggersCopy.addAll(this.keyloggers);
        return keyloggersCopy;
    }

    public Keylogger getKeyloggerById(int index){
        Keylogger keylogger = new Keylogger();
        for(int i = 0; i < this.keyloggers.size(); ++i){
            if(this.keyloggers.get(i).getId() == index){
                keylogger = this.keyloggers.get(i);
            }
        }
        return keylogger;
    }

    public void updateKeylogger(int index, Keylogger keylogger){
        for(int i = 0; i < this.keyloggers.size(); ++i){
            if(this.keyloggers.get(i).getId() == index){
                this.keyloggers.remove(i);
                this.keyloggers.add(index, keylogger);
                break;
            }
        }
    }

    public void addKeylogger(Keylogger keylogger){
        this.keyloggers.add(keylogger);
    }

    public void deleteKeyloggerById(int index){
        for(int i = 0; i < this.keyloggers.size(); ++i){
            if(this.keyloggers.get(i).getId() == index){
                this.keyloggers.remove(i);
                break;
            }
        }
    }

    public void deteleKeylogger(Keylogger keylogger){
        for(int i = 0;i < this.keyloggers.size(); ++i){
            if(this.keyloggers.get(i).equals(keylogger)){
                this.keyloggers.remove(i);
                break;
            }
        }
    }

    public double findRating(Keylogger keylogger){
        double rating = 0;
        if((keylogger.getModifiedRegisters().size() > 0) ){
            for (String element : keylogger.getModifiedRegisters()) {
                if (element.equals("HKLM-run") || element.equals("HKCU-run"))
                    rating += 20;
            }
        }
        if(keylogger.getUsedFunctions().size() > 0) {
            for (String element : keylogger.getUsedFunctions()) {
                if (element.equals("CreateFileW") || element.equals("OpenProcess") || element.equals("ReadFile") || element.equals("WriteFile") || element.equals("RegisterHotKey") || element.equals("SetWindowsHookEx"))
                    rating += 30;
            }
        }
        if(keylogger.getUsedKeys().size() > 0){
            for (String element : keylogger.getUsedKeys()) {
                if (element.equals("[Up]") || element.equals("[Num Lock]") || element.equals("[Down]") || element.equals("[Right]") || element.equals("[UP]") || element.equals("[Left]") || element.equals("[PageDown]"))
                    rating += 10;
            }
        }
        keylogger.setRating(rating);
        return rating;
    }

    public Keylogger readKeylogger() throws ParseException {
        Scanner scanner = new Scanner(System.in);
        Keylogger keylogger = new Keylogger();
        System.out.println("Id");
        try {
            keylogger.setId(scanner.nextInt());
        } catch (Exception e){
            System.out.println("Provide int");
            keylogger.setId(scanner.nextInt());
        }

        System.out.println("Name");
        keylogger.setName(scanner.next());

        System.out.println("Creation Date - dd/mm/yyyy");
        String date;
        try {
            date = scanner.next();
        } catch (Exception e){
            System.out.println("Provide date in format - dd/mm/yyyy");
            date = scanner.next();
        }
        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        keylogger.setCreationDate(date1);

        System.out.println("Infection Method");
        keylogger.setInfectionMethod(scanner.next());

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
        keylogger.setModifiedRegisters(arr);

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
        keylogger.setUsedFunctions(arr1);

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
        keylogger.setUsedKeys(arr2);
        findRating(keylogger);
        return keylogger;
    }
}
