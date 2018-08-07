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

public class User {
    
    private String userName;
    private String userAddress;
    
    public User(String name, String address) {
        this.userName = name;
        this.userAddress = address;
    }
    
    public String getName() {
        return userName;
    }
    
    public String getAddress() {
        return userAddress;
    }
}
