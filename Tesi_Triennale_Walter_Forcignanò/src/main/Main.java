package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Model;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		// TODO Auto-generated method stub
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("base_Interface.fxml"));
			BorderPane root = (BorderPane) loader.load();
			Scene scene = new Scene(root);
			Controller controller = (Controller) loader.getController();
			Model model = new Model();
			controller.setModel(model);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
