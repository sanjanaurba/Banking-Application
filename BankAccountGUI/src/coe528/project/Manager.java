/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coe528.project;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author sanjana
 */
public class Manager {
     /* Overview: Managers are immutable bank account users with a one defined username, password and role and a directory path
    * that allows them to locate their customers files. Managers are able to add customers, remove customers and logout.
    *
    *
    * Abstraction Function:
    * AF(c)= a manager (c) with a username, password, account, role and level
    *
    *
    * Rep Invariant:
    * Holds true if: the directory path is not empty not null
    */ 
    private static final String username="admin";
    private static final String password="admin";
    private static final String role="Manager";        //all instances of Manager will have the same role, user and pass (static) and cant be changed so (final)
    
    private static final String directoryPath = "src/bankaccountgui/customerInfo/"; //set a directory path to later search through customers 
    
   
    
    public void addCustomer(String usernameEntered, String passwordEntered){ 
        //REQUIRES: a non empty and non null username and password entered 
        //EFFECTS: creates a new file for the customer with a the inputted username and pass and defult balance, level and role 
        
        
        //Create patch for file 
        Path filePath=Paths.get(directoryPath, usernameEntered + ".txt"); //create a path for the new file
        
        //File contents     
        String content= usernameEntered + ","+ passwordEntered + ",100.0,silver,Customer"; //text file contents, deafult 100 balance silver level and role
           
        //Writing conetnts onto file 
        try (BufferedWriter writer=new BufferedWriter(new FileWriter(filePath.toFile()))) {
            writer.write(content); //create a new txt file write out the contents 
            } catch (IOException e) {
                e.printStackTrace();
        }   
    }
    
    
    
    public boolean customerExists(String usernameEntered){
        //REQUIRES: A non null and empty username enetered by manager 
        //EFFECTS: returns a boolean; true if the file already exists in the same directory with the same username, false if not
        
         Path filePath= Paths.get(directoryPath, usernameEntered + ".txt"); //Since each customer can only have one bank account and no two customers can have same username, check if file with same direcory and name alr exist
         return Files.exists(filePath); //returns true sine the username enetered akready has a file exisiing in the directory
    }
    
    
    
    public void deleteCutsomer(String usernameEntered){ 
        //REQUIRES:  A non null and empty username enetered by manager 
        //EFFCTS: deletes the file with corresponding username 
 
        Path filePath = Paths.get(directoryPath, usernameEntered+".txt");
        try  {
            Files.delete(filePath); //create a new txt file write out the contents 
            } catch (IOException e) {
               e.printStackTrace();
        } 
    }
    
        
    @Override   
    public String toString(){
        //EFFECTS: implements the abstraction function and returns a string of all the elements that make up the Manager Object
        return "Manager" + username + password + role + "Locate Cutsomer File:" + directoryPath; 
    }
        
    
    
    public boolean repOk(){ 
        //EFFECTS: implemants the rep invariant and returns true if rep invariant holds for the Manager object, if not return false 
  
        if (directoryPath.isEmpty() || directoryPath==null){
            return false;
        }
        return true;
    }
    
    
    
    }
