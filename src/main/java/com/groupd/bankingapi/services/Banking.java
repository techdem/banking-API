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
    
    private static ArrayList<String[][]> dataBase = new ArrayList();
    
    {
        dataBase.add(new String[][]{{new User("Tudor Chiribes", "Christchurch").getName()},
        {new Account("111111").getAccountNumber()}});
    }
    
    public static ArrayList accessDatabase() {
        
        return dataBase;
    }
}
