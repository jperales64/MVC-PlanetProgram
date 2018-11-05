package app;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import planet.detail.Planet;
import planet.detail.PlanetController;
import planet.detail.PlanetDirector;

public class AppMain extends Application {
	public AppMain() {
	}
	
	public static void main(String[] args) {
		
		//launch(args);
		PlanetDirector pDir = new PlanetDirector();
		pDir.makePlanet();
		Planet planet = pDir.getPlanet();
		
		System.out.println("Planet Built");
		System.out.println("Planet Name: " + planet.getName());
		System.out.println("Planet Diameter" + planet.getDiameter());
		System.out.println("Planet Temp: " + planet.getTemp());
	}
	
	//FXML startup method
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