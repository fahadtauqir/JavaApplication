package phase4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BillDialogBoxController {

	@FXML
	private Button BackButton;

	@FXML
	private TextArea userDetailsLabel;

	@FXML
    private TextArea billDetailsLabel;

	private String selectedMeterNo;
	private String selectedEndDate;

	private String[] userProperties = { "Firstname", "Lastname", "MeterNo", "MeterReading", "MeterType", "Date" };
	private String[] billProperties = { "MeterNo", "MeterType", "StartDate", "EndDate", "Units Consumed",
			"Tariff", "Standing Charges", "NumberOfDays", "BillAmount", "Payment Status" };
	
	public void initialize() {
	    userDetailsLabel.setEditable(false);
	    billDetailsLabel.setEditable(false);
	}
	
	public void setMeterDetails(String meterNo, String endDate) {
		this.selectedMeterNo = meterNo;
		this.selectedEndDate = endDate;
		displayMatchingUserData();
		displayMatchingBillData();
	}

	private void displayMatchingUserData() {
		List<String[]> matchingUserData = new ArrayList<>();

		// Fetch matching data from UserIDList.csv
		for (String[] user : readCSV(
				"C:\\Users\\syedf\\eclipse-workspace\\UtilityBillProject\\src\\phase4\\UserIDList.csv")) {
			if (user[2].equals(selectedMeterNo)) {
				matchingUserData.add(user);
			}
		}
		displayUserData(matchingUserData);
	}

	private void displayMatchingBillData() {
		List<String[]> matchingBillData = new ArrayList<>();
		// Fetch matching data from Bills.csv
		for (String[] bill : readCSV(
				"C:\\Users\\syedf\\eclipse-workspace\\UtilityBillProject\\src\\phase4\\Bills.csv")) {
			if (bill[0].equals(selectedMeterNo) && bill[3].equals(selectedEndDate)) {
				matchingBillData.add(bill);
			}
		}

		displayBillData(matchingBillData);
	}

	private void displayBillData(List<String[]> data) {
		StringBuilder sb = new StringBuilder();
		for (String[] line : data) {
			for (int i = 0; i < line.length; i++) {
				if (i < billProperties.length) {
					sb.append(billProperties[i]).append(": \t").append(line[i]).append("\n");
				}
			}
			sb.append("\n");
		}
		billDetailsLabel.setText(sb.toString());
	}

	private void displayUserData(List<String[]> data) {
		StringBuilder sb = new StringBuilder();
		for (String[] line : data) { // Iterate over each line in data
			for (int i = 0; i < line.length; i++) { // Iterate over each property
				if (i < userProperties.length) {
					sb.append(userProperties[i]).append(": \t").append(line[i]).append("\n");
				}
			}
			sb.append("\n"); // Add a newline for separation between entries
		}
		String displayData = sb.toString();

		userDetailsLabel.setText(displayData.toString());
	}

	private List<String[]> readCSV(String filePath) {
		List<String[]> data = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			br.readLine(); // Skip the header
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");
				data.add(values);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	public void BackToBillButtonListener(ActionEvent e) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource("ViewBill.fxml"));

		// Build the scene graph.
		Scene scene = new Scene(parent);

		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		// Display our window, using the scene graph.
		stage.setTitle("View Bill Panel");
		stage.setScene(scene);
		stage.show();
	}

}