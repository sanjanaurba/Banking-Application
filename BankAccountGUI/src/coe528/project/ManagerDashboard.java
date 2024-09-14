/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coe528.project;


import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Insets;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.io.IOException;
import javafx.scene.control.Alert;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;


/**
 *
 * @author sanjana
 */
public class ManagerDashboard {
     /* Overview: ManagerDashboard is a mutable user GUI component which establishes a Manager screen upon logging in.  
    * The dashboard consists of many interactive componenets inlcuing buttons and input text fields to allow Managers to 
    * add new customers by entering a new username and a password as well as deleting cutsomers by entering their username.
    * Managers can also logout using the logout button.
    *
    *
    * Abstraction Function:
    * AF(c)= A  ManagerDashboard (c) with a manager and
    * a primary stage that holds the all the contents in the GUI
    *
    *
    *
    *
    *
    * Rep Invariant:
    * Holds true if the primary stage is not null
    */
    
    
    private Manager manager;
    private Stage primaryStage;
    
     public ManagerDashboard(Stage primaryStage){
         //REQUIRES: a non null primary stage 
         //MODIFIES: this (current instance if teh primary stage)
         //EFFECTS: creates a new manager instance and sets the primary stage 
         manager=new Manager();
         this.primaryStage=primaryStage;
     }
           
    
    
    
     public Scene getLayout() {
         //EFFECTS: Initializes and returns the main GUI compenents in the customer dashboard (inlcuidng buttons and input feilds)
         
        
        //FORMATTING- made out of main stackpane and border pane to seperate the sides
        StackPane managerRoot=new StackPane(); 
        BorderPane border=new BorderPane();
        
        //Stream Backround to StackPane 
        try { InputStream picture=Files.newInputStream(Paths.get("src/manager.png")); //Stream image, exception thorwn 
        Image backgroundImage= new Image(picture);
        ImageView imageView=new ImageView(backgroundImage);
        imageView.setFitWidth(1450);
        imageView.setFitWidth(1100);
        imageView.setPreserveRatio(true);
        
            
            //Buttons (manager options on the left side)
            Button home=new Button();
            home.setText("Home"); 
            home.setFont(new Font("Arial", 16));
            home.setStyle("-fx-background-color:transparent;");
            home.setTextFill(Color.WHITE);
            
            Button addCustomer=new Button();
            addCustomer.setText("Add Customer"); 
            addCustomer.setFont(new Font("Arial", 16));
            addCustomer.setStyle("-fx-background-color:transparent;");
            addCustomer.setTextFill(Color.WHITE);


            Button deleteCustomer=new Button();
            deleteCustomer.setText("Delete Customer"); 
            deleteCustomer.setFont(new Font("Arial", 16));
            deleteCustomer.setStyle("-fx-background-color:transparent;");
            deleteCustomer.setTextFill(Color.WHITE);
             
            //Logout Button (outside of Vbox)
            Button logOut=new Button();
            logOut.setText("Log Out"); 
            logOut.setFont(new Font("Arial", 10));
            logOut.setTranslateX(340);
            logOut.setTranslateY(-360);
            
            
            VBox managerOptions=new VBox(70);
            managerOptions.setPadding(new Insets(50));
            managerOptions.setAlignment(Pos.CENTER);
        
        
        //Button Functions 
        
        //HOME PAGE 
        home.setOnAction(e-> {
        border.setCenter(null); //remove any backgorund in the window  
         });
        
        
        //ADD CUSTOMER FUNCTION
        addCustomer.setOnAction(e-> {
            
            StackPane addWindow=new StackPane();
            addWindow.setTranslateX(20);
            
            BackgroundFill backgroundFill = new BackgroundFill(Color.WHITE, null, null); 
            Background background = new Background(backgroundFill);

            addWindow.setBackground(background);
            
            VBox layout = new VBox(20);
            layout.setAlignment(Pos.CENTER);
                
            //Contents 
            Label addLabel=new Label("Enter Customer Username: "); //+ customer.getUsername() when figure out whats wrong
            addLabel.setFont(new Font("Arial", 16));

            TextField newCustomerField=new TextField();
            newCustomerField.setPrefHeight(40);
            newCustomerField.setPrefWidth(0.5);
            
            Label deleteCustomerLabel=new Label("Enter Customer Password: "); //+ customer.getUsername() when figure out whats wrong
            deleteCustomerLabel.setFont(new Font("Arial", 16));
            
            TextField newPasswordField=new TextField();
            newPasswordField.setPrefHeight(40);
            newPasswordField.setPrefWidth(0.5);
            
            Button confirmCustomer=new Button();
            confirmCustomer.setText("Confirm New Customer"); 
            confirmCustomer.setFont(new Font("Arial", 16));

            //Confirm add customer function
            confirmCustomer.setOnAction(g -> {
                String username=newCustomerField.getText().trim().replaceAll("\\s+", ""); //get rid of ALL spaces from input to ensure that the username stored is just the word no spaces 
                String password=newPasswordField.getText().trim().replaceAll("\\s+", ""); 

                if (username.isEmpty() || password.isEmpty()) {
                    showAlert("Error.", "Please enter both username and password.");
                    return;
                }

                if (username.equalsIgnoreCase("admin")) { //username should not be admin since resersved for manager
                    showAlert("Invalid Username", "Manager Username. Please Enter A Different Username.");
                    return;
                }

                if (manager.customerExists(username)) { //check if customer already exists
                    showAlert("Error.", "Customer Already Exists. Please Try Again.");
                    return;
                }
                
                manager.addCustomer(username, password); //only if above conditions dont occur customer is added
                showAlert("Success", "Customer Successfully Added.");
            });
            
             //FORMATTING LAYOUT (add label, textfield and button to window)
            layout.getChildren().addAll(addLabel, newCustomerField, deleteCustomerLabel,newPasswordField, confirmCustomer);
            layout.setPadding(new Insets(30));
            addWindow.getChildren().add(layout);
            border.setCenter(addWindow); // Set to border's center
            border.setPadding(new Insets(32, 10, 0, 32)); //entireright side spacing
            });
            
            
        
        
        
        //DELETE CUSTOMER FUNCTION
        deleteCustomer.setOnAction(e-> {
            
            StackPane deleteWindow=new StackPane(); //new stack pane for delete customer window 
            deleteWindow.setTranslateX(20);
            
            BackgroundFill backgroundFill=new BackgroundFill(Color.WHITE, null, null); 
            Background background = new Background(backgroundFill);

            deleteWindow.setBackground(background);
            
            VBox layout = new VBox(20);
            layout.setAlignment(Pos.CENTER);
                
            //Contents 
            Label deleteLabel=new Label("Enter Customer Username: "); //+ customer.getUsername() when figure out whats wrong
            deleteLabel.setFont(new Font("Arial", 16));

            TextField deleteField=new TextField();
            deleteField.setPrefHeight(40);
            deleteField.setPrefWidth(0.5);
            
            
            Button confirmCustomer=new Button();
            confirmCustomer.setText("Confirm Delete"); 
            confirmCustomer.setFont(new Font("Arial", 16));

            //Confirm customer Function
            confirmCustomer.setOnAction(g -> {
                    
                    if(!manager.customerExists(deleteField.getText())){ //if customer with usernam einputted does not exist cant delete 
                        showAlert("Customer Not Found. Please Try Again.");
                    }
                 else {
                        manager.deleteCutsomer(deleteField.getText());
                        showAlert("Update", "Customer Successfully Deleted"); 
                }
            
            });
            
            //FORMATTING LAYOUT (add label, textfield and button to window)
            layout.getChildren().addAll(deleteLabel, deleteField, confirmCustomer);
            layout.setPadding(new Insets(30));
            deleteWindow.getChildren().add(layout);
            border.setCenter(deleteWindow); // Set to border's center
            border.setPadding(new Insets(32, 10, 0, 32)); //entireright side spacing
            });
        
           //Log Out 
            logOut.setOnAction(e -> {
              try {     
                LoginMenu loggingOut=new LoginMenu(); //craetes new login menu object
                loggingOut.showLoginMenu(primaryStage);
               
                    } catch (Exception ex) {
                ex.printStackTrace(); 
                     }
                 });
                
        
                
        //LAYOUT FORMATTING 
    
        //Adding all labels + textfields into VBox (customerOptions)
        managerOptions.getChildren().addAll(home,addCustomer, deleteCustomer); //add all elements to the screen 
       
        //Border pane to keep options on left, output screen (stack on left)
         border.setLeft(managerOptions); //ensure the vbox is on the left side (the buttons all on the left)
      
 
        //Stack Pane keeps all togther within border 
        managerRoot.getChildren().addAll(imageView, border, logOut); //add the inner stacks for each new window after boredr
        
            } catch (IOException e){
                e.printStackTrace();
            }
       
    
            return new Scene(managerRoot, 1100, 750);  //returns new scene with stackpane and dimensions
        }
        
        
        //overload method to handle error messages and sucess updates
     private void showAlert(String message) {
             //REQUIRES: a message that is not empty and not null
            //EFFECTS: displays an error message containg infomartion relevent to the reasoning of error 
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error.");
                alert.setHeaderText(null);
                alert.setContentText(message);
                alert.showAndWait();
            }
     private void showAlert(String title, String message) {
              //REQUIRES: a message and title that is not empty and not null
              //EFFECTS: displays an info message containing update infomartion 
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(title);
                alert.setHeaderText(null);
                alert.setContentText(message);
                alert.showAndWait();
            }
     
     
     
      @Override   
    public String toString(){
        //EFFECTS: implements the abstraction function and returns a string of all the elements that make up the ManagerDashboard Object
        return "Manager Dashboard:" + manager + "Primary Stage:" +this.primaryStage;
    }
     
    
     public boolean repOk(){ 
        //EFFECTS: implements the rep invariant and returns true if rep invariant holds for the ManagerDashboard object, if not return false 
        
        if (this.primaryStage==null){
            return false;
        }
        
        
        return true;
    }
        
    
     
        
     }
 