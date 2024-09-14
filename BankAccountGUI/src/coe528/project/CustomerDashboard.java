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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import java.io.InputStream;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.scene.control.Alert;

/**
 *
 * @author sanjana
 */
public class CustomerDashboard { 
    /* Overview: CustomerDashBoard is a mutable user GUI component which establishes a Customer screen upon logging in.  
    * The dashboard consists of many interactive componenets including buttons and input text fields to allow Customers to 
    * check their balance and level, input deposit amount, withdrawal amounts, and purchase items from an online shop. The customer can 
    * also log out. For each interaction the cutsomer rec
    *
    *
    * Abstraction Function:
    * AF(c)= A customer dashboard (c) with a customer, account balance, and the correpsonding level of the customer and
    * a primary stage that holds the all the contents in the GUI
    *
    *
    *
    *
    *
    * Rep Invariant:
    * Holds true if the primary stage is not null, account balance is not negative and the level reprsentative is 
    * either silver gold or platinum
    */
    
    
    private Customer customer;
    private double accountBalance;
    private String levelRep;
    private Stage primaryStage;
    
  
    
    public CustomerDashboard(Stage primaryStage, String usernameEntered, String passwordEntered){
        //EFFECTS: creates a new Customer Dashboard object by initlaizing a new Cutsomer object 
        //using customer info inputted through login and matching account balance and level as found in the file
      
        
        this.primaryStage=primaryStage; //setting stage 
        
         String customerFile="src/bankaccountgui/customerInfo/"+usernameEntered+".txt"; //read file to inialize corresponding customer when dashboard created 
                  
            try (BufferedReader reader=new BufferedReader(new FileReader(customerFile))) {
                    String info=reader.readLine(); //reads the line of text, store into info
                    
                    if (info!=null) {
                        String[] parts=info.split(","); //split the line of text after each comma, put into part of an array
                        
                     if (parts.length>1 && passwordEntered.equals(parts[1])) { //if length is greater than one of string array
                           accountBalance=Double.parseDouble(parts[2]); //convert last part of the string to an double and set as account balance value
                           levelRep=parts[3]; //set level 
                           
                            customer=new Customer(usernameEntered, passwordEntered,accountBalance, levelRep); //when logged in as customer and customer initlaized using file info customer object created, used to call methods later
                            
                        }
                       }
                } catch (IOException e) {
                    e.printStackTrace();
                }
    }
        
        
    
