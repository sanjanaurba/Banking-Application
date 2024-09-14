/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coe528.project;

/**
 *
 * @author sanjana
 */
public class PlatinumLevel extends CustomerLevel {
      /* Overview: PlatinumLevel in an immutable object representng a CustomerLevel state customers membership.
    *  It is a part of the State Design Pattern as it provides speciifcs behviour for purchasing online items its sepcific fees. 
    *
    * Abstraction Function:
    * AF(c)= a PlatinumLevel (c) with a online purchase fee of $0 
    *
    *
    * Rep Invariant:
    * Holds true if: the customer is not null 
    */ 
    
    
    
    
   public PlatinumLevel(Customer customerP){
         super(customerP);
    } 
   
   
    @Override
    public void updateState(){
       //REQUIRES: non null customer 
        //MODIFIES: cutsomers level by changing to a different state depending on the balance 
            double accountBalance=customer.getBalance();
            if (accountBalance<10000) {
                customer.setLevel(new SilverLevel(customer));
            } else if (accountBalance>=10000 && accountBalance<20000) {
                customer.setLevel(new GoldLevel(customer));
            }

        }
   
    
    @Override
    public void onlinePurchase(double price){
        //REQUIRES: non negative price
        //MODIFIES: customers account balance 
        //EFFCTS: sets the customers balance into the new amount after deducting teh price of the items
        customer.setBalance(customer.getBalance()-price); //using customer class setter to set post purchase balance, platLevel online purchase NO fee and item amount subtracted     
    }
    
    
     @Override   
    public String toString(){
        //EFFECTS: implements the abstraction function and returns a string of all the elements that make up the each of the states of teh PlatinumLevel
        return "CustomerLevel: Platinum Purchase Fees: $0";
    }
        
    
    @Override 
    public boolean repOk(){ 
        //EFFECTS: implements the rep invariant and returns true if rep invariant holds for the PlatinumLevel state, if not return false 
        
        if (this.customer==null){
            return false;
       
        }
  
        
        return true;
    } 
    
}