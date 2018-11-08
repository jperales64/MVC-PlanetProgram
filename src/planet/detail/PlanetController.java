package planet.detail;

import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.JFileChooser;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.converter.DoubleStringConverter;
import planetValidator.PlanetValidator;
import unitConverter.UnitConverter;

public class PlanetController {
	private PlanetLoader planetLoader = new PlanetLoader();
	private PlanetSaver planetSaver = new PlanetSaver();
	final JFileChooser planetFileChooser = new JFileChooser("c:\\temp\\");
	private Planet planet = new Planet();
	public UnitConverter unitConverter = new UnitConverter();
	private PlanetDirector planetDirector = new PlanetDirector();
	private PlanetValidator planetValidator = new PlanetValidator();
	
	
	@FXML private ImageView planetImage;
	@FXML private Button selectImageButton;
	@FXML private TextField planetName;
	@FXML private TextField planetDiameterKM;
	@FXML private TextField planetDiameterM;
	@FXML private TextField planetMeanSurfaceTempC;
	@FXML private TextField planetMeanSurfaceTempF;
	@FXML private TextField planetNumberOfMoons;
	@FXML private Label fancyPlanetName;
	
    private NumberFormat numberFormat = NumberFormat.getNumberInstance();
    private DoubleProperty meters;

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
	
	Planet buildPlanetFromTextFields(){
		String nameOfPlanet = planetName.getText();
		Double diameter = Double.parseDouble(planetDiameterKM.getText());
		Double temp = Double.parseDouble(planetMeanSurfaceTempC.getText());
		int numbOfMoons = Integer.parseInt(planetNumberOfMoons.getText());
		planetDirector.makePlanet(nameOfPlanet, diameter, numbOfMoons, temp, null);
		return planetDirector.getPlanet();
		
	}

	@FXML
	void savePlanet(ActionEvent event) {
		//Use the text in the text fields to build a planet and save it
		planet = buildPlanetFromTextFields();
		//IDk how to handle an Invalid planet GUI wise
		boolean result = planetValidator.validatePlanet(planet);
		if(!result){
			
		}
		String filename = "c:\\temp\\" + planet.getPlanetName() + ".ser";
		planetFileChooser.showSaveDialog(null);
		planetFileChooser.grabFocus();
	}

	void setTextFields(Planet planet)  {
		planetName.setText(planet.getPlanetName());
		planetDiameterKM.setText(Double.toString(planet.getDiameter()));
		planetMeanSurfaceTempC.setText(Double.toString(planet.getTemperature()));
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
	
    private InvalidationListener toKilometers = (Observable o) -> {
        if (!planetDiameterKM.isFocused()) {
        	System.out.println(planetDiameterM.getText());
        }
    };
	
	private InvalidationListener fromKilometers = (Observable o) -> {
        if (planetDiameterKM.isFocused()) {
        	Number n;
        	double diameterInMiles;
        	UnitConverter unitConverter = new UnitConverter();
			try {
				n = numberFormat.parse(planetDiameterKM.getText());
	        	this.planet.setDiameter(n.doubleValue());
	        	diameterInMiles = unitConverter.kilometerToMile(n.doubleValue());
	        	planetDiameterM.setText(Double.toString(diameterInMiles));
			} catch (ParseException e) {
			}
        }
    };
	
	public void initialize() {
		
		Planet defaultPlanet = new Planet();
		
		
		
		//StringProperty diameter = new SimpleStringProperty();
		
		planetDiameterKM.textProperty().addListener(fromKilometers);
		planetDiameterM.textProperty().addListener(toKilometers);
		//fromKilometers.invalidated(null);
		//planetDiameterKM.textProperty().bind(diameter);
		
		//planet.setDiameter(Double.parseDouble(planetDiameterKM.getText()));
		//UnitConverter unitConverter = new UnitConverter();
		
		
		fancyPlanetName.textProperty().bind(planetName.textProperty());
		//planetDiameterM.textProperty().bind(planetDiameterKM.textProperty());
		planetMeanSurfaceTempF.textProperty().bind(planetMeanSurfaceTempC.textProperty());
		

		setTextFields(defaultPlanet);
		setPlanetImage(defaultPlanet);
	}

}