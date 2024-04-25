package phase4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GenerateBillController {

	@FXML
	private Button backToHome;

	@FXML
	private TextField numberOfUnits;

	@FXML
	private DatePicker endDate;

	@FXML
	private ComboBox<String> meterNo;

	@FXML
	private DatePicker startDate;

	@FXML
	private ComboBox<String> meterType;

	@FXML
	private TextField tariff;

	@FXML
	private TextField standingCharges;

	private List<String[]> userIDList;

	private List<BillData> billDataList = new ArrayList<>();

	@FXML
	private void initialize() {

		userIDList = readCSV("C:\\Users\\syedf\\eclipse-workspace\\UtilityBillProject\\src\\phase4\\UserIDList.csv");
		populateComboBoxes();
	}

	private void populateComboBoxes() {
		List<String> meterNos = new ArrayList<>();
	    List<String> meterTypes = new ArrayList<>();

	    for (String[] record : userIDList) {
	        if (!meterNos.contains(record[2])) {
	            meterNos.add(record[2]);
	        }
	    }

	    for (String[] record : userIDList) {
	        if (!meterTypes.contains(record[4])) {
	            meterTypes.add(record[4]);
	        }
	    }

	    meterNo.getItems().setAll(meterNos);
	    meterType.getItems().setAll(meterTypes);
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
		return data;
	}

	private void writeBillToFile(BillData billData) {
		String fileName = "C:\\Users\\syedf\\eclipse-workspace\\UtilityBillProject\\src\\phase4\\Bills.csv";

		try (FileWriter writer = new FileWriter(fileName, true)) {
			if (!fileExists(fileName) || new File(fileName).length() == 0) {
				writer.write(
						"Meter Number, Meter Type, Start Date, End Date, Number of Units, Tariff, Standing Charges, NumberOfDays, Bill Amount, Payment Status \n");
			}

			writer.write(billData.getMeterNo() + ", " + billData.getMeterType() + ", " + billData.getStart() + ", "
					+ billData.getEnd() + ", " + billData.getNumberOfUnits() + ", $" + billData.getTariff() + ", $"
					+ billData.getStandingCharges() + ", " + billData.getdaysBetween() + ", $"
					+ billData.getBillAmount() + ", " + billData.getpaymentStatus() + "\n");

			System.out.println("Bill details written to file: " + fileName);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error writing bill to file: " + e.getMessage());
		}
	}

	private boolean fileExists(String fileName) {
		File file = new File(fileName);
		return file.exists() && !file.isDirectory();
	}

	public void generateButtonListener() {
		String selectedMeterNo = meterNo.getValue();
		String selectedMeterType = meterType.getValue();
		LocalDate start = startDate.getValue();
		LocalDate end = endDate.getValue();
		if (selectedMeterNo == null || selectedMeterType == null || start == null || end == null) {
			showInvalidInputAlert2("Please fill in all the fields.");
			return;
		}
		long convertdaysBetween = ChronoUnit.DAYS.between(start, end);
		int daysBetween = (int) convertdaysBetween;
		Integer numberOfUnitsValue;
		double tariffValue;
		double scharge;
		double billAmount = 0;
		String paymentStatus = "Unpaid";

		try {
			numberOfUnitsValue = Integer.parseInt(numberOfUnits.getText());
		} catch (NumberFormatException e) {
			showInvalidInputAlert1("Number of Units must be a valid integer.");
			return;
		}

		try {
			tariffValue = Double.parseDouble(tariff.getText());
		} catch (NumberFormatException e) {
			showInvalidInputAlert3("Tariff cannot be emptyp or must be a valid number.");
			return;
		}
		try {
			scharge = Double.parseDouble(standingCharges.getText());
		} catch (NumberFormatException e) {
			showInvalidInputAlert4("Standing Charge cannot be empty or must be a valid number.");
			return;
		}

		BillData billData = new BillData(selectedMeterNo, selectedMeterType, start, end, numberOfUnitsValue,
				tariffValue, scharge, daysBetween, billAmount, paymentStatus);

		billData.calculateBillAmount();
		billDataList.add(billData);

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Bill Generated");
		alert.setHeaderText(null);
		alert.setContentText("Bill generated successfully!");
		alert.showAndWait();

		writeBillToFile(billData);
	}

	private void showInvalidInputAlert1(String string) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Invalid Input");
		alert.setHeaderText(null);
		alert.setContentText("Number of Units must be a valid integer.");
		alert.showAndWait();
	}

	private void showInvalidInputAlert2(String string) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Invalid Input");
		alert.setHeaderText(null);
		alert.setContentText("Please fill in all the fields.");
		alert.showAndWait();
	}

	private void showInvalidInputAlert3(String string) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Invalid Input");
		alert.setHeaderText(null);
		alert.setContentText("Tariff must be a valid number.");
		alert.showAndWait();
	}

	private void showInvalidInputAlert4(String string) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Invalid Input");
		alert.setHeaderText(null);
		alert.setContentText("Standing Charge must be a valid number.");
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