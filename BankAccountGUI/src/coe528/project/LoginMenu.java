/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package coe528.project;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Insets;


import java.io.InputStream;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.File;

import javafx.scene.control.Alert;
/**
 *
 * @author sanjana
 */
public class LoginMenu extends Application {
     /* Overview: The LoginMenu is a mutable GUI compenent that allows users to input their username and password.
    * The crednetials ar then authenticated to ensure that the correct username and the corresponding password is inputted 
    * matching the text file, in terms of the Customer. For the Manager, the defaulted username and password is be autheticated.
    *
    *
    * Abstraction Function:
    * AF(c)= a LoginMenu (c) with a primary Stage 
    *
    *
    * Rep Invariant:
    * Holds true if: the primaryStage is not null
    */ 
    

    private Stage primaryStage;
    
  
    
    @Override
    public void start(Stage primaryStage)throws Exception {
        //REQUIRES: a non null primary stage 
        //EFFECTS: displays the login menu as the primary stage 
     
        showLoginMenu(primaryStage);
        primaryStage.show();
        
    }
    
    public Pane showLoginMenu(Stage primaryStage) throws Exception {
        //REQUIRES: non-null primary stage
        //MODIFIES: this.primaryStage (the current instance of the stage is modified)
        //EFFECTS: returns a Login Menu stackpane that is contained within the scene and set to the stage (containig user input areas)
        
        this.primaryStage=primaryStage;
        
        primaryStage.setTitle("Bank Account");
        
        
        StackPane root=new StackPane();
        Scene scene=new Scene(root, 1100, 750);
        primaryStage.setScene(scene);
        
        //Background Image 
        InputStream picture=Files.newInputStream(Paths.get("src/IMG_3789.png")); //Stream image, exception thorwn 
        Image backgroundImage= new Image(picture);
        ImageView imageView=new ImageView(backgroundImage);
        imageView.setFitWidth(400);
        imageView.setPreserveRatio(true);
       
        
        
        Label username=new Label("Username:");
        username.setFont(new Font("Arial", 16));
        
        TextField usernameField=new TextField();
        usernameField.setPrefHeight(40);
        usernameField.setPrefWidth(1);
       
           
        Label password=new Label("Password:");
        password.setFont(new Font("Arial", 16));
        
        PasswordField passwordField=new PasswordField();
        passwordField.setPrefHeight(40);
        passwordField.setPrefWidth(1);
        
        //Logging in and entering the correct dashboard 
        Button login = new Button();
        login.setText("Log In");
        login.setFont(new Font("Arial", 16));
        
        //UserInputs Layout 
        VBox userInputs=new VBox(20);
        userInputs.setPadding(new Insets(20));
        userInputs.setAlignment(Pos.CENTER);
        
      
  
        VBox.setMargin(username, new Insets(0,0,5,0)); //setting margins for each child around the sides if the rectangle
        VBox.setMargin(username, new Insets(0,0,5,0));
        
    
       
        login.setOnAction(e-> {
            
             String inputUsername=usernameField.getText(); //when login button is pressed, store inputted username and passwords 
             String inputPassword=passwordField.getText();
             String roleManager= "Manager";

             
            //Manager 
            if(inputUsername.equals("admin") && inputPassword.equals("admin") && roleManager.equals("Manager")){  //create manager dashboard if amdin is username and pass 
                ManagerDashboard managerDashboard=new ManagerDashboard(this.primaryStage);
             
                primaryStage.setScene(managerDashboard.getLayout());//return the managerRoot with buttons, functions
                primaryStage.show();
            }
                       
            //Customer 
            else if (authenticateCustomer(inputUsername,inputPassword)){
                
                CustomerDashboard customerDashboard=new CustomerDashboard(this.primaryStage,inputUsername, inputPassword);
                
                primaryStage.setScene(customerDashboard.getLayout());//return the customerRoot with all buttons, functions
                primaryStage.show();
            }
             
             });
        
        //Adding all labels + textfields into VBox (userinputs)
        userInputs.getChildren().addAll(username, usernameField, password, passwordField, login); //add all elements to the screen 
       
        //Border pane to keep image on left, inputs on right 
        BorderPane border=new BorderPane();
        border.setLeft(imageView); //to make the image only appear on the left 
        border.setCenter(userInputs); //ensure the vbox is on the right side 
        
        
        //Stack Pane keeps all togther within border 
        root.getChildren().addAll(border);
        
        return root;
    }

             
  
        
        
        //Customer Authetication Method 
        public boolean authenticateCustomer(String usernameEntered, String passwordEntered) {
            //REQUIRES: a non null and non empty username and password eneterd by the user
            //EFFECTS: returns a boolean (true or false) based on whether teh inputted user name is found and matches the 
            // inputted password 
            
            String role="Customer"; //set role as customer to authenticate role 
            
            String customerFile="src/bankaccountgui/customerInfo/"+usernameEntered+".txt";
            
            File file=new File(customerFile); //creates new file with the file name 
                if (!file.exists()) {
                 showAlert("Customer not found. Please check the entered username.");
                  return false;
                }
                  
            try (BufferedReader reader=new BufferedReader(new FileReader(file))) {
                    String info=reader.readLine(); //reads the line of text, store into info
                    
                    if (info!=null) {
                        String[] parts=info.split(","); //split the line of text after each comma, put into part of an array
                        
                     if (parts.length>1 && passwordEntered.equals(parts[1]) && role.equals(parts[4])) {
                           return true; //passowrd enetered and correct password in file matched 
                            
                        } else {
                            showAlert("Incorrect password.");
                            return false;
                        }
                    }
                } catch (IOException e) {
                    showAlert("Error. Please try again.");
                    e.printStackTrace();
                }

                return false;
            }
        
        
        
                private void showAlert(String message) {
                //REQUIRES: a message that is not empty and not null
                //EFFECTS: displays an error message containg infomartion relevent to the reasoning of error 
                
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText(message);
                alert.showAndWait();
            }

                
    @Override   
    public String toString(){
        //EFFECTS: implements the abstraction function and returns a string of all the elements that make up the LoginMenu Object
        return "Login Menu:" + "Primary Stage:"+ this.primaryStage + "Enter Username" + "Enter Password";
    }
        
    
    
    public boolean repOk(){ 
        //EFFECTS: implements the rep invariant and returns true if rep invariant holds for the LoginMenu object, if not return false 
        
        if (this.primaryStage==null){
            return false;
       
        }
        
        return true;
    } 
                
                
                
                
          
            
             
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    

}


