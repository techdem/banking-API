/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.groupd.bankingapi.model;

/**
 *
 * @author tudor
 */

public class Account {
    
    private String accountNumber;
    
    public Account(String number) {
        this.accountNumber = number;
    }
    
    public String getAccountNumber() {
        return accountNumber;
    }
}
