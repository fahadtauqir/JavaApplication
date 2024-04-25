package phase4;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class UserIDController {

	@FXML
	private Button backToHome;

	@FXML
	private DatePicker date;

	@FXML
	private TextField firstName;

	@FXML
	private TextField lastName;

	@FXML
	private TextField meterNo;

	@FXML
	private Button createButton;

	@FXML
	private TextField meterReading;

	@FXML
	private ComboBox<String> meterType;

	private static UserData userData;

	private static List<UserData> userDataList = new ArrayList<>();// Initialise the list

	public void initialize() {
		meterType.getItems().addAll("Electricity", "Gas");
	}

	public void createButtonListener(ActionEvent e) throws IOException {

		if (firstName.getText().isEmpty() || lastName.getText().isEmpty()) {
			showInvalidInputAlert1("Please fill in all the fields (First Name & Last Name)");
			return;
		}

		if (date.getValue() == null) {
			showInvalidInputAlert2("Please Enter a Date");
			return;
		}

		int meterReadingValue;
		try {
			meterReadingValue = Integer.parseInt(meterReading.getText());
		} catch (NumberFormatException ex) {
			showInvalidInputAlert1("Invalid meter reading. Please enter a valid number.");
			return;
		}

		int meterNoValue;
		try {
			meterNoValue = Integer.parseInt(meterNo.getText());
		} catch (NumberFormatException ex) {
			showInvalidInputAlert1("Invalid meter No. Please enter a valid number");
			return;
		}

		if (meterNoExists(meterNoValue)) {
			showInvalidInputAlert1("Meter number already exists. Please enter a different number.");
			return;
		}

		LocalDate StartDate = date.getValue();

		userData = new UserData(firstName.getText(), lastName.getText(), meterNoValue, meterReadingValue,
				meterType.getValue(), StartDate); // error needs to be resolved

		clearinputfields();
		showUserCreatedDialog();
		userDataList.add(userData);
		String filePath = "C:\\Users\\syedf\\eclipse-workspace\\UtilityBillProject\\src\\phase4\\UserIDList.csv";
		UserData.writeUserDataToCSV(filePath, userDataList);
	}

	private boolean meterNoExists(int meterNoValue) {
		for (UserData userData : userDataList) {
			if (userData.getMeterNo() == meterNoValue) {
				return true;
			}
		}
		return false;
	}

	private void clearinputfields() {
		firstName.clear();
		lastName.clear();
		meterNo.clear();
		meterReading.clear();
		date.setValue(null);
	}

	private void showInvalidInputAlert1(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Invalid Input");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	private void showInvalidInputAlert2(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Please enter Date");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	private void showUserCreatedDialog() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("User Created");
		alert.setHeaderText(null);
		alert.setContentText("User created successfully!");
		alert.showAndWait();
	}

	public void BackToHomeButtonListener(ActionEvent e) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource("HomePage.fxml"));

		// Build the scene graph.
		Scene scene = new Scene(parent);

		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		// Display our window, using the scene graph.
		stage.setTitle("Home Page");
		stage.setScene(scene);
		stage.show();
	}

}
