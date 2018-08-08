/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.groupd.bankingapi.services;

import java.util.*;
import com.groupd.bankingapi.model.*;

/**
 *
 * @author tudor
 */
public class Banking {
    
    private static Bank myBank;
    private static ArrayList<ArrayList<Object>> dataBase = new ArrayList<ArrayList<Object>>();
    private static boolean adminLoggedIn;
    private static boolean userLoggedIn;
    private static int loggedInID;
    private static Account selectedAccount;
    
    static {
        myBank = new Bank("10-20-30","Safest Bank","100 Safe Road");
        
        dataBase.add(new ArrayList<Object>());
        
        dataBase.get(0).add(new User(1,"Tudor Chiribes","Christchurch","root"));
        dataBase.get(0).add(new Account("111111"));
        dataBase.get(0).add(new Account("222222"));
        
        dataBase.add(new ArrayList<Object>());
        
        dataBase.get(1).add(new User(2,"Test User","Test City","test password"));
        dataBase.get(1).add(new Account("testACCnumber"));
        dataBase.get(1).add(new Account("testACCnumber2"));
    }
    
    public static ArrayList<ArrayList<Object>> accessDatabase() {
        
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
        
        dataBase.add(new ArrayList<Object>());
        dataBase.get(dataBase.size()-1).add(new User(id,name,address,pass));
    }
    
    public boolean login(String login) {
        
        String[] input = login.split(",");
        
        String user = input[0].substring(1).toLowerCase();
        String password = input[1].substring(0, input[1].length() - 1);
        
        adminLoggedIn = false;
        userLoggedIn = false;
        selectedAccount = null;
        
        for(ArrayList<Object> databaseRows : dataBase) {
                            
            if(((User)databaseRows.get(0)).getName().toLowerCase().equals(user) &&
                    ((User)databaseRows.get(0)).getPassword().equals(password)) {

                if(((User)databaseRows.get(0)).getName().toLowerCase().equals("tudor chiribes")) {

                        adminLoggedIn = true;
                        loggedInID = 1;
                    }

                userLoggedIn = true;
                loggedInID = ((User)databaseRows.get(0)).getID();
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
        
        for(ArrayList<Object> databaseRows : dataBase) {
                            
            if(((User)databaseRows.get(0)).getID() == loggedInID) {

                for(int i = 1; i < databaseRows.size(); i++) {

                    accounts += ((Account)databaseRows.get(i)).getAccountNumber() + ",";
                }
            }
        }
        
        return accounts;
    }
    
    public void newAccount(String newAccount) {
        
        for(ArrayList<Object> databaseRows : dataBase) {
                            
            if(((User)databaseRows.get(0)).getID() == loggedInID) {

                dataBase.get(loggedInID-1).add(new Account(newAccount));
            }
        }
    }
    
    public boolean selectAccount(String accountNo) {
        
        for(ArrayList<Object> databaseRows : dataBase) {
                            
            if(((User)databaseRows.get(0)).getID() == loggedInID) {
                
                for(int i = 1; i < databaseRows.size(); i++) {

                    if(((Account)databaseRows.get(i)).getAccountNumber().equals(accountNo)) {
                        
                        selectedAccount = (Account)databaseRows.get(i);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public boolean accountSelected() {
        
        if(selectedAccount == null) {
            
            return false;
        }
        else {
            
            return true;
        }
    }
    
    public String selectedAccount() {
        
        return selectedAccount.getAccountNumber();
    }
    
    public int getAccountBalance() {
        
        return selectedAccount.getBalance();
    }
    
    public void lodgeAmount(int amount) {
        
        int currentBalance = selectedAccount.getBalance();
        selectedAccount.setBalance(currentBalance+amount);
    }
    
    public void withdrawAmount(int amount) {
        
        int currentBalance = selectedAccount.getBalance();
        selectedAccount.setBalance(currentBalance-amount);
    }
}
