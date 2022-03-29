package com.company;

import MalwareClasses.Rootkit;
import Service.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

public class Main {

    public static void main(String[] args) throws ParseException {
        Service service = Service.getInstance();
        service.menu();
    }
}
