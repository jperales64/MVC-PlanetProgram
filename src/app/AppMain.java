package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import planet.detail.PlanetController;

public class AppMain extends Application {
	public AppMain() {
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		PlanetController controller = new PlanetController();

		FXMLLoader loader = new FXMLLoader(controller.getClass().getResource("PlanetView.fxml"));
		loader.setController(controller);
		
		Pane pane = (Pane) loader.load();

		Scene scene = new Scene(pane, 590, 400);
		primaryStage.setTitle("CS 4773 Assignment 3");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}