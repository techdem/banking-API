/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.groupd.bankingapi.model;

/**
 *
 * @author Tudor Chiribes, Cormac O'Donovan, Alex Andrews
 */

public class Account {
    
    private String accountNumber;
    private int balance;
    
    public Account(String number) {
        this.accountNumber = number;
    }
    
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public void setBalance(int newBalance) {
        this.balance = newBalance;
    }
    
    public int getBalance() {
        return balance;
    }
}
