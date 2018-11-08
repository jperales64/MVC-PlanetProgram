package planet.detail;

import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JFileChooser;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class PlanetController {
	private PlanetLoader planetLoader = new PlanetLoader();
	final JFileChooser planetFileChooser = new JFileChooser("c:\\temp\\");
	private Planet planet;
	
	@FXML private ImageView planetImage;
	@FXML private Button selectImageButton;
	@FXML private TextField planetName;
	@FXML private TextField planetDiameterKM;
	@FXML private TextField planetDiameterM;
	@FXML private TextField planetMeanSurfaceTempC;
	@FXML private TextField planetMeanSurfaceTempF;
	@FXML private TextField planetNumberOfMoons;
	@FXML private Label fancyPlanetName;

	public PlanetController() {
	}

	@FXML
	void selectImage(ActionEvent event) {
	}

	@FXML
	void loadPlanet(ActionEvent event) {
		String fileName;

		planetFileChooser.grabFocus();
		int r = planetFileChooser.showOpenDialog(null);
		
	
		if (r == JFileChooser.APPROVE_OPTION)

		{
			
			fileName = planetFileChooser.getSelectedFile().getAbsolutePath();
			planet = planetLoader.loadPlanet(fileName);
			
			setTextFields(planet);
			setPlanetImage(planet);
			
		}
	}

	@FXML
	void savePlanet(ActionEvent event) {
		planetFileChooser.showSaveDialog(null);
		planetFileChooser.grabFocus();
	}

	void setTextFields(Planet planet)  {
		planetName.setText(planet.getName());
		planetDiameterKM.setText(planet.getDiameter().toString());
		planetMeanSurfaceTempC.setText(planet.getTemp().toString());
		planetNumberOfMoons.setText(Integer.toString(planet.getNumbOfMoons()));
		
	}
	void setPlanetImage(Planet planet){
		try {
			FileInputStream input = new FileInputStream(planet.getPlanetImg());
			Image image = new Image(input);
			planetImage.setImage(image);
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void initialize() {
		
		fancyPlanetName.textProperty().bind(planetName.textProperty());
		
		Planet defaultPlanet = new Planet();
		setTextFields(defaultPlanet);
		setPlanetImage(defaultPlanet);
		

	}

}