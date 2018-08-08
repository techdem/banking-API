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

/**
 *
 * @author Tudor Chiribes
 */

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Bank {
    
    Banking myBanking = new Banking();
    
    boolean adminLoggedIn;
    boolean userLoggedIn;
    
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
    
    // Path for testing that the server is accepting POST methods
    @POST
    @Path("/")
    public String testPost(String input) {
	return "Echo post: " + input;
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
        
        if(!myBanking.adminLoggedIn()) {
            
            return Response.status(200).entity("Access denied!").build();
        }
        // Check that database exists
        if(myBanking.accessDatabase() == null) {
            return Response.status(200).entity("Cannot connect to database!").build();
        }
        else {
            
            // Iterate over the database and print user IDs and associated accounts
            for(Object[] usr : myBanking.accessDatabase()) {
                
                System.out.println("USER: " + ((User)usr[0]).getID());
                System.out.println("\tName: " + ((User)usr[0]).getName());
                
                for(int i = 1; i < usr.length; i++) {
                    
                    System.out.println("\tAccounts: " + ((Account)usr[i]).getAccountNumber());
                }
            }
            
            return Response.status(200).entity("Database is connected!").build();
        }
    }
    
    @POST
    @Path("/user/signup")
    public Response signupPage(String newUser) {
        
        myBanking.newUser(newUser);
        return Response.status(200).entity("You have registered successfully").build();
    }
    
    @POST
    @Path("/user/login")
    public Response loginPage(String login) {
        
        if(myBanking.login(login)) {
            if(myBanking.adminLoggedIn()) {

                return Response.status(200).entity("Welcome Admin!").build();
            }
            else if(myBanking.userLoggedIn()) {
                
                return Response.status(200).entity("Logged in successfully!").build();
            }
        }
        return Response.status(200).entity("Login failed!").build();
    }
    
    @GET
    @Path("/user")
    public Response displayUser() {
        
        if(!myBanking.userLoggedIn()) {
            return Response.status(200).entity("Please login first!").build();
        }
        
        return Response.status(200).entity("Displaying Accounts: " +
                myBanking.displayAccounts()).build();
    }
    
    @POST
    @Path("/user/account/new")
    public String createAccount(String input) {
	return "Creating new account: " + input;
    }
    
    @POST
    @Path("/user/account/select")
    public String selectAccount(String input) {
	return "Selecting account: " + input;
    }
    
    @GET
    @Path("/user/account/balance")
    public Response getBalance() {
        return Response.status(200).entity("Displaying account balance!").build();
    }
    
    @POST
    @Path("/user/account/lodge")
    public String lodgeAmount(String input) {
	return "Lodging amount: " + input;
    }
    
    @POST
    @Path("/user/account/transfer")
    public String transferAmount(@QueryParam("account") int account,
            @QueryParam("amount") int amount) {
        return "Transferring " + amount + " into account " + account;
    }
    
    @POST
    @Path("/user/account/withdraw/{param}")
    public String withdrawAmount(@PathParam("param") String input) {
	return "Withdrawing " + input;
    }
}
