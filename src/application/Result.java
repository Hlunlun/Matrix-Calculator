package application;

import java.math.BigDecimal;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Result {
	
	@FXML
	private GridPane grid;	
		
	private BigDecimal [][]matrix;	
	
	public void getMatrix(BigDecimal[][]m) {
		
		matrix=m;
	}
	
	public void assignMatrix(double width,double height) {		
				
		clearRowColumn();
		grid.getChildren().clear();
		grid.setAlignment(Pos.CENTER);
		for(int i=0;i<matrix.length;i++) {
			
			for(int j=0;j<matrix[0].length;j++) {
				
				String element=matrix[i][j].toString();
								
				Label label=new Label(element);	
				
				label.setPrefSize(width, height);
				grid.add(label, j+1, i+1);
			}
			
		}
		
		Label blank=new Label();					
		blank.setPrefSize(width, height);
		grid.add( blank, 0,0);
		
		for(int i=0;i<matrix[0].length;i++) {			
			
			Label label=new Label("A"+Integer.toString(i+1));					
			label.setPrefSize(width, height);
			grid.add(label,i+1 , 0);			
			
		}

		for(int i=0;i<matrix.length;i++) {
			
			Label label=new Label("B"+Integer.toString(i+1));					
			label.setPrefSize(width, height);
			grid.add(label, 0,i+1);
				
		}
				
	}
	
	public void calDeterminant() {
		
		Determinant determinant=new Determinant(matrix);
		BigDecimal det=determinant.calculateDeterminant();
		
		String element=det.toString();
		showAnswer("Determinant of the matrix is "+element);
	}
	
	public void showAnswer(String answer) {
		
		Text text=new Text(answer);
		Font font = Font.font("Comic Sans MS", FontWeight.NORMAL, FontPosture.REGULAR, 20);		
		text.setFont(font);
		text.setFill(Color.WHITE);
		text.setTextAlignment(TextAlignment.CENTER);
		grid.getChildren().add(text);
	}
	
	public void clearRowColumn() {
		while(grid.getRowConstraints().size() > 0){
		    grid.getRowConstraints().remove(0);
		}

		while(grid.getColumnConstraints().size() > 0){
		    grid.getColumnConstraints().remove(0);
		}
	}
	
	
}
