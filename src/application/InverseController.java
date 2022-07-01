package application;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;




public class InverseController implements Initializable,EventHandler<KeyEvent>{
	
	@FXML
	private Spinner<Integer> spinnerN;
			
	@FXML
	private Button setValueBtn;	
	
	@FXML
	private Button gomenu;
	
	@FXML
	GridPane grid;	
	
	@FXML
	private Button calculateBtn;
	
	@FXML
	private Button checkBtn;
	
	@FXML
	private Button clearBtn;
	
	@FXML
	private Label inputLabel;
	
	

	private Integer currentValueN=1;
	private int initialValue=1;
	
	private BigDecimal[][] matrix;
	private BigDecimal[][] inverse;
	
	private TextField[][] input;
	
	private SceneController sceneController=new SceneController();	
		
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
		
		
		SpinnerValueFactory<Integer>valueFactory=new SpinnerValueFactory.IntegerSpinnerValueFactory(1,5,initialValue);
		
		spinnerN.setValueFactory(valueFactory);
		spinnerN.setEditable(true);
		currentValueN = (Integer) spinnerN.getValue();
		
		
		spinnerN.valueProperty().addListener(new ChangeListener<Integer>() {			
			@Override
			public void changed(ObservableValue<? extends Integer> arg0, Integer arg1, Integer arg2) {
				currentValueN=(Integer) spinnerN.getValue();				
				
			}
		});
		
	}
	
	
	public void menu(ActionEvent event) throws IOException {	
		
		sceneController.switchScene(event,Mode.Menu.getPath());			
		
	}	
	
	public void setValue(ActionEvent event) throws IOException {		
		setMatrix();		
	}	
	
	public void clearButtonAction(ActionEvent event) {	
		
		if(input==null)return;
		
		for(int i=0;i<input.length;i++) {
			for(int j=0;j<input[0].length;j++) {
				input[i][j].setText(null);
			}
		}
	}
	
	public void setMatrix() {
		inputLabel.setVisible(true);
		
		calculateBtn.setDisable(true);
		
		
		clearRowColumn();
		grid.getChildren().clear();
		grid.setAlignment(Pos.CENTER);		
		grid.setGridLinesVisible(true);	
		
		input=new TextField[currentValueN][currentValueN];		
		
		matrix=new BigDecimal[currentValueN][currentValueN];
		
		for(int i=0;i<currentValueN;i++) {
			for(int j=0;j<currentValueN;j++) {
				input[i][j]=new TextField();				
				grid.add(input[i][j], j+1, i+1);
				
			}
			
		}
	}
	
	
	public void checkEmpty(ActionEvent event) {
		
		Alert alert = new Alert(AlertType.INFORMATION); 
		alert.setTitle("Wrong message!"); 
		alert.setHeaderText("");
		
		
		Boolean empty=false;
		
		for(int i=0;i<input.length;i++) {
			for(int j=0;j<input[0].length;j++) {
				try {
					if(input[i][j].getText().equals("")) {
						empty=true;
						String message="Empty element in the matrix you input";						
						alert.setContentText(message);
						alert.show();
						alert.showAndWait();
						break;
					}
					else {
						matrix[i][j]=new BigDecimal(input[i][j].getText());			
							
					}
					
				}catch(NumberFormatException e) {
					String message="Wrong number fomat in the matrix you input";
					alert.setContentText(message);
					alert.show();
					alert.showAndWait();
					empty=true;
					break;
					// 實體化Alert對話框物件，並直接在建構子設定對話框的訊息類型、文字和按鈕
				}
			}
			if(empty)break;
		}
		
		if(empty)calculateBtn.setDisable(true);
		else calculateBtn.setDisable(false);
	}
	
	
	
	public void clearRowColumn() {
		while(grid.getRowConstraints().size() > 0){
		    grid.getRowConstraints().remove(0);
		}

		while(grid.getColumnConstraints().size() > 0){
		    grid.getColumnConstraints().remove(0);
		}
	}
	
	public void calculate(ActionEvent event) throws IOException {
		
		Determinant determinant=new Determinant(matrix);
		BigDecimal det=determinant.calculateDeterminant();
		if(!det.equals(BigDecimal.valueOf(0))) {
			
			
						
		}
		
		calculateBtn.setDisable(true);
		result();
		
		
	}
	
	public void result() throws IOException {
		
		FXMLLoader loader=new FXMLLoader(getClass().getResource(Mode.Result.getPath()));
		Parent root=loader.load();
		
		Result resultController=loader.getController();
		Determinant determinant=new Determinant(matrix);
		BigDecimal det=determinant.calculateDeterminant();
		if(det.equals(BigDecimal.valueOf(0))) {
			resultController.showAnswer("There is no inverse matrix for the matrix you input!");
		}
		else {
			resultController.getMatrix(inverse);
			resultController.assignMatrix(40,30);
		}
		
		
		Scene scene=new Scene(root);
		Stage stage=new Stage();
		stage.setScene(scene);
		stage.setTitle("Result of Matrix Inverse");
		stage.show();
		

	}	
	
	
	
	
	@Override
	public void handle(KeyEvent event) {		
		if (KeyEvent.KEY_PRESSED.equals(event.getEventType())) {			
			if(event.getCode()==KeyCode.ENTER) {
				System.out.println(event.getCode());
				setMatrix();			
				
			}
				
							
        } 
		
	}

	
		
}


