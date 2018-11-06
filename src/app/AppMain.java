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
import planet.detail.PlanetLoader;
import planet.detail.PlanetSaver;

public class AppMain extends Application {
	public AppMain() {
	}
	
	public static void main(String[] args) {
		launch(args);
		/*
		String filename = "c:\\temp\\Neptune.ser";
		PlanetLoader pLoader = new PlanetLoader();
		PlanetSaver pSaver = new PlanetSaver();
		PlanetDirector pDir = new PlanetDirector();
		pDir.makePlanet("Neptune", 12.3, 13, 50.0, "images/neptune.png");
		Planet planet = pDir.getPlanet();
		
		pSaver.serializePlanet(planet);
		Planet loadedPlanet = pLoader.deserialzeAddress(filename);
		System.out.println(loadedPlanet.getPlanetImg());
		*/
		
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