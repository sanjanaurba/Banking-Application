/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coe528.project;


import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;


/**
 *
 * @author sanjana
 * 
 */
public class Customer {
    /* Overview: Customers are mutable bank account users with a username, password,  
    * account balance and a corresponding level to define its state (membership). They are able to check their account info 
    * including their balance and level, deposit money, withdraw money and purchase items from an online shop.
    *
    *
    * Abstraction Function:
    * AF(c)= a customer (c) with a username, password, account, role and level
    *
    *
    * Rep Invariant:
    * Holds true if: the username and password is not null or empty, the account balance is not negative,
    * the level representation is either "silver", "gold" or platinum" and the level itseld is not null 
    */ 
    
    private String username;
    private String password;
    private double account; 
    private static final String role="Customer";
    private String levelRepresentation;
    private CustomerLevel level;
      
   
    public Customer(String username, String password, double account, String levelRepresentation) {
        //EFFECTS: creates new customer object with username, password, account, levelString and initlizes the customer level state based on balance
       
       this.username=username; //setting username attained through file when customer autheticated in LoginMenu
       this.password=password;
       this.account=account;
       this.levelRepresentation=levelRepresentation;
       
       //inializing customer level depending on account balance
       if(this.account<10000){ //checks account balance 
        level=new SilverLevel(this); //updating current instance of the level to silver (replaces old level with silver level, but keeps THIS(same) instnace of customer)
        }
        
        else if(this.account>=10000 && this.account<20000){
        level=new GoldLevel(this); //where this is the current instance of the customer 
            
        }
        
        else if (this.account>=20000){
        level=new PlatinumLevel(this);
        }
        
    }
    

    public void depositMoney(double amount){
        //REQUIRES: amount>=0 
        //MODIFIES: this.account (customers current account balance)
        //EFFECTS: increases the account balaance by the deposited amount 
        
             this.account+=amount;  
        }
    
    
    
    public void withdrawMoney(double amount){
        //REQUIRES: amount>=0 AND amount<=account (withdraw amount cant be greater than account balance)
        //MODIFIES: this.account (customers current account balance)
        //EFFECTS: decreases the account balance by teh withdrawn amount 
            this.account-=amount;
        
    }
    
    
    //For Online Purchases
    public void doPurchase(double amount){  
        //REQUIRES: amount>=0 AND amount<=account (purchase amount cant be graeter than account balance) and level is not null
        //MODIFIES: level 
        //EFFECTS: updates the level and makes the purchase (with correct fee) based on that level
        level.updateState(); //call methods of sublasses to handle state transitions
        level.onlinePurchase(amount);   //call method of sublasses to handle online purachsing 
    }
    
    
    public void setLevel(CustomerLevel newLevel) {
        //REQUIRES: a non null newLevel 
        //MODIFIES: level
        //EFFECTS: updates the level into the new level
        level=newLevel;
    }
 
    
    public void updateFile() { 
        //EFFECTS: updates the customers file with the new balance/level string representation  
        
    String customerFile = "src/bankaccountgui/customerInfo/" + this.username + ".txt"; //locate and set the customer file name 

    try (BufferedReader reader=new BufferedReader(new FileReader(customerFile))) { //Buffered reads process file, filereader provide acess to file 
        String info=reader.readLine(); //set first file line as cutsomer info

        if (info!=null) { 
            String[] parts=info.split(","); //create array of strings, split each index by comma (so each index contains a string for username, pass, account balance and level)
            
            //Balance
            parts[2]=String.format("%.2f", this.account); //update the account balance in the file which is in the second index (located after the second comma in txt file), convert to string, to 2 decmal places

            //Level
            if (this.account<10000) {
                parts[3]="silver"; //updating string representation of the level
            } else if (this.account>=10000 && this.account<20000) {
                parts[3]="gold";
            } else if (this.account>=20000) {
                parts[3]="platinum";
            }

            try (BufferedWriter writer=new BufferedWriter(new FileWriter(customerFile, false))) { //write onto the customer existing file, DONT just append (doesnt simply update the parts) rather re-write
                writer.write(String.join(",", parts)); //join array parts[] (with info including the updated balance and updated string) and back to a line with commas and write it to the file
                } 
        }
           } catch (IOException e) { //if error reading/writing file 
             e.printStackTrace(); //smooth handle print out errors without ending the program allow to see where error occured debug 
    }
}

    
   
    
     public double getBalance(){
         //EFFECTS: returns the balance as read from the customer text file 
            String customerFile = "src/bankaccountgui/customerInfo/" + this.username + ".txt";   // since final is always up to date, reteive teh balance from the file ensure current instance is getting shown (since account is always getting updated (withdraw, deposit, purchase)
            
            try (BufferedReader reader=new BufferedReader(new FileReader(customerFile))){
                String info=reader.readLine();

          if (info != null) { //if info in file is not null
            String[] parts = info.split(","); //create array of strings, split each index by comma (so each index has a string for username, pass, etc.)
                
            return Double.parseDouble(parts[2]);  //convert string in file into a double, return that as new balance 
          }         
           
            } catch (IOException e) {
               e.printStackTrace();  
    }
            return 0.0;
}
     
     
    
    public void setBalance(double postPurchase){
        //REQUIRES: a postPurchase value that is not negative
        //MODIFIES: this (current account balance)
        //EFFECTS: sets the balance to the post purachse value upon purching an item from teh shop
        this.account=postPurchase; 
    }
  
    
    
    public String getUsername(){
        //EFFECTS: returns Customers username 
        
        return username;
    }
    
    
    
    public String getLevelString(){
        //EFFECTS: returns the string reprentation of the level as read in teh customers file, in order to display it 
        
        String customerFile = "src/bankaccountgui/customerInfo/" + this.username + ".txt";   //since file is always up to date, reteive teh balance from the file ensure current instance is getting shown (since account is always getting updated (withdraw, deposit, purchase)
            
            try (BufferedReader reader=new BufferedReader(new FileReader(customerFile))){
                String info=reader.readLine();

            if (info != null) { //if info in file is not null
            String[] parts=info.split(","); //create array of strings, split each index by comma (so each index has a string for username, pass, etc.)
            
            return parts[3]; //return string in third index which is the level in string representation
          }         
           
            } catch (IOException e) {
               e.printStackTrace();  
            }
            return null;
        }

      
    
    @Override   
    public String toString(){
        //EFFECTS: implements the abstraction function and returns a string of all the elements that make up the Customer Object
        return "Customer:" + username + ',' + password + ','+ role + ','+ account + levelRepresentation;
    }
        
    
    
    public boolean repOk(){ 
        //EFFECTS: implemants the rep invariant and returns true if rep invariant holds for the Customer object, if not return false 
        
        if (this.account<0){
            return false;
        }
        
        else if (this.username==null || this.username.isEmpty()){
            return false;
        }
        
        else if (this.password==null || this.password.isEmpty()) {
            return false;
        }
        
        else if (this.account<0){
            return false;
        }
        
        else if (this.levelRepresentation.equals("silver")||this.levelRepresentation.equals("gold")|| this.levelRepresentation.equals("platinum")){
            return false;
        }
        
        else if (this.level==null){
            return false;
        }
        
        
        return true;
    }
    
    
}
