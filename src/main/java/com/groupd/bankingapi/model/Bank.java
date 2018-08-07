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

public class Bank {
    
    private String bankName;
    private String bankAddress;
    
    public Bank(String name, String address) {
        this.bankName = name;
        this.bankAddress = address;
    }
    
    public String getName() {
        return bankName;
    }
    
    public String getAddress() {
        return bankAddress;
    }
}
