package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class MenuController implements EventHandler<KeyEvent>{
	
	@FXML
	private RadioButton radioBtn1;
	@FXML
	private RadioButton radioBtn2;
	@FXML	
	private RadioButton radioBtn3;
	@FXML
	private RadioButton radioBtn4;
	@FXML
	private RadioButton radioBtn5;	
	@FXML
	private RadioButton radioBtn6;
	@FXML
	private Button nextBtn;
		
	SceneController sceneController=new SceneController();
	
	public void change(ActionEvent event) throws IOException {
		
		if(radioBtn1.isSelected()) {			
			sceneController.switchScene(event, Mode.Power.getPath());
		}
		if(radioBtn2.isSelected()) {			
			sceneController.switchScene(event, Mode.Transpose.getPath());
		}
		if(radioBtn3.isSelected()) {			
			sceneController.switchScene(event, Mode.Multiple.getPath());
		}
		if(radioBtn4.isSelected()) {			
			sceneController.switchScene(event, Mode.AddSubstract.getPath());
		}
		if(radioBtn5.isSelected()) {			
			sceneController.switchScene(event, Mode.Inverse.getPath());
		}
		if(radioBtn6.isSelected()) {			
			sceneController.switchScene(event, Mode.Determinant.getPath());
		}
				
	}
	
	@Override
	public void handle(KeyEvent event) {		
		if (KeyEvent.KEY_PRESSED.equals(event.getEventType())) {
			if(radioBtn2.isSelected() ) {
				if(event.getCode()==KeyCode.ENTER) {
					System.out.println(event.getCode());
					try {
						System.out.println(event.getCode());
						sceneController.switchScene(event, Mode.Transpose.getPath());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}						
					
				}
				
			}	
			if(radioBtn5.isSelected() ) {
				if(event.getCode()==KeyCode.ENTER) {
					System.out.println(event.getCode());
					try {
						System.out.println(event.getCode());
						sceneController.switchScene(event, Mode.Inverse.getPath());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}						
					
				}
				
			}
        } 
		
	}
		
}
