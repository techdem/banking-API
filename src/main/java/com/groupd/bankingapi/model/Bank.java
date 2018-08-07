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
    
    private String sortCode;
    private String bankName;
    private String bankAddress;
    
    public Bank(String code, String name, String address) {
        this.sortCode = code;
        this.bankName = name;
        this.bankAddress = address;
    }
    
    public String getSortCode() {
        return sortCode;
    }
    
    public String getName() {
        return bankName;
    }
    
    public String getAddress() {
        return bankAddress;
    }
}
