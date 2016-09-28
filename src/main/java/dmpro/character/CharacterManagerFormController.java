package dmpro.character;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import sun.applet.Main;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class CharacterManagerFormController extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			BorderPane page = (BorderPane) FXMLLoader.load(Main.class.getResource("./TestCharacterManagerForm.fxml"));
            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            primaryStage.show();
		}   catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
