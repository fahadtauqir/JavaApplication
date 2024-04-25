package phase4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PaymentController {

	@FXML
	private ComboBox<String> amountDue;

	@FXML
	private Button backToHome;

	@FXML
	private Button makePayment;

	@FXML
	private ComboBox<String> endDate;

	@FXML
	private ComboBox<String> meterNo;

	private List<String[]> billsList;

	@FXML
	private void initialize() {

		billsList = readCSV("C:\\Users\\syedf\\eclipse-workspace\\UtilityBillProject\\src\\phase4\\Bills.csv");
		populateComboBoxes();
		meterNo.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
			updateEndDateComboBox(newValue);
		});
		meterNo.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
			updateAmountDueComboBox(newValue);
		});
	}

	private void populateComboBoxes() {
		 List<String> meterNos = new ArrayList<>();
		    List<String> selectedDates = new ArrayList<>();
		    List<String> selectedAmountDue = new ArrayList<>();

		    for (String[] bill : billsList) {
		        if (!meterNos.contains(bill[0])) {
		            meterNos.add(bill[0]);
		        }
		        if (!selectedDates.contains(bill[3])) {
		            selectedDates.add(bill[3]);
		        }
		        if (!selectedAmountDue.contains(bill[8])) {
		            selectedAmountDue.add(bill[8]);
		        }
		    }

		    meterNo.getItems().setAll(meterNos);
		    endDate.getItems().setAll(selectedDates);
		    amountDue.getItems().setAll(selectedAmountDue);
		}

	private List<String[]> readCSV(String filePath) {
		List<String[]> data = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			line = br.readLine();
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");
				data.add(values);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// print the size of the list to see if data is read
		System.out.println("Read " + data.size() + " records from " + filePath);
		return data;
	}

	private void updateEndDateComboBox(String selectedMeterNo) {
		Set<String> dates = new HashSet<>();
		for (String[] bill : billsList) {
			if (bill[0].equals(selectedMeterNo)) {
				dates.add(bill[3]); 

			}
		}
		endDate.getItems().setAll(dates);
	}

	private void updateAmountDueComboBox(String selectedMeterNo) {
		Set<String> selectedamountDue = new HashSet<>();
		for (String[] bill : billsList) {
			if (bill[0].equals(selectedMeterNo)) { // Check if the meter number matches
				selectedamountDue.add(bill[8]); 

			}
		}
		amountDue.getItems().setAll(selectedamountDue);
	}

	@FXML
	private void MakePaymentbuttonlistener(ActionEvent event) {
		String selectedMeterNo = meterNo.getValue();
		String selectedDate = endDate.getValue();
		String selectedamountdue = amountDue.getValue();
		if (selectedMeterNo == null || selectedDate == null || selectedamountdue == null) {
			showInvalidInputAlert("Please select all the fields.");
			return;
		}
		
		updateBillAsPaid(selectedMeterNo, selectedDate);
		writeCSV("C:\\Users\\syedf\\eclipse-workspace\\UtilityBillProject\\src\\phase4\\Bills.csv");
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Payment Successfull");
		alert.setHeaderText(null);
		alert.setContentText("Payment has been made successfully!");
		alert.showAndWait();
	}

	private void updateBillAsPaid(String meterNo, String date) {
		for (String[] bill : billsList) {
			if (bill[0].equals(meterNo) && bill[3].equals(date)) {
				bill[9] = "Paid";
				break;
			}
		}
	}

	private void writeCSV(String filePath) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
			
			bw.write("Meter Number, Meter Type, Start Date, End Date, Number of Units, Tariff, Standing Charges, NumberOfDays, Bill Amount, Payment Status \n");

			for (String[] bill : billsList) {
				bw.write(String.join(",", bill) + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void showInvalidInputAlert(String string) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Invalid Input");
		alert.setHeaderText(null);
		alert.setContentText("Please select all the fields.");
		alert.showAndWait();
	}
	public void BackToHomeButtonListener(ActionEvent e) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource("HomePage.fxml"));

		// Build the scene graph.
		Scene scene = new Scene(parent);

		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		// Display our window, using the scene graph.
		stage.setTitle("Create New User");
		stage.setScene(scene);
		stage.show();
	}

}