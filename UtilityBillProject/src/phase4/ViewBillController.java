package phase4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewBillController {

	@FXML
	private Button backToHome;

	@FXML
	private ComboBox<String> meterType;

	@FXML
	private ComboBox<String> meterNo;

	@FXML
	private ComboBox<String> endDate;

	@FXML
	private Button viewButton;

	private List<String[]> billsList;

	private String selectedMeterNo;
	private String selectedEndDate;

	@FXML
	private void initialize() {
		
		billsList = readCSV("C:\\Users\\syedf\\eclipse-workspace\\UtilityBillProject\\src\\phase4\\Bills.csv");

		// Populate meterNo ComboBox
		populateComboBoxes();

		meterNo.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
			updateEndDateComboBox(newValue);
		});

	}

	private void populateComboBoxes() {
	    List<String> meterNos = new ArrayList<>();
	    List<String> selectedDates = new ArrayList<>();

	    for (String[] bill : billsList) {
	        if (!meterNos.contains(bill[0])) {
	            meterNos.add(bill[0]);
	        }
	        if (!selectedDates.contains(bill[3])) {
	            selectedDates.add(bill[3]);
	        }
	    }

	    meterNo.getItems().setAll(meterNos);
	    endDate.getItems().setAll(selectedDates);
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

	public void ViewBillButtonListner(ActionEvent e) throws IOException {

		
		selectedMeterNo = meterNo.getValue();
		selectedEndDate = endDate.getValue();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("BillDialogBox.fxml"));
		Parent root = loader.load();

		BillDialogBoxController dialogController = loader.getController();
		dialogController.setMeterDetails(selectedMeterNo, selectedEndDate);

		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		stage.setTitle("User Bill");
		stage.setScene(new Scene(root));
		stage.show();
	}

	private void updateEndDateComboBox(String selectedMeterNo) {
		Set<String> dates = new HashSet<>();
		for (String[] bill : billsList) {
			if (bill[0].equals(selectedMeterNo)) { // Check if the meter number matches
				dates.add(bill[3]); // Assuming date is at index 3
			}
		}
		endDate.getItems().setAll(dates);
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
