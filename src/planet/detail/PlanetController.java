package planet.detail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.JFileChooser;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
	@FXML private Button saveButton;
	
    private NumberFormat numberFormat = NumberFormat.getNumberInstance();

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
		//TODO: create an outputString method in unitConverter to save space.
		String planetDiameterInKilometers;
		String planetDiameterInMiles;
		String planetTempInCelcius;
		String planetTempInFahrenheit;
		
		planetName.setText(planet.getPlanetName());

		planetDiameterInKilometers = Double.toString(planet.getDiameter());
		planetDiameterKM.setText(planetDiameterInKilometers);
		
		planetDiameterInMiles = Double.toString(unitConverter.kilometerToMile(planet.getDiameter()));
		planetDiameterM.setText(planetDiameterInMiles);
		
		planetTempInCelcius = Double.toString(planet.getTemperature());
		planetMeanSurfaceTempC.setText(planetTempInCelcius);
		
		planetTempInFahrenheit = Double.toString(unitConverter.celciusToFahrenheit(planet.getTemperature()));
		planetMeanSurfaceTempF.setText(planetTempInFahrenheit);
		
		planetNumberOfMoons.setText(Integer.toString(planet.getNumbOfMoons()));
		
		if (planetName.getText().equals("")) {
			saveButton.setDisable(true);
		}
		
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
	
	private InvalidationListener fromKilometers = (Observable o) -> {
        if (planetDiameterKM.isFocused()) {
        	Number n;
        	double diameterInMiles;
			try {
				n = numberFormat.parse(planetDiameterKM.getText());
	        	this.planet.setDiameter(n.doubleValue());
	        	diameterInMiles = unitConverter.kilometerToMile(n.doubleValue());
	        	planetDiameterM.setText(Double.toString(diameterInMiles));
			} catch (ParseException e) {
			}
        }
    };
    
	private InvalidationListener fromCelcius = (Observable o) -> {
        if (planetMeanSurfaceTempC.isFocused()) {
        	Number n;
        	double tempInFahrenheit;
			try {
				n = numberFormat.parse(planetMeanSurfaceTempC.getText());
	        	this.planet.setTemperature(n.doubleValue());
	        	tempInFahrenheit = unitConverter.celciusToFahrenheit(n.doubleValue());
	        	planetMeanSurfaceTempF.setText(Double.toString(tempInFahrenheit));
			} catch (ParseException e) {
			}
        }
    };
    
	private InvalidationListener nothingToSave = (Observable o) -> {
		if (planetName.isFocused()) {
			if (planetName.getText().equals("")) {
				saveButton.setDisable(true);
			}else {
				saveButton.setDisable(false);
			}
		}
    };
	
	public void initialize() {
		
		//Planet defaultPlanet = new Planet();
		
		setTextFields(planet);
		setPlanetImage(planet);
				
		planetDiameterKM.textProperty().addListener(fromKilometers);
		planetMeanSurfaceTempC.textProperty().addListener(fromCelcius);
		planetName.textProperty().addListener(nothingToSave);
		
		fancyPlanetName.textProperty().bind(planetName.textProperty());

	}

}