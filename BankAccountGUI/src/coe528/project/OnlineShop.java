/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coe528.project;

import java.io.InputStream;
import java.io.IOException;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;

import javafx.scene.image.Image;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BackgroundPosition;





/**
 *
 * @author sanjana
 */
public class OnlineShop {

        /* Overview: OnlineShop is a mutable GUI component which establishes an online shopping screen for the Customer.
        * The Online shop allows Customers to have an interactive experience by clicking on items to add to their cart 
        * while showing the total cost of the items before purchasing it.
        *
        * Abstraction Function:
        * AF(c)= an Online Shop with a totalCost value (with a label containing the total cost) and a balance value 
        *
        *
        *
        * Rep Invariant:
        * Holds true if the the totalCost is not negative, the total Cost label is not null 
        */ 
        private double totalCost;
        private Label totalCostLabel;
        private double newBalance;

        
        
    public Pane getLayout(Customer customer) {
        //REQUIRES: a customer that is not null
        //EFFECTS: returns a new pane that is within the window (on the right side) contained in the same scene as the other customer dashboard windows
        
        totalCost=0;
        newBalance=customer.getBalance();
        
        //Components
        StackPane purchaseWindow=new StackPane(); //a new stack pane initialozed to create shopping window 
        purchaseWindow.setPadding(new Insets(10)); //spacing 

        totalCostLabel=new Label("$" + totalCost); //total cost label 
        totalCostLabel.setFont(new Font("Arial", 13));
        totalCostLabel.setTranslateX(250);
        totalCostLabel.setTranslateY(68);

        HBox buttonsBox=new HBox(75);
        buttonsBox.setTranslateX(141);
        buttonsBox.setTranslateY(550);

        //Buttons (initizlied in the createButton method)
        Button shoesPurchase=createButton("Shoes", 85);
        Button clothesPurchase=createButton("Clothes", 20);
        Button accessoriesPurchase=createButton("Accessories", 45);
        Button productsPurchase=createButton("Products", 35);

        //Vbox to adjust the button in columns 
        VBox firstColumn=new VBox(150, shoesPurchase, productsPurchase);
        VBox secondColumn=new VBox(150, clothesPurchase,accessoriesPurchase );

        //Hbox to keep column together of the buttons 
        buttonsBox.getChildren().addAll(firstColumn, secondColumn);

        Button submitPurchase = new Button("Submit Purchase");
        submitPurchase.setFont(new Font("Arial", 16));
        submitPurchase.setOnAction(event -> submitPurchaseHandler(customer)); //call the submit handler to check for purchase validity 
        submitPurchase.setTranslateX(260);
        submitPurchase.setTranslateY(160);
        purchaseWindow.getChildren().addAll(totalCostLabel, buttonsBox, submitPurchase);

        try {
            InputStream shop=Files.newInputStream(Paths.get("src/shop.png"));
            Image shopImage=new Image(shop);
            BackgroundImage backgroundImage=new BackgroundImage(shopImage,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
            new BackgroundSize(100, 100, true, true, true, false)); //making a new backgorund out of an imaging setting repeat and dimensions 
            Background background=new Background(backgroundImage);
            purchaseWindow.setBackground(background); //make the purachse window 
        } catch (IOException e){
            e.printStackTrace();
        }

        return purchaseWindow; //return the stackPane containing all elements (backround
    }

    
    
    private Button createButton(String name, double price) { //private method since no other class should be able to acess (method is only for Online shop)
        //REQUIRES: non null and non empty string and non negative price 
        //MODIFIES: button and total cost label and actual price
        //EFFECTS: returns the corresponding button with updating total costs 
        
        Button button=new Button("Add " + name + " to Cart"); //all 4 times create button called ie for shoesPurchase, so differnt event hanlders, but seem like one 
        button.setFont(new Font("Times New Roman", 10));
        button.setOnAction(event -> { //when coresponding button pressed incremnet total cost and label by item amount 
            totalCost+=price;
            totalCostLabel.setText("$" + totalCost);
        });
        return button;
    }

    
    
    
    private double checkFee(){
        //EFFECTS: returns a double based on balance in order to detremine whether product can be purchased or not 
        
        if(newBalance<10000){ //checks account balance 
            return 20;
        }
        
        else if(newBalance>=10000 && newBalance<20000){
            return 10;
            
        }
        else {
        return 0; //gretater than 20000 return 0 since no purchase fee for platinum
        }
    }
      
    
    private void submitPurchaseHandler(Customer customer) {
        //REQUIRES: customer is not null and totalCost>=0
        //MODIFIES: customers balance, level and file  
        //EFFECTS: upadtes customers balance when a valid purchase is made and upadtes the text file 
        
        
             if (totalCost<50) {
                 showAlert("Purchase Must Be $50 or More");
                
                  }
             else {
               if (totalCost>=50 && totalCost+checkFee()>customer.getBalance()){ //cost of items AND fee is greater than the balance
                   showAlert("Insufficient Balance. Purchase Fee included.");
           
              } 
         
               if (totalCost>=50 && customer.getBalance()>=totalCost+checkFee()){
     
                    customer.doPurchase(totalCost); //checks customer level using internal state process and does purchase accordlingly
                    customer.updateFile();  //no file update unless valid amount 
                    showAlert("Update", "Items Purchased. Thank you for Shopping!");
             
            }
        }
    }
    
 
    //overload method 
    private void showAlert(String message) {
         //REQUIRES: a message that is not empty and not null
         //EFFECTS: displays an error message containg infomartion relevent to the reasoning of error 
        
        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error.");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showAlert(String title, String message) {
        //REQUIRES: a message and title that is not empty and not null
        //EFFECTS: displays an info message containing update infomartion 
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
     @Override   
    public String toString(){
        //EFFECTS: implements the abstraction function and returns a string of all the elements that make up the OnlineShop Object
        return "Online Shop" + "Total Cost is Items:" + totalCost + newBalance;
    }
        
    
    
    public boolean repOk(){ 
        //EFFECTS: implemants the rep invariant and returns true if rep invariant holds for the OnlineShop object, if not return false 
        
        if (totalCost<0){
            return false;
        }
        
        else if (totalCostLabel==null){
            return false;
        }
        
  
        
        return true;
    }
    
    
    
    
}