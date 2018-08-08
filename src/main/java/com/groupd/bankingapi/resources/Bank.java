/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.groupd.bankingapi.resources;

import com.groupd.bankingapi.model.*;
import com.groupd.bankingapi.services.*;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.*;

/**
 *
 * @author Tudor Chiribes, Cormac O'Donovan, Alex Andrews
 */

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Bank {
    
    Banking myBanking = new Banking();
    
    // Path for testing that the server is working as expected
    @GET
    @Path("/test")
    public Response testServer() {
        return Response.status(200).entity("Server is running!").build();
    }
    
    // Root path for the banking API
    @GET
    @Path("/")
    public Response bankAPI() {
        return Response.status(200).entity("Welcome to the Bank API!").build();
    }
    
    // Alert the user if an invalid path has been given for the API
    @GET
    @Path("/{param}")
    public Response invalidPath(@PathParam("param") String input) {
        String output = "Path not valid!";
        return Response.status(404).entity(output).build();
    }
    
    // Path to check bank details
    @GET
    @Path("/system")
    public Response accessSystem() {
        
        // Check for admin login
        if(!myBanking.adminLoggedIn()) {
            
            return Response.status(200).entity("Access denied!").build();
        }
        
        System.out.println("Name: " + myBanking.accessSystem().getName() +
                "\nAddress: " + myBanking.accessSystem().getAddress() +
                "\nSort Code: " + myBanking.accessSystem().getSortCode());
        
        return Response.status(200).entity("Bank " + myBanking.accessSystem().getName()
                + " is operational").build();
    }
    
    // Path to display database
    @GET
    @Path("/database")
    public Response accessDatabase() {
        
        // Check for admin login
        if(!myBanking.adminLoggedIn()) {
            
            return Response.status(200).entity("Access denied!").build();
        }
        
        // Check that database exists
        if(myBanking.accessDatabase() == null) {
            return Response.status(200).entity("Cannot connect to database!").build();
        }
        else {
            
            String response = "";
            // Iterate over the database and print user IDs and associated accounts
            for(ArrayList<Object> databaseRows : myBanking.accessDatabase()) {
                
                response += " USER: " + ((User)databaseRows.get(0)).getID();
                response += " Name: " + ((User)databaseRows.get(0)).getName();

                for(int i = 1; i < databaseRows.size(); i++) {

                    response += " Accounts: " +
                            ((Account)databaseRows.get(i)).getAccountNumber();
                }
            }
            
            return Response.status(200).entity(response).build();
        }
    }
    
    // Path for new user
    @POST
    @Path("/user/signup")
    public Response signupPage(String newUser) {
        
        myBanking.newUser(newUser);
        return Response.status(200).entity("Registration submitted").build();
    }
    
    // Path for existing user login
    @POST
    @Path("/user/login")
    public Response loginPage(String login) {
        
        // Check if successful
        if(myBanking.login(login)) {
            
            // Admin login
            if(myBanking.adminLoggedIn()) {

                return Response.status(200).entity("Welcome Admin!").build();
            }
            
            // User Login
            else if(myBanking.userLoggedIn()) {
                
                return Response.status(200).entity("Logged in successfully!").build();
            }
        }
        
        // Login condition not met
        return Response.status(200).entity("Login failed!").build();
    }
    
    // Path for logout
    @GET
    @Path("/user/logout")
    public Response logoutPage() {
        
        myBanking.logout();
        return Response.status(200).entity("Logout successful!").build();
    }
    
    // Path for user accounts
    @GET
    @Path("/user")
    public Response displayUser() {
        
        // User must be logged in
        if(!myBanking.userLoggedIn()) {
            return Response.status(200).entity("Please login first!").build();
        }
        
        // Display list of accounts in output
        return Response.status(200).entity("Displaying user accounts: " +
                myBanking.displayAccounts()).build();
    }
    
    // Path to create new account
    @POST
    @Path("/user/account/new")
    public String createAccount(String input) {
        
        // User must be logged in
        if(!myBanking.userLoggedIn()) {
            return "Please login first!";
        }
        
        // Parse JSON input and remove extra characters
        String newAccount = input.substring(1, input.length()-1);
        myBanking.newAccount(newAccount);
	return "Submitted new account: " + newAccount;
    }
    
    // Path to select account
    @GET
    @Path("/user/account/select")
    public Response selectAccount(@QueryParam("account") String accountNo) {
        
        // User must be logged in
        if(!myBanking.userLoggedIn()) {
            return Response.status(200).entity("Please login first!").build();
        }
        
        // Attempt to select account
        if(myBanking.selectAccount(accountNo)) {
            return Response.status(200).entity("Selecting account: " + accountNo).build();
        }
        else {
            return Response.status(200).entity("Account not found!").build();
        }
    }
    
    // Path to display account balance
    @GET
    @Path("/user/account/balance")
    public Response getBalance() {
        
        // User must be logged in
        if(!myBanking.userLoggedIn()) {
            return Response.status(200).entity("Please login first!").build();
        }
        
        // Account must be selected
        if(!myBanking.accountSelected()) {
            return Response.status(200).entity("Please select an account first").build();
        }
        else {
            return Response.status(200).entity("Displaying account balance: " +
                    myBanking.getAccountBalance() + " for account: " + myBanking.selectedAccount()).build();
        }
    }
    
    // Path to lodge money into account
    @POST
    @Path("/user/account/lodge")
    public String lodgeAmount(String input) {
        
        // User must be logged in
        if(!myBanking.userLoggedIn()) {
            return "Please login first!";
        }
        
        // Account must be selected
        if(!myBanking.accountSelected()) {
            return "Please select an account first";
        }
        else {
            if(myBanking.lodgeAmount(input)) {
                
                return "Amount lodged!";
            }
        }
        
        // Input could not be parsed
        return "Invalid input!";
    }
    
    // Path for transfer between accounts
    @POST
    @Path("/user/account/transfer")
    public String transferAmount(String input) {
        
        // User must be logged in
        if(!myBanking.userLoggedIn()) {
            return "Please login first!";
        }
        
        // Account must be selected
        if(!myBanking.accountSelected()) {
            return "Please select an account first";
        }
        else {
            if(myBanking.transferAmount(input)) {
                
                return "Amount transfered!";
            }
        }
        
        // Input could not be parsed
        return "Invalid input!";
    }
    
    @POST
    @Path("/user/account/withdraw")
    public String withdrawAmount(String input) {
        
        // User must be logged in
        if(!myBanking.userLoggedIn()) {
            return "Please login first!";
        }
        
        // Account must be selected
        if(!myBanking.accountSelected()) {
            return "Please select an account first";
        }
        else {
            if(myBanking.withdrawAmount(input)) {
                
                return "Amount withdrew!";
            }
        }
        
        // Input could not be parsed
        return "Invalid input!";
    }
}
