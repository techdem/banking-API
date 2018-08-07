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
    
    @GET
    @Path("/test")
    public Response testServer() {
        return Response.status(200).entity("Server is running!").build();
    }
    
    @GET
    @Path("/")
    public Response bankAPI() {
        return Response.status(200).entity("Welcome to the Bank API!").build();
    }
    
    @POST
    @Path("/")
    public String testPost(String input) {
	return "Testing post: " + input;
    }
    
    @GET
    @Path("/{param}")
    public Response invalidPath(@PathParam("param") String input) {
        String output = "Path not valid!";
        return Response.status(200).entity(output).build();
    }
    
    @GET
    @Path("/system")
    public Response accessSystem() {
        
        return Response.status(200).entity("Bank " + myBanking.accessSystem().getName()
                + " is operational").build();
    }
    
    @GET
    @Path("/database")
    public Response accessDatabase() {
        
        if(myBanking.accessDatabase() == null) {
            return Response.status(200).entity("Cannot connect to database!").build();
        }
        else {
            return Response.status(200).entity("Database is connected!").build();
        }
    }
    
    @POST
    @Path("/user/signup")
    public Response signupPage() {
        return Response.status(200).entity("Register New User").build();
    }
    
    @POST
    @Path("/user/login")
    public Response loginPage() {
        return Response.status(200).entity("Welcome to Login Page").build();
    }
    
    @GET
    @Path("/user")
    public Response displayUser() {
        return Response.status(200).entity("Displaying Accounts").build();
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
