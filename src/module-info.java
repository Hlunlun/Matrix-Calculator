module MatrixCalculator {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;	
	requires org.junit.jupiter.api;
	
	opens application to javafx.graphics, javafx.fxml;
}