    public Scene getLayout() {
        //EFFECTS: Initializes and returns the main GUI compenents in the customer dashboard (inlcuidng buttons and input feilds)
        
        
        //FORMATTING- made out of main stackpane and border pane to seperate the sides
        StackPane customerRoot=new StackPane(); 
        BorderPane border=new BorderPane();
        
        //Stream Backround to entire backgorund
        try { InputStream picture=Files.newInputStream(Paths.get("src/customer.png")); //create new input stream picture by getting path and using path to create another input stream of the file, to get data of the image
        Image backgroundImage= new Image(picture); //image data into an actual image 
        ImageView imageView=new ImageView(backgroundImage);
        imageView.setFitWidth(1450);
        imageView.setFitWidth(1100);
        imageView.setPreserveRatio(true);
        
            
            //Welcome, <username>! text
            Label welcome=new Label("Welcome, " + customer.getUsername() +"!"); 
            welcome.setFont(new Font("Times New Roman", 25));
            welcome.setTranslateX(15);
            welcome.setTranslateY(-255);
            
            //Status text (silver, gold, platinum)
            Label status=new Label(customer.getLevelString().toUpperCase()); 
            status.setFont(new Font("Times New Roman", 20));
            status.setTranslateX(160);
            status.setTranslateY(270);
            
            //Balance text
            Label balance=new Label(String.valueOf("$" + customer.getBalance())); 
            balance.setFont(new Font("Times New Roman", 50));
            balance.setTextFill(Color.GOLD);
            balance.setTranslateX(145);
            balance.setTranslateY(-70);
            
            //Buttons (customer options on the left side)
            Button accountInfo=new Button();
            accountInfo.setText("Account Info"); 
            accountInfo.setFont(new Font("Arial", 16));
            accountInfo.setStyle("-fx-background-color:transparent;");
            accountInfo.setTextFill(Color.WHITE);

            Button withdraw=new Button();
            withdraw.setText("Withdraw Money"); 
            withdraw.setFont(new Font("Arial", 16));
            withdraw.setStyle("-fx-background-color:transparent;");
            withdraw.setTextFill(Color.WHITE);
            
            Button deposit=new Button();
            deposit.setText("Deposit Money"); 
            deposit.setFont(new Font("Arial", 16));
            deposit.setStyle("-fx-background-color:transparent;");
            deposit.setTextFill(Color.WHITE);

            Button onlinePurchase=new Button();
            onlinePurchase.setText("Online Purchase"); 
            onlinePurchase.setFont(new Font("Arial", 16));
            onlinePurchase.setStyle("-fx-background-color:transparent;");
            onlinePurchase.setTextFill(Color.WHITE);
            
            
            //Logout Button (outside of Vbox)
            Button logOut=new Button();
            logOut.setText("Log Out"); 
            logOut.setFont(new Font("Arial", 10));
            logOut.setTranslateX(340);
            logOut.setTranslateY(-360);
    
        //Spacing for customer options buttons
        VBox customerOptions=new VBox(70);
        customerOptions.setPadding(new Insets(50));
        customerOptions.setAlignment(Pos.CENTER);
        
        
        //Button Functions 
    
        //ACCOUNT INFO FUNCTION- just returns to main page
        accountInfo.setOnAction(e-> {
        balance.setText(String.format("$%.2f", customer.getBalance())); //set Balance label text to updated balance as read from the customer file, to 2 deceimal places
        status.setText(customer.getLevelString().toUpperCase());
        
        border.setCenter(null); //removes previous backround to return to main page (without a stackpane window)
        
         });
        
 
        //WITHDRAW FUNCTION 
        withdraw.setOnAction(e-> {
               
            StackPane withdrawWindow=new StackPane(); //create a new stackpane to hold all of the  
            withdrawWindow.setTranslateX(20);
            
            BackgroundFill backgroundFill=new BackgroundFill(Color.WHITE, null, null); //make withdraw window backgorund white
            Background background=new Background(backgroundFill);

            withdrawWindow.setBackground(background);
            
            VBox layout=new VBox(20);
            layout.setAlignment(Pos.CENTER);
                
            //Contents 
            Label withdrawLabel=new Label("Enter Withdraw Amount: "); 
            withdrawLabel.setFont(new Font("Arial", 16));

            TextField withdrawField=new TextField();
            withdrawField.setPrefHeight(40);
            withdrawField.setPrefWidth(0.5);
            
            Button submitWithdraw=new Button();
            submitWithdraw.setText("Withdraw Money Now"); 
            submitWithdraw.setFont(new Font("Arial", 16));
 
              //Submit withdraw function
              submitWithdraw.setOnAction(g -> {
                try {
                    double withdrawValue = Double.parseDouble(withdrawField.getText()); //convert inputted string to double
                    if (withdrawValue<0) {
                        showAlert("Please Enter Valid Withdraw Amount");
                    } 
                    else if (withdrawValue>customer.getBalance()) {
                        showAlert("Insufficient Balance.");
                    } else {
                       customer.withdrawMoney(withdrawValue);
                       customer.updateFile(); //called to re-write balance and level in the customers file
                        
                        showAlert("Update","Withdraw Sucessful"); 
                    }
                } catch (NumberFormatException ex) {
                    showAlert("Please Enter Valid Withdraw Amount"); //if letters/characcters entered
                }
            });
              
            //FORMATTING LAYOUT (adds label, textfield and button to window)
            layout.getChildren().addAll(withdrawLabel, withdrawField, submitWithdraw); //puts everything into the layout vbox
            layout.setPadding(new Insets(30));
            withdrawWindow.getChildren().add(layout); //adding vbox into window 
            border.setCenter(withdrawWindow);  //adding window into border pane so right side view
            border.setPadding(new Insets(32, 10, 0, 32)); //entire right side spacing
            });
        
         
        
        
        //DEPOSIT FUNCTION 
        deposit.setOnAction(e-> {
            
            StackPane depositWindow=new StackPane(); //creates new stackPane as the right side window 
            depositWindow.setTranslateX(20);
          
            
            BackgroundFill backgroundFill=new BackgroundFill(Color.WHITE, null, null); 
            Background background=new Background(backgroundFill);

            depositWindow.setBackground(background);
            
            VBox layout=new VBox(20);
            layout.setAlignment(Pos.CENTER);
                
            //Contents 
            Label depositLabel=new Label("Enter Deposit Amount: "); 
            depositLabel.setFont(new Font("Arial", 16));

            TextField depositField=new TextField();
            depositField.setPrefHeight(40);
            depositField.setPrefWidth(0.5);
            
            Button submitDeposit=new Button();
            submitDeposit.setText("Deposit Money Now"); 
            submitDeposit.setFont(new Font("Arial", 16));

              submitDeposit.setOnAction(g -> {
                try {
                    double depositValue = Double.parseDouble(depositField.getText());
                    if (depositValue < 0) {
                        showAlert("Please Enter Valid Deposit Amount");
                    } else {
                        this.customer.depositMoney(depositValue);
                        this.customer.updateFile(); //called to re-write balance and level in the customers file
                        showAlert("Update","Deposit Sucessful"); 
                    }
                } catch (NumberFormatException ex) {
                    showAlert("Please Enter Valid Deposit Amount");
                }
            });
              
            //FORMATTING LAYOUT 
            layout.getChildren().addAll(depositLabel, depositField, submitDeposit);
            layout.setPadding(new Insets(30)); //space between fields in vbox
            
            depositWindow.getChildren().add(layout);
            border.setCenter(depositWindow); //window only in right side 
            border.setPadding(new Insets(32, 10, 0, 32)); //entire right side spacing
            });
        
        

           //PURCHASE ONLINE FUNCTION
            onlinePurchase.setOnAction(e-> {
  
            OnlineShop shoppingPage=new OnlineShop(); //creates a new OnlineShop object
            border.setCenter(shoppingPage.getLayout(customer)); //setting the border pane right side to purchase window since shoppingPage.getLayout(customer) returns purchase window
            border.setPadding(new Insets(-84, -10, -136, 47)); //spacing 
         
            });
        
            
            //LOG OUT FUNCTION
            logOut.setOnAction(e -> {
              try {
 
                LoginMenu loggingOut=new LoginMenu(); //creates a new login menu 
                loggingOut.showLoginMenu(primaryStage); //shows that new login menu 
      
                    } catch (Exception ex) {
                ex.printStackTrace(); 
                     }
                 });

            
        //LAYOUT FORMATTING 
    
        //Adds all labels + textfields into VBox (customerOptions)
        customerOptions.getChildren().addAll(accountInfo, withdraw, deposit, onlinePurchase); //add all elements to the screen 
       
        //Border pane to keep options on left, output screen (stack on left)
        border.setLeft(customerOptions); //ensure the vbox is on the left side (the buttons all on the left)

        //Stack Pane keeps all togther within border 
        customerRoot.getChildren().addAll(imageView,welcome,status,balance,border, logOut); //add the inner stacks for each new window after boredr
        
            } catch (IOException e){
                e.printStackTrace();
            }
        
        return new Scene(customerRoot,1100,750) ; //returns the scene (customer dashboard with the stackpane as the parent and dimensions)
       
        }
    
   
    //Overload method to handle error messages and sucess updates
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
        //EFFECTS: implements the abstraction function and returns a string of all the elements that make up the CustomerDasboard Object
        return "Customer Dashboard:" + "Customer" + ',' + customer.getUsername() + "Balance is:"+ accountBalance + ','+ "Level is" + levelRep+ "Primary Stage:" +this.primaryStage +"Items: Clothes, Shoes, Personal Care Products, Accesories";
    }
        
    
    
    public boolean repOk(){ 
        //EFFECTS: implements the rep invariant and returns true if rep invariant holds for the CustomerDashboard object, if not return false 
        
        if (accountBalance<0){
            return false;
        }
        
        else if (levelRep.equals("silver")||levelRep.equals("gold")||levelRep.equals("platinum")){
            return false;
        }
        
        else if (this.primaryStage==null) {
            return false;
        }
        
        return true;
    }
        
     
    
}
    
    
    
    
    
    
