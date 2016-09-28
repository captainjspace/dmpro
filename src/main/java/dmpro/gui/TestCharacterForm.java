package dmpro.gui;


import dmpro.character.Character;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class TestCharacterForm extends Application {
	Character c= new Character();
	
	public void start(Stage characterStage){
		
		characterStage.setTitle("AD&D Character Form");
		GridPane form = new GridPane();
		form.setAlignment(Pos.CENTER);
		form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(25, 25, 25, 25));
        Scene scene = new Scene(form);
        
        Text sceneTitle = new Text("Character Form");
        sceneTitle.setFont(Font.font("Arial", FontWeight.NORMAL,20));
        form.add(sceneTitle, 0, 0, 2, 1);
        
        Label id = new Label("Character ID:");
        form.add(id, 0, 1);
        final TextField idField = new TextField();
        form.add(idField, 1, 1);
        
        Label name = new Label("Character name:");
        form.add(name,0,2);
        final TextField nameField = new TextField();
        form.add(nameField, 1, 2);
        
        Label prefix = new Label("prefix:");
        form.add(prefix,0,3);
        final TextField prefixField = new TextField();
        form.add(prefixField, 1, 3);
        
        Label firstName = new Label("Character firstName:");
        form.add(firstName,0,4);
        final TextField firstNameField = new TextField();
        form.add(firstNameField, 1, 4);
        
        Label lastName = new Label("Character lastName:");
        form.add(lastName,0,5);
        final TextField lastNameField = new TextField();
        form.add(lastNameField, 1, 5);
        
        Label title = new Label("Character title:");
        form.add(title,0,6);
        final TextField titleField = new TextField();
        form.add(titleField, 1, 6);
        		
        Label age = new Label("Character age:");
        form.add(age,0,7);
        final TextField ageField = new TextField();
        form.add(ageField, 1, 7);
        
        Label height = new Label("Character height:");
        form.add(height,0,8);
        final TextField heightField = new TextField();
        form.add(heightField, 1, 8);
        
        Label weight = new Label("Character weight:");
        form.add(weight,0,9);
        final TextField weightField = new TextField();
        form.add(weightField, 1, 9);
        
        Label sex = new Label("Character sex:");
        form.add(sex,0,10);
        final TextField sexField = new TextField();
        form.add(sexField, 1, 10);
        
        
        characterStage.setScene(scene);
        characterStage.show();
        
	}

	public static void main(String[] args) {
        launch(args);
    }
	
}
