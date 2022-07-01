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



public class MultipleController implements Initializable,EventHandler<KeyEvent>{
	
	@FXML
	private Spinner<Integer> spinnerN1;
	
	@FXML
	private Spinner<Integer> spinnerN2;
	
	@FXML
	private Spinner<Integer> spinnerM1;
	
	@FXML
	private Spinner<Integer>spinnerM2;
	
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

	private Integer currentValueN1=1;	
	private Integer currentValueN2=1;
	private Integer currentValueM1=1;
	private Integer currentValueM2=1;
	
	private int initialValue=1;
	
	private BigDecimal[][] matrix1;
	private BigDecimal[][] matrix2;
	private BigDecimal[][] multiple;
	
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
		
		input1=new TextField[currentValueN1][currentValueN2];
		matrix1=new BigDecimal[currentValueN1][currentValueN2];
		input2=new TextField[currentValueM1][currentValueM2];
		matrix2=new BigDecimal[currentValueM1][currentValueM2];
		multiple=new BigDecimal[currentValueN1][currentValueM2];
		
		setMatrix(matrix1,grid1,currentValueN1,currentValueN2,input1);
		setMatrix(matrix2,grid2,currentValueM1,currentValueM2,input2);
		
		
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
		
		int row=0,col=0,r=0;
		BigDecimal sum=BigDecimal.valueOf(0);
		while(row<matrix1.length) {
			
			while(r<matrix2[0].length) {
				
				if(col<matrix2.length) {
					sum=sum.add(matrix1[row][col].multiply(matrix2[col][r]));
					col++;
				}
				if(col>=matrix2.length&&r<matrix2[0].length) {
					multiple[row][r]=sum;
					sum=BigDecimal.valueOf(0);
					r++;
					col=0;
				}
				
			}
			r=0;
			col=0;		
			row++;		
			
		}
		
		
		calculateBtn.setDisable(true);
		result();
		
		
	}
	
	public void result() throws IOException {
		
		FXMLLoader loader=new FXMLLoader(getClass().getResource(Mode.Result.getPath()));
		Parent root=loader.load();
		
		Result resultController=loader.getController();
		resultController.getMatrix(multiple);
		resultController.assignMatrix(100,60);
		
		Scene scene=new Scene(root);
		Stage stage=new Stage();
		stage.setScene(scene);
		stage.setTitle("Result of Matrix Multiplication");
		stage.show();
		

	}	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
		
		
		setSpinner(spinnerN1,currentValueN1);
		setSpinner(spinnerN2,currentValueN2);
		setSpinner(spinnerM1,currentValueM1);
		setSpinner(spinnerM2,currentValueM2);
		
		spinnerN1.valueProperty().addListener(new ChangeListener<Integer>() {				
			@Override
			public void changed(ObservableValue<? extends Integer> arg0, Integer arg1, Integer arg2) {
				currentValueN1=(Integer) spinnerN1.getValue();		
			}
		});
		spinnerN2.valueProperty().addListener(new ChangeListener<Integer>() {				
			@Override
			public void changed(ObservableValue<? extends Integer> arg0, Integer arg1, Integer arg2) {
				currentValueN2=(Integer) spinnerN2.getValue();	
				currentValueM1=(Integer) spinnerN2.getValue();	
				spinnerM1.getValueFactory().setValue(spinnerN2.getValue());
				
			}
		});
		spinnerM1.valueProperty().addListener(new ChangeListener<Integer>() {				
			@Override
			public void changed(ObservableValue<? extends Integer> arg0, Integer arg1, Integer arg2) {
				currentValueN2=(Integer) spinnerM1.getValue();	
				currentValueM1=(Integer) spinnerM1.getValue();	
				spinnerN2.getValueFactory().setValue(spinnerM1.getValue());
			}
		});
		spinnerM2.valueProperty().addListener(new ChangeListener<Integer>() {				
			@Override
			public void changed(ObservableValue<? extends Integer> arg0, Integer arg1, Integer arg2) {
				currentValueM2=(Integer) spinnerM2.getValue();	
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





