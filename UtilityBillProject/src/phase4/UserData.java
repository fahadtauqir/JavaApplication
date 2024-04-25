package phase4;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class UserData {
	private String firstName;
	private String lastName;
	private int meterNo;
	private int meterReading;
	private String meterType;
	private LocalDate date;

	public UserData() {

	}

	public UserData(String firstName, String lastName, int meterNo, int meterReading,
			String meterType, LocalDate date) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.meterNo = meterNo;
		this.meterReading = meterReading;
		this.meterType = meterType;
		this.date = date;
	}

	// Method to write a list of UserData to a CSV file
	public static void writeUserDataToCSV(String filePath, List<UserData> userDataList) {	//It iterates through the userDataList 
		try (FileWriter writer = new FileWriter(filePath, false)) { 						//and formats each UserData object as a CSV line
			// Write CSV header
			writer.write("FirstName,LastName,MeterNo,MeterReading,MeterType,Date\n"); // writing headers in file

			// Write UserData to csv
			for (UserData userData : userDataList) { 	// extracting from userDataList and writing into file using enhanced for loop
				writer.write(userData.toCSVString() + "\n"); // for comma seperated writing into the file
			}

			System.out.println("Data written to CSV file successfully.");

		} catch (IOException e) {
			System.err.println("Error writing to CSV file: " + e.getMessage());
		}
	}

	// Getters and setters for each field
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getMeterNo() {
		return meterNo;
	}

	public void setMeterNo(int meterNo) {
		this.meterNo = meterNo;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}	

	public int getMeterReading() {
		return meterReading;
	}

	public void setMeterReading(int meterReading) {
		this.meterReading = meterReading;
	}

	public String getmeterType() {
		return meterType;
	}

	public void setmeterType(String meterType) {
		this.meterType = meterType;
	}

	public String toCSVString() {
		return String.join(",", firstName, lastName, String.valueOf(meterNo), String.valueOf(meterReading),
				meterType, date.toString());
	}

}
