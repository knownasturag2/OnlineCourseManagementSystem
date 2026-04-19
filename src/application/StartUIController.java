package application;

import uap.*;
import javafx.scene.Scene;
import java.io.IOException;
import javax.swing.JOptionPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;

public class StartUIController {

    @FXML
    private Label textlabel;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button clickButton;

    @FXML
    private Button signInButton;
    
    
    
    
    @FXML
    public void LogIn(ActionEvent e) throws IOException {
    	
        String userId = passwordField.getText().trim();
        String Password = passwordField.getText().trim();
        
        try {
        	
            User user = Main.cms.findUser(userId);
            
            if (user != null) {
            	
                if (user instanceof Admin || Password.equals("a-1234")) {
                    
                    AnchorPane root = FXMLLoader.load(getClass().getResource("AdminUI.fxml"));
                    Scene scene = new Scene(root);            
                    Main.stage.setScene(scene);
                    Main.stage.show();
                    
                } else if (user instanceof Instructor || Password.equals("i-1234")) {
                    
                    AnchorPane root = FXMLLoader.load(getClass().getResource("InstructorUI.fxml"));
                    Scene scene = new Scene(root);            
                    Main.stage.setScene(scene);
                    Main.stage.show();
                    
                } else if (user instanceof Student || Password.equals("s-1234")) {
                   
                    AnchorPane root = FXMLLoader.load(getClass().getResource("StudentUI.fxml"));
                    Scene scene = new Scene(root);            
                    Main.stage.setScene(scene);
                    Main.stage.show();
                    
                }
                
            } else {
                
            	JOptionPane.showMessageDialog(null, "Not Found");
            }
            
        } catch (InvalidUserException e1) {
           
        	JOptionPane.showMessageDialog(null, "Not Found");
        	
        }
        
    }
    
    

    @FXML
    public void SignIn(ActionEvent e) throws IOException {
    	
    	AnchorPane root = FXMLLoader.load(getClass().getResource("SignInUI.fxml"));
        Scene scene = new Scene(root);            
        Main.stage.setScene(scene);
        Main.stage.show();
        
    }

    
}

