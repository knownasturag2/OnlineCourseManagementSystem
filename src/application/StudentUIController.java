package application;

import uap.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class StudentUIController implements Initializable{
	
	@FXML
	private TextField text;
	
	@FXML
	private TextField id;
	
	@FXML
	private TextField cId;
	
	@FXML
	private ListView<String> view;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
		
	public void initialize(URL arg0, ResourceBundle arg1) {
		view.getItems().clear();
        ArrayList<String> courseOfferInfo = new ArrayList<>();
        
        for (CourseRecord record : Main.cms.getOfferedCourses()) {
            try {
                Course course = Main.cms.findCourse(record.getCourseId());
                User user = Main.cms.findUser(record.getInstructorId());

                
                String courseId = record.getId(); 
                String courseTitle = course.getTitle();
                String instructorName = user.getName();
                String startDate = record.getStart_date().toString();
                int duration = record.getDuration();

                String courseOfferDetails = String.format("Offer Course ID: %s | Course Title: %s | Instructor: %s | Start Date: %s | Duration: %d hours", 
                                                          courseId, courseTitle, instructorName, startDate, duration);
                
                courseOfferInfo.add(courseOfferDetails);
            } catch (InvalidCourseException | InvalidUserException e) {
                e.printStackTrace();
            }
        }
        
        view.getItems().addAll(courseOfferInfo);
    }
	
	@FXML
    public void searchButton(ActionEvent event) {
		
		String title = text.getText();
        try {
            ArrayList<CourseRecord> courseRecords = Main.cms.getOfferedCoursesWithTitle(title);
            view.getItems().clear();
            for (CourseRecord record : courseRecords) {
                view.getItems().add(record.getCourseId());
            }
        } catch (NotAvailableException e) {
        	view.getItems().clear();
            view.getItems().add(e.getMessage());
        }
        
	}
	
	 @FXML
	    private void enrollButton(ActionEvent event) throws NotAvailableException, InvalidUserException {
	        ObservableList<String> enrolledCoursesList = FXCollections.observableArrayList();
	        String studentId = id.getText().trim();

	        try {
	            Student student = (Student) Main.cms.findUser(studentId);
	            if (student != null) {
	                for (String courseId : student.getCourseRecordIds().keySet()) {
	                    if (student.getCourseRecordIds().get(courseId).equals("registered")) {
	                        CourseRecord record = Main.cms.findCourseRecord(courseId);
	                        Course course = Main.cms.findCourse(record.getCourseId());
	                        enrolledCoursesList.add(course.getTitle() + " - " + record.getStart_date().toString());
	                    }
	                }
	            }
	        } catch (InvalidUserException | InvalidCourseException e) {
	            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
	            e.printStackTrace();
	        }

	        view.getItems().clear();
	        view.setItems(enrolledCoursesList);
	    }
	 
    public void registerButton(ActionEvent event) throws FileNotFoundException, IOException {
    	
    	String studentId = id.getText().trim();
        String courseRecordId = cId.getText().trim();
        
        try {
            Student student = (Student) Main.cms.findUser(studentId); 
            Main.cms.registerStudent(student, courseRecordId);
            DataHandler.saveData(Main.cms);
            JOptionPane.showMessageDialog(null, "Student enrolled successfully.");
            id.setText("");
            cId.setText("");
        } catch (InvalidCourseException | InvalidUserException e) {
        	 JOptionPane.showMessageDialog(null, "Error: Not found");
        }
	}
    
    public void attendButton(ActionEvent event) throws InvalidUserException, FileNotFoundException, IOException {
    	
    	String studentId = id.getText().trim();
        String courseRecordId = cId.getText().trim();

        try {
            Student student = (Student) Main.cms.findUser(studentId);
            Main.cms.attendClass(student, courseRecordId);
            DataHandler.saveData(Main.cms);
            JOptionPane.showMessageDialog(null, "Student marked as attending the class.");
            id.setText("");
            cId.setText("");
        } catch (InvalidCourseException | InvalidUserException e) {
        	JOptionPane.showMessageDialog(null, "Error: Not found");
        }
    	 
	}
    
    public void completeButton(ActionEvent event) throws InvalidUserException, FileNotFoundException, IOException {
    	
    	String studentId = id.getText().trim();
        String courseRecordId = cId.getText().trim();

        try {
            Student student = (Student) Main.cms.findUser(studentId);
            Main.cms.completeCourse(student, courseRecordId);
            DataHandler.saveData(Main.cms);
            JOptionPane.showMessageDialog(null, "Student marked as completed the course.");
            id.setText("");
            cId.setText("");
        } catch (InvalidCourseException | InvalidUserException e) {
        	JOptionPane.showMessageDialog(null, "Error: Not found");
        }

    }
    
    public void logout(ActionEvent e) throws IOException {
    	
    	root = FXMLLoader.load(getClass().getResource("LogOutUI.fxml"));
	    stage=(Stage)((Node)e.getSource()).getScene().getWindow();
	    scene= new Scene(root);
	    stage.setScene(scene);
    	
    }
    
}
