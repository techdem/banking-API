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
    private static ArrayList<String[][]> dataBase = new ArrayList();
    
    {
        myBank = new Bank("10-20-30","Safest Bank","100 Safe Road");
        
        dataBase.add(new String[][]{{new User("Tudor Chiribes","Christchurch","root").getName()},
        {new Account("111111").getAccountNumber()}});
        
    }
    
    public static ArrayList accessDatabase() {
        
        return dataBase;
    }
    
    public static Bank accessSystem() {

        return myBank;
    }
}
