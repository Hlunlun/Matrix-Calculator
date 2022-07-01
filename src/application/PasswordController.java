package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class PasswordController implements EventHandler<KeyEvent>{
	
	@FXML
	private PasswordField passwordField; 
	@FXML	
	private Button submitButton;
	@FXML
	private Label passwrordLabel;
	
	String password="";
	String key="lQkEXJsNTiMKPFutOpbaImDoxvjRydwVLBUqWfzCcenYgHSrGZhA";
	String pass=key.substring(8,11);
	
	SceneController sceneController=new SceneController();	
	
	public void forcusControl() {
		passwordField.requestFocus();
	}
	
	public void submit(ActionEvent event) throws IOException {
		
		password=passwordField.getText();

		if(password.equals(pass)) {			
			System.out.println(password);
			sceneController.switchScene(event, Mode.Menu.getPath());
		}	
	}

	@Override
	public void handle(KeyEvent event) {
		password=passwordField.getText();
		if (KeyEvent.KEY_PRESSED.equals(event.getEventType())) {
			if(password.equals(pass) && event.getCode()==KeyCode.ENTER) {
				System.out.println(password);		
				try {
					sceneController.switchScene(event, Mode.Menu.getPath());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}				
        } 
		
	}
	
	
	
}


