/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coe528.project;

import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.File;
/**
 *
 * @author sanjana
 */
public class SilverLevel extends CustomerLevel {
     /* Overview: SilverLevel in an immutable object reprenting a CustomerLevel state customers membership.
    *  It is a part of the State Design Pattern as it provides speciifcs behviour for purchasing online items its sepcific fees. 
    *
    * Abstraction Function:
    * AF(c)= a SilverLevel (c) with a online purchase fee of $20 that is accounted for when purachsing items
    *
    *
    * Rep Invariant:
    * Holds true if: the customer is not null 
    */ 
    
    public SilverLevel(Customer customerS){
         super(customerS);
    } 
    
    @Override
    public void updateState(){
        //REQUIRES: non null customer 
        //MODIFIES: cutsomers level by changing to a different state depending on the balance 
        
            double accountBalance=customer.getBalance();
            if (accountBalance>=10000 && accountBalance<20000) {
                customer.setLevel(new GoldLevel(customer));
            } else if (accountBalance >= 20000) {
                customer.setLevel(new PlatinumLevel(customer));
            }

        }
    

    @Override
    public void onlinePurchase(double price){
        //REQUIRES: non negative price
        //MODIFIES: customers account balance 
        //EFFCTS: sets the customers balance into the new amount after deducting teh price of the items and the fee
        this.customer.setBalance(customer.getBalance()-price-20); //using customer class setter to set post purchase balance, silverLevel online purchase fee and amount subtracted     
    }
    
    
    @Override   
    public String toString(){
        //EFFECTS: implements the abstraction function and returns a string of all the elements that make up the each of the states of teh SilverLevel
        return "CustomerLevel: Silver Purchase Fees: $20";
    }
        
    
    @Override 
    public boolean repOk(){ 
        //EFFECTS: implements the rep invariant and returns true if rep invariant holds for the SilverLevel state, if not return false 
        
        if (this.customer==null){
            return false;
       
        }
  
        
        return true;
    } 
    
    
    
}
