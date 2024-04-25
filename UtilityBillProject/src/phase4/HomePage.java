package phase4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HomePage extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		  // Load the FXML file.
	      Parent parent = FXMLLoader.load(
	               getClass().getResource("HomePage.fxml")); 
	      
	      // Build the scene graph.
	      Scene scene = new Scene(parent); 
	      
	      // Display our window, using the scene graph.
	      stage.setTitle("Home Page"); 
	      stage.setScene(scene);
	      stage.show(); 
		
	}

}
