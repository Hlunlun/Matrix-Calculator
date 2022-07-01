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


public class PowerController implements Initializable,EventHandler<KeyEvent>{
	
	@FXML
	private Spinner<Integer> spinnerN;
	
	@FXML
	private Spinner<Integer> spinnerM;
		
	
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
	private Integer currentValueM=1;
	private int initialValue=1;
	
	private BigDecimal[][] matrix;
	private BigDecimal[][] power;
	
	private TextField[][] input;
	
	private SceneController sceneController=new SceneController();	
		
	
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
		power=new BigDecimal[currentValueN][currentValueN];
		power=matrix;
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
		
		int row=0,col=0,r=0,n=0;
		BigDecimal sum=BigDecimal.valueOf(0);
		
		while(n<currentValueM) {
			while(row<matrix.length) {
				
				while(r<matrix.length) {
					
					if(col<matrix.length) {
						sum=sum.add(power[row][col].multiply(matrix[col][r]));
						col++;
					}
					if(col>=matrix.length&&r<matrix.length) {
						power[row][r]=sum;
						sum=BigDecimal.valueOf(0);
						r++;
						col=0;
					}				
				}
				r=0;
				col=0;		
				row++;				
			}
			row=0;
			n++;
		}		
		
		calculateBtn.setDisable(true);
		result();
		
		
	}
	
	public void result() throws IOException {
		
		FXMLLoader loader=new FXMLLoader(getClass().getResource(Mode.Result.getPath()));
		Parent root=loader.load();
		
		Result resultController=loader.getController();
		resultController.getMatrix(power);
		resultController.assignMatrix(80,30);
		
		Scene scene=new Scene(root);
		Stage stage=new Stage();
		stage.setScene(scene);
		stage.setTitle("Result of Matrix Tranpose");
		stage.show();
		

	}	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
		
		SpinnerValueFactory<Integer>valueFactory1=new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100,initialValue);
		
		
		SpinnerValueFactory<Integer>valueFactory2=new SpinnerValueFactory.IntegerSpinnerValueFactory(1,20,initialValue);
		
		spinnerM.setValueFactory(valueFactory1);
		spinnerM.setEditable(true);
		currentValueM = (Integer) spinnerM.getValue();
		
		spinnerN.setValueFactory(valueFactory2);
		spinnerN.setEditable(true);
		currentValueN = (Integer) spinnerN.getValue();
		
		
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

