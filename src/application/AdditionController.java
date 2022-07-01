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
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;



public class AdditionController implements Initializable,EventHandler<KeyEvent>{
	
	@FXML
	private RadioButton plus;
	@FXML
	private RadioButton minus;	
	
	@FXML
	private Spinner<Integer> spinnerN;
	
	@FXML
	private Spinner<Integer> spinnerM;
	
	@FXML
	private Button setValueBtn;	
	
	@FXML
	private Button gomenu;
	
	@FXML
	private GridPane grid1;
	
	@FXML
	private GridPane grid2;
	
	@FXML
	private Button calculateBtn;
	
	@FXML
	private Button checkBtn;
	
	@FXML
	private Button clearBtn;
	
	@FXML
	private Label inputLabel;	

	private Integer currentValueN=1;	
	private Integer currentValueM=1;
	
	private int initialValue=1;
	
	private BigDecimal[][] matrix1;
	private BigDecimal[][] matrix2;
	private BigDecimal[][] addition;
	
	private TextField[][] input1;
	private TextField[][] input2;
	
	private SceneController sceneController=new SceneController();	
		
	
	public void menu(ActionEvent event) throws IOException {	
		
		sceneController.switchScene(event,Mode.Menu.getPath());		
		
	}	
	
	public void setValue(ActionEvent event) throws IOException {		
		setMatrixAction();		
	}	
	
	public void clearButtonAction(ActionEvent event) {	
		clear(input1);
		clear(input2);
		
	}
	private void clear(TextField[][] input) {
		
		if(input==null)return;
		
		for(int i=0;i<input.length;i++) {
			for(int j=0;j<input[0].length;j++) {
				input[i][j].setText(null);
			}
		}
	}
	
	public void setMatrixAction() {
		inputLabel.setVisible(true);
		
		calculateBtn.setDisable(true);		
		
		clearRowColumn(grid1);
		clearRowColumn(grid2);
		
		input1=new TextField[currentValueN][currentValueM];
		matrix1=new BigDecimal[currentValueN][currentValueM];
		input2=new TextField[currentValueN][currentValueM];
		matrix2=new BigDecimal[currentValueN][currentValueM];
		addition=new BigDecimal[currentValueN][currentValueM];
		
		setMatrix(matrix1,grid1,currentValueN,currentValueM,input1);
		setMatrix(matrix2,grid2,currentValueN,currentValueM,input2);
		
		
	}
	
	
	private void setMatrix(BigDecimal[][]matrix,GridPane grid,Integer n,Integer m,TextField[][]input) {
		
		grid.getChildren().clear();
		grid.setAlignment(Pos.CENTER);		
		grid.setGridLinesVisible(true);		
		
		for(int i=0;i<n;i++) {
			for(int j=0;j<m;j++) {				
				input[i][j]=new TextField();				
				grid.add(input[i][j], j+1, i+1);
				
			}
			
		}
	}	
	
	public void checkEmptyAction(ActionEvent event) {
		checkEmpty(input1,matrix1,1);
		checkEmpty(input2,matrix2,2);		
		
	}
	
	private void checkEmpty(TextField[][]input,BigDecimal[][]matrix,Integer num) {
		Alert alert = new Alert(AlertType.INFORMATION); 
		alert.setTitle("Wrong message!"); 
		alert.setHeaderText("");		
		
		Boolean empty=false;
		if(!plus.isSelected()&&!minus.isSelected()) {			
			String message="You have not chosse the operator yet!";						
			alert.setContentText(message);
			alert.show();
			return;
		}
		for(int i=0;i<matrix.length;i++) {
			for(int j=0;j<matrix[0].length;j++) {
				try {
					if(input[i][j].getText().isEmpty()) {
						empty=true;			
						String n=num==1?"A":"B";
						String message="Empty element in the matrix "+n+" you input";						
						alert.setContentText(message);
						alert.show();
						
						break;
					}
					else {
						matrix[i][j]=new BigDecimal(input[i][j].getText());		
					}						
										
				}catch(NumberFormatException e) {
					String n=num==1?"A":"B";
					String message="Wrong number fomat in the matrix "+n+" you input";
					alert.setContentText(message);
					alert.show();
					alert.showAndWait();
					empty=true;
					break;
					
				}
			}
			if(empty)break;
		}
		
		if(empty)calculateBtn.setDisable(true);
		else calculateBtn.setDisable(false);
	}
	
	
	
	public void clearRowColumn(GridPane grid) {
		while(grid.getRowConstraints().size() > 0){
		    grid.getRowConstraints().remove(0);
		}

		while(grid.getColumnConstraints().size() > 0){
		    grid.getColumnConstraints().remove(0);
		}
	}
	
	public void calculate(ActionEvent event) throws IOException {
		
		
		for(int i=0;i<currentValueN;i++) {
			for(int j=0;j<currentValueM;j++) {
				if(plus.isSelected())addition[i][j]=matrix1[i][j].add(matrix2[i][j]);
				if(minus.isSelected())addition[i][j]=matrix1[i][j].subtract(matrix2[i][j]);
			}
			
		}
		
		calculateBtn.setDisable(true);
		result();
		
		
	}
	
	public void result() throws IOException {
		
		FXMLLoader loader=new FXMLLoader(getClass().getResource(Mode.Result.getPath()));
		Parent root=loader.load();
		
		Result resultController=loader.getController();
		resultController.getMatrix(addition);
		resultController.assignMatrix(100,60);
		
		Scene scene=new Scene(root);
		Stage stage=new Stage();
		stage.setScene(scene);
		stage.setTitle("Result of Matrix Multiplication");
		stage.show();
		

	}	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
		
		
		setSpinner(spinnerN,currentValueN);
		setSpinner(spinnerM,currentValueM);
		
		spinnerN.valueProperty().addListener(new ChangeListener<Integer>() {				
			@Override
			public void changed(ObservableValue<? extends Integer> arg0, Integer arg1, Integer arg2) {
				currentValueN=(Integer) spinnerN.getValue();		
			}
		});
		spinnerM.valueProperty().addListener(new ChangeListener<Integer>() {				
			@Override
			public void changed(ObservableValue<? extends Integer> arg0, Integer arg1, Integer arg2) {
				
				currentValueM=(Integer) spinnerM.getValue();	
				
			}
		});
		
	}
	
	
	private void setSpinner(Spinner<Integer> spinner,Integer currentValue) {
		
		SpinnerValueFactory<Integer>valueFactory=new SpinnerValueFactory.IntegerSpinnerValueFactory(1,10,initialValue);	
		
		spinner.setValueFactory(valueFactory);
		spinner.setEditable(true);
		currentValue = (Integer) spinner.getValue();
		
	}
	
	
	@Override
	public void handle(KeyEvent event) {		
		if (KeyEvent.KEY_PRESSED.equals(event.getEventType())) {			
			if(event.getCode()==KeyCode.ENTER) {
				System.out.println(event.getCode());
				setMatrixAction();			
				
			}
				
							
        } 
		
	}	
		
}





