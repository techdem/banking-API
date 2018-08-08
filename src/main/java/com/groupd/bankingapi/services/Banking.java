/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.groupd.bankingapi.services;

import java.util.ArrayList;
import com.groupd.bankingapi.model.*;

/**
 *
 * @author tudor
 */
public class Banking {
    
    private static Bank myBank;
    private static ArrayList<Object[][]> dataBase = new ArrayList();
    
    static {
        myBank = new Bank("10-20-30","Safest Bank","100 Safe Road");
        
        dataBase.add(new Object[][]{{new User(1,"Tudor Chiribes","Christchurch","root"),
        new Account("111111")}});
    }
    
    public static ArrayList<Object[][]> accessDatabase() {
        
        return dataBase;
    }
    
    public static Bank accessSystem() {

        return myBank;
    }
    
    public void newUser(String newUser) {
        
        int id = dataBase.size()+1;
        String[] input = newUser.split(",");
        
        String name = input[0].substring(1);
        String address = input[1];
        String pass = input[2].substring(0, input[2].length() - 1);
        
        // Print parameters for debugging
        System.out.println("First parameter: " + name);
        System.out.println("Second parameter: " + address);
        System.out.println("Third parameter: " + pass);
        
        dataBase.add(new Object[][]{{new User(id,name,address,pass)}});
        
        for(Object[][] o : dataBase) {
            
            System.out.println(((User)o[0][0]).getID());
        }
    }
}
