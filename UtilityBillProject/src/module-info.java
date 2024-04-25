module UtilityBillProject {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.base;
	requires java.sql;
	
	
		opens phase4 to javafx.graphics, javafx.fxml;
}
