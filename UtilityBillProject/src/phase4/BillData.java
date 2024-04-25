package phase4;

import java.time.LocalDate;

public class BillData {
	private String meterNo;
	private String meterType;
	private LocalDate start;
	private LocalDate end;
	private int numberOfUnits;
	private double tariff;
	private double standingCharges;
	private int daysBetween;
	private double billAmount;
	private String paymentStatus;

	public BillData(String meterNo, String meterType, LocalDate start, LocalDate end, int numberOfUnits, double tariff,
			double standingCharges, int daysBetween, double billAmount, String paymentStatus) {
		this.meterNo = meterNo;
		this.meterType = meterType;
		this.start = start;
		this.end = end;
		this.numberOfUnits = numberOfUnits;
		this.tariff = tariff;
		this.standingCharges = standingCharges;
		this.daysBetween = daysBetween;
		this.billAmount = billAmount;
		this.paymentStatus = paymentStatus;
	}

	public String getMeterNo() {
		return meterNo;
	}

	public String getMeterType() {
		return meterType;
	}

	public LocalDate getStart() {
		return start;
	}

	public LocalDate getEnd() {
		return end;
	}

	public int getNumberOfUnits() {
		return numberOfUnits;
	}

	public void setNumberOfUnits(int numberOfUnits) {
		this.numberOfUnits = numberOfUnits;
	}

	public double getTariff() {
		return tariff;
	}

	public void setTariff(double tariff) {
		this.tariff = tariff;
	}

	public double getStandingCharges() {
		return standingCharges;
	}

	public void setStandingCharges(double standingCharges) {
		this.standingCharges = standingCharges;
	}

	public int getdaysBetween() {
		return daysBetween;
	}

	public void setdaysBetween(int daysBetween) {
		this.daysBetween = daysBetween;
	}

	public double getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(double billAmount) {
		this.billAmount = billAmount;
	}
	
	public String getpaymentStatus() {
		return paymentStatus;
	}
	
	public void setpaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}


	@Override
	public String toString() {
		return "BillData{" + "meterNo='" + meterNo + '\'' + ", meterType='" + meterType + '\'' + ", start=" + start
				+ ", end=" + end + ", numberOfUnits=" + numberOfUnits + ", tariff=" + tariff + ", billAmount="
				+ billAmount + '}';
	}

	public void calculateBillAmount() {
		this.billAmount = (this.numberOfUnits * this.tariff) + (this.standingCharges * this.daysBetween);
	}
}
