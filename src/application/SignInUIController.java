package application;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;
import uap.*;


public class SignInUIController {

	@FXML
    private TextField name;

    @FXML
    private TextField age;

    @FXML
    private Button signInButton;
    
    
    
    
    @FXML
    void SignIn(ActionEvent e) throws IOException {
    	
        String studentName = name.getText().trim();
        String studentAge = age.getText().trim();
        
        if (studentName.isEmpty() || studentAge.isEmpty()) {
        	
	        System.out.println("Please enter name and age.");
	        return;
	        
	    }
        
        try {
        	
        	int age = Integer.parseInt(studentAge);       
	        String loginId = Main.cms.addStudent(studentName, age); 
	        DataHandler.saveData(Main.cms);        
	        JOptionPane.showMessageDialog(null, "Student information saved successfully.\nGenerated ID: " + loginId);
	        
	        
        } catch (NumberFormatException e1) {
        	
        	System.out.println("Age must be a number.");
        	
        }
        	
	        	
	    AnchorPane root = FXMLLoader.load(getClass().getResource("StartUI.fxml"));
        Scene scene = new Scene(root);            
        Main.stage.setScene(scene);
        Main.stage.show();
        
    }
    
}
                	
            
       

