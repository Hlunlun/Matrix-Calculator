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


public class TransposeController implements Initializable,EventHandler<KeyEvent>{
	
	@FXML
	private Spinner<Integer> spinnerN;
	
	@FXML
	private Spinner<Integer> spinnerM;
	
	@FXML
	private Label n;
	
	@FXML
	private Label m;
	
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
		
		n.setText(Integer.toString(currentValueN));
		m.setText(Integer.toString(currentValueM));		
		
		clearRowColumn();
		grid.getChildren().clear();
		grid.setAlignment(Pos.CENTER);		
		grid.setGridLinesVisible(true);
		
		input=new TextField[currentValueN][currentValueM];		
		
		matrix=new BigDecimal[currentValueM][currentValueN];
		
		for(int i=0;i<currentValueN;i++) {
			for(int j=0;j<currentValueM;j++) {				
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
						//alert.showAndWait();
						break;
					}
					else {
						matrix[j][i]=new BigDecimal(input[i][j].getText());		
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
		
		
		//getOnInputMethodsTextChanged
		//https://vimsky.com/zh-tw/examples/usage/javafx-textfield.html
		//http://cr.openjdk.java.net/~leifs/rt34620/webrev.02/modules/controls/src/main/java/com/sun/javafx/scene/control/skin/ComboBoxPopupControl.java.cdiff.html
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
		
		calculateBtn.setDisable(true);
		result();
		
		
	}
	
	public void result() throws IOException {
		
		FXMLLoader loader=new FXMLLoader(getClass().getResource(Mode.Result.getPath()));
		Parent root=loader.load();
		
		Result resultController=loader.getController();
		//why can't just code like this?
		//Result resultController=new Result();
		//because the resultConroller actually control the Result.fxml 
		//instead of this Transpose.fxml
		
		resultController.getMatrix(matrix);
		resultController.assignMatrix(40,30);
		
		Scene scene=new Scene(root);
		Stage stage=new Stage();
		stage.setScene(scene);
		stage.setTitle("Result of Matrix Tranpose");
		stage.show();
		

	}	
	
	/////可以用在Inverse matrix
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
		
		SpinnerValueFactory<Integer>valueFactory1=new SpinnerValueFactory.IntegerSpinnerValueFactory(1,20,initialValue);
		
		///如果要用在inverse ，這個可以甭加
		SpinnerValueFactory<Integer>valueFactory2=new SpinnerValueFactory.IntegerSpinnerValueFactory(1,20,initialValue);
		
		//valueFactory.setValue(1);
		
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
				
				n.setText(Integer.toString(currentValueN));
			}
		});
		
		spinnerM.valueProperty().addListener(new ChangeListener<Integer>() {
			
			@Override
			public void changed(ObservableValue<? extends Integer> arg0, Integer arg1, Integer arg2) {
				currentValueM=(Integer) spinnerM.getValue();
				
				m.setText(Integer.toString(currentValueM));
				
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

//https://matrix.reshish.com/multCalculation.php	

//file:///C:/%E6%88%90%E5%A4%A7/%E4%BA%8C%E4%B8%8B/%E7%A8%8B%E8%A8%AD%E4%BA%8C/%E8%AA%B2%E5%A0%82%E8%AC%9B%E7%BE%A9/JavaFX/JavaFX-Part2.pdf 
//p21

//add textfield 
//https://www.javatpoint.com/javafx-textfield
//https://www.tutorialkart.com/javafx/javafx-textfield/