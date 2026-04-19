package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import uap.*;

public class AddCourseUIController {
	private Stage stage;
	private Scene scene;
	private Parent root;

	@FXML
    private TextField titleField;

    @FXML
    private TextField idField;

    @FXML
    private TextField durationField;

    @FXML
    private TextField instructorField;

    @FXML
    private ListView<String> courseListView;
    
    @FXML
    private DatePicker date;


    @FXML
    void addCourse(ActionEvent event) throws IOException  {
        
    	try {
    		
    		String title = titleField.getText().trim();
            String courseId = Main.cms.addCourse(title);
            DataHandler.saveData(Main.cms);
            courseListView.getItems().add(courseId + " - " + title);
            titleField.clear();
            
    	} catch (FileNotFoundException e) {
    		
    		courseListView.getItems().add("Error");
    		
    	}
    }

    @FXML
    void offerCourse(ActionEvent event) throws FileNotFoundException, IOException {
    	
        String courseId = idField.getText();
        String instructorId = instructorField.getText();
        int duration = Integer.parseInt(durationField.getText());
        String startDate = date.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        try {
            
            Main.cms.offerCourse(courseId, instructorId, startDate, duration);
            DataHandler.saveData(Main.cms);
            updateCourseListView(courseId);
            
        } catch (InvalidCourseException | InvalidUserException e) {
        	
            e.printStackTrace(); 
            
        }
    }

  
    private void updateCourseListView(String courseId) {
    	
        courseListView.getItems().add(courseId);
        
    }


    @FXML
    void viewCourseList(ActionEvent event) {
    	
        courseListView.getItems().clear();
        ArrayList<String> coursesInfo = new ArrayList<>();
        
        for (Course course : Main.cms.getCourses()) {
        	
            coursesInfo.add(course.getId() + " - " + course.getTitle());
            
        }
        
        courseListView.getItems().addAll(coursesInfo);
    }


    @FXML
    public void Back(ActionEvent e) throws IOException {
    	
    	root = FXMLLoader.load(getClass().getResource("AdminUI.fxml"));
   	    stage=(Stage)((Node)e.getSource()).getScene().getWindow();
   	    scene= new Scene(root);
   	    stage.setScene(scene);
   	    
    }

}

