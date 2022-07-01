package application;

public enum Mode {

	Menu("Menu.fxml"),
	Power("Power.fxml"),
	Transpose("Transpose.fxml"),
	Multiple("Multiple.fxml"),
	AddSubstract("AdditionSubstraction.fxml"),
	Inverse("Inverse.fxml"),
	Determinant("Determinant.fxml"),	
	Result("Result.fxml");
		
	private String path;
	Mode(String text){
		path=text;
	}
	
	public String getPath() {		
		return path;
	}
	
}
