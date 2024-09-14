/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coe528.project;

/**
 *
 * @author sanjana
 */
public abstract class CustomerLevel {
     /* Overview: CustomerLevel are immutable objects that represent differnt states in relation to the Cutsomers membership level.  
    * The CustomerLevel applies teh State design patternand allows the differnt states to have their own
    * implementaion of the online purchase function according to their levels purchase fees. The states 
    *
    * Abstraction Function:
    * AF(c)= a CustomerLevel (c) in which the state is determined by the concrete sublasses as well as the behaviour since each has their repscetive 
    * fees that affect the online purchase.
    *
    * Rep Invariant:
    * Holds true if: the customer is not null
    */ 
    
    
    
    protected Customer customer; //acessible by classes within package and customerLevels subclasses, customer level depends on the customer 
    
    public CustomerLevel(Customer customer){
        //REUIRES: a non null customer 
        //MODIFIES: this (current instance of the customer)
        //EFFECTS: sets the current customer to the customer passed as an argument 
        
        this.customer=customer; //ensure current instance of the customer set to customer (object being created to parameter passed in different class)
    }
    
    public abstract void onlinePurchase(double price);

    public abstract void updateState();
    
   @Override   
    public abstract String toString();
        
    
    
    public abstract boolean repOk();
  
}
