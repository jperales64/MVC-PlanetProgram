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
	final JFileChooser fc = new JFileChooser("c:\\temp\\");
	private Planet planet;
	@FXML
	private ImageView planetImage;

	@FXML
	private Button selectImageButton;

	@FXML
	private TextField planetName;

	@FXML
	private TextField planetDiameterKM;

	@FXML
	private TextField planetDiameterM;

	@FXML
	private TextField planetMeanSurfaceTempC;

	@FXML
	private TextField planetMeanSurfaceTempF;

	@FXML
	private TextField planetNumberOfMoons;

	@FXML
	private Label fancyPlanetName;

	public PlanetController() {
		planet = new Planet();
		planetImage = new ImageView();
		selectImageButton = new Button();
		planetName = new TextField();
		planetDiameterKM = new TextField();
		planetDiameterM = new TextField();
		planetMeanSurfaceTempC = new TextField();
		planetMeanSurfaceTempF = new TextField();
		planetNumberOfMoons = new TextField();
		fancyPlanetName = new Label();

	}

	@FXML
	void selectImage(ActionEvent event) {
	}

	@FXML
	void loadPlanet(ActionEvent event) {
		String fileName;

		// invoke the showsOpenDialog function to show the save dialog
		int r = fc.showOpenDialog(null);

		// if the user selects a file
		if (r == JFileChooser.APPROVE_OPTION)

		{
			// set the label to the path of the selected file
			fileName = fc.getSelectedFile().getAbsolutePath();
			planet = planetLoader.deserialzeAddress(fileName);
			try {
				setTextFields(planet);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@FXML
	void savePlanet(ActionEvent event) {
		fc.showSaveDialog(null);
	}

	void setTextFields(Planet planet) throws FileNotFoundException {
		planetName.setText(planet.getName());
		planetDiameterKM.setText(planet.getDiameter().toString());
		planetMeanSurfaceTempC.setText(planet.getTemp().toString());
		planetNumberOfMoons.setText(Integer.toString(planet.getNumbOfMoons()));
		FileInputStream input = new FileInputStream(planet.getPlanetImg());
		Image image = new Image(input);
		planetImage.setImage(image);
		//System.out.println(planet.getPlanetImg());
	}

}