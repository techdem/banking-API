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
    
    // Declare new bank, database and environment variables
    private static Bank myBank;
    private static ArrayList<ArrayList<Object>> dataBase = new ArrayList<ArrayList<Object>>();
    private static boolean adminLoggedIn;
    private static boolean userLoggedIn;
    private static int loggedInID;
    private static Account selectedAccount;
    
    // Use static initialization block to populate database
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
    
    // Return the database
    public static ArrayList<ArrayList<Object>> accessDatabase() {
        
        return dataBase;
    }
    
    // Return bank details
    public static Bank accessSystem() {

        return myBank;
    }
    
    // Create new user
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
    
    // Login by checking for username and password
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
    
    // Return current user state
    public boolean userLoggedIn() {
        
        return userLoggedIn;
    }
    
    // Return current user state
    public boolean adminLoggedIn() {
        
        return adminLoggedIn;
    }
    
    // Logout any user or admin and reset state
    public void logout() {
        
        adminLoggedIn = false;
        userLoggedIn = false;
        selectedAccount = null;
    }
    
    // Return list of account numbers that belong to the user
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
    
    // Create new account for the logged in user
    public void newAccount(String newAccount) {
        
        for(ArrayList<Object> databaseRows : dataBase) {
                            
            if(((User)databaseRows.get(0)).getID() == loggedInID) {

                dataBase.get(loggedInID-1).add(new Account(newAccount));
            }
        }
    }
    
    // Select an account that belongs to the logged in user
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
    
    // Check if an account has been selected
    public boolean accountSelected() {
        
        if(selectedAccount == null) {
            
            return false;
        }
        else {
            
            return true;
        }
    }
    
    // Return the number of the selected account
    public String selectedAccount() {
        
        return selectedAccount.getAccountNumber();
    }
    
    // Return the balance of the currently selected account
    public int getAccountBalance() {
        
        return selectedAccount.getBalance();
    }
    
    // Lodge amount to currently selected account
    public boolean lodgeAmount(String input) {
        
        int lodgeAmount = 0;
        String amount = input.substring(1, input.length()-1);
            
        try {

            lodgeAmount = Integer.parseInt(amount);
            int currentBalance = selectedAccount.getBalance();
            selectedAccount.setBalance(currentBalance+lodgeAmount);
            return true;
        }
        catch (NumberFormatException e) {

            System.out.println("Cannot convert lodge amount!");
        }
        
        return false;
    }
    
    // Withdraw amount from the currently selected account
    public boolean withdrawAmount(String input) {
        
        int withdrawAmount = 0;
        String amount = input.substring(1, input.length()-1);
        
        try {

            withdrawAmount = Integer.parseInt(amount);
            int currentBalance = selectedAccount.getBalance();
            selectedAccount.setBalance(currentBalance-withdrawAmount);
            return true;
        }
        catch (NumberFormatException e) {

            System.out.println("Cannot convert withdraw amount!");
        }
        
        return false;
    }
    
    // Transfer amount from currently selected account to specified account
    public boolean transferAmount(String transfer) {
        
        int transferAmount = 0;
        String[] input = transfer.split(",");
        String accountNo = input[0].substring(1);
        String amountString = input[1].substring(0, input[1].length() - 1);
        
        try {
            
            transferAmount = Integer.parseInt(amountString);
            
            for(ArrayList<Object> databaseRows : dataBase) {
            
                for(int i = 1; i < databaseRows.size(); i++) {

                    if(((Account)databaseRows.get(i)).getAccountNumber().equals(accountNo)) {

                        selectedAccount.setBalance(selectedAccount.getBalance()-transferAmount);
                        int oldBalance = ((Account)databaseRows.get(i)).getBalance();
                        ((Account)databaseRows.get(i)).setBalance(oldBalance + transferAmount);
                        return true;
                    }
                }
            }
        }
        catch (NumberFormatException e) {

            System.out.println("Cannot convert transfer amount!");
        }
        
        return false;
    }
}
