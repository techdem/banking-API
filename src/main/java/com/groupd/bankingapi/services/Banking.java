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
    private static ArrayList<Object[]> dataBase = new ArrayList();
    private static boolean adminLoggedIn;
    private static boolean userLoggedIn;
    private static int loggedInID;
    
    static {
        myBank = new Bank("10-20-30","Safest Bank","100 Safe Road");
        
        dataBase.add(new Object[]{new User(1,"Tudor Chiribes","Christchurch","root"),
        new Account("111111"), new Account("222222")});
        
        dataBase.add(new Object[]{new User(2,"Test User","Test City","test password"),
        new Account("test account")});
    }
    
    public static ArrayList<Object[]> accessDatabase() {
        
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
        
        dataBase.add(new Object[]{new User(id,name,address,pass)});
    }
    
    public boolean login(String login) {
        
        String[] input = login.split(",");
        
        String user = input[0].substring(1).toLowerCase();
        String password = input[1].substring(0, input[1].length() - 1);
        
        adminLoggedIn = false;
        userLoggedIn = false;
        
        for(Object[] o : dataBase) {
            
            if(((User)o[0]).getName().toLowerCase().equals(user) &&
                    ((User)o[0]).getPassword().equals(password)) {
                
                if(((User)o[0]).getName().toLowerCase().equals("tudor chiribes")) {
                    
                    adminLoggedIn = true;
                    loggedInID = 1;
                }
                userLoggedIn = true;
                loggedInID = ((User)o[0]).getID();
                return true;
            }
        }
        
        return false;
    }
    
    public boolean userLoggedIn() {
        
        return userLoggedIn;
    }
    
    public boolean adminLoggedIn() {
        
        return adminLoggedIn;
    }
    
    public String displayAccounts() {
        
        String accounts = "";
        
        for(Object[] o : dataBase) {
            
            if(((User)o[0]).getID() == loggedInID) {
                
                for(int i = 1; i < o.length; i++) {
                    
                    accounts += ((Account)o[i]).getAccountNumber() + " ";
                }
            }
        }
        
        return accounts;
    }
}
