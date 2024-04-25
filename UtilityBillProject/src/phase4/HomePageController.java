package phase4;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HomePageController {

	@FXML
	private Button addNewUser;

	@FXML
	private Button paymentButton;

	@FXML
	private Button generateBill;

	@FXML
	private Button viewBillButton;

	public void addNewUserButtonListner(ActionEvent e) throws IOException {

		Parent parent = FXMLLoader.load(getClass().getResource("UserID.fxml"));

		// Build the scene graph.
		Scene scene = new Scene(parent);

		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		// Display our window, using the scene graph.
		stage.setTitle("Create New User");
		stage.setScene(scene);
		stage.show();
	}

	public void generateBillButtonListner(ActionEvent e) throws IOException {

		Parent parent = FXMLLoader.load(getClass().getResource("GenerateBill.fxml"));

		// Build the scene graph.
		Scene scene = new Scene(parent);

		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		// Display our window, using the scene graph.
		stage.setTitle("Generate Bill");
		stage.setScene(scene);
		stage.show();
	}

	public void viewBillButtonListner(ActionEvent e) throws IOException {

		Parent parent = FXMLLoader.load(getClass().getResource("ViewBill.fxml"));

		// Build the scene graph.
		Scene scene = new Scene(parent);

		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		// Display our window, using the scene graph.
		stage.setTitle("Select Bill");
		stage.setScene(scene);
		stage.show();
	}

	public void paymentButtonListner(ActionEvent e) throws IOException {

		Parent parent = FXMLLoader.load(getClass().getResource("Payment.fxml"));

		// Build the scene graph.
		Scene scene = new Scene(parent);

		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		// Display our window, using the scene graph.
		stage.setTitle("Next Page");
		stage.setScene(scene);
		stage.show();
	}

}
