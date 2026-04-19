package application;
	
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import uap.*;

public class Main extends Application {
	
	public static CourseManagementSystem cms = null;
	public static Stage stage;
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		
		stage = primaryStage;
	    Parent root = FXMLLoader.load(getClass().getResource("StartUI.fxml"));
	    Scene scene = new Scene(root);
	    stage.setScene(scene);
	    primaryStage.setTitle("Online Course Management System");
	    stage.show();
	    
	}
	
	public static void main(String[] args) {
		
		try {
			
			cms = DataHandler.loadData();
			System.out.println("Load");
			
		} catch (ClassNotFoundException | IOException e) {
			
			System.out.println("New Data");
			cms = new CourseManagementSystem("UAP");
			cms.addAdmin("Turag", 22);
			cms.addAdmin("Maisha", 26);
			
			try {
				
				DataHandler.saveData(cms);
				
			} catch (IOException e1) {
				
				e1.printStackTrace();
				
			}
				
		}
		
		launch(args);
		
	}
}