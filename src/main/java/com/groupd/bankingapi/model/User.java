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
    
    private int id;
    private String userName;
    private String userAddress;
    private String userPassword;
    
    public User(int id, String name, String address, String pass) {
        this.id = id;
        this.userName = name;
        this.userAddress = address;
        this.userPassword = pass;
    }
    
    public int getID() {
        
        return id;
    }
    
    public String getName() {
        return userName;
    }
    
    public String getAddress() {
        return userAddress;
    }
    
    public String getPassword() {
        return userPassword;
    }
}
