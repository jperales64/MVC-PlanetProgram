package planet.detail;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.NumberFormat;
import java.text.ParseException;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import planetValidator.AlertBuilder;
import planetValidator.PlanetValidator;
import planetValidator.ValidationError;
import unitConverter.UnitConverter;

public class PlanetController {
	private PlanetLoader planetLoader = new PlanetLoader();
	private PlanetSaver planetSaver = new PlanetSaver();
	private Planet planet = new Planet();
	public UnitConverter unitConverter = new UnitConverter();
	private PlanetDirector planetDirector = new PlanetDirector();
	private PlanetValidator planetValidator = new PlanetValidator();
    private NumberFormat numberFormat = NumberFormat.getNumberInstance();
    private FileChooser fileChooser = new FileChooser();
    private AlertBuilder alert = new AlertBuilder();
	
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
	@FXML private Button loadButton;

	public PlanetController() {}

	@FXML 
	void selectImage(ActionEvent event) {
		fileChooser.setInitialDirectory(new File("images"));
		File planetImage = fileChooser.showOpenDialog(loadButton.getScene().getWindow());
		try {
			planet.setPlanetImg("images/" + planetImage.getName()); 
		}catch (Exception e) {
			System.out.println("Failed to load image");
		}
		setPlanetImage(planet);
	}
	
	@FXML
	public void loadPlanet() {	
		PlanetFileParser pfParser = new PlanetFileParser();
		fileChooser.setInitialDirectory(new File("saved_planets"));
		File planetFile = fileChooser.showOpenDialog(loadButton.getScene().getWindow());
		
		try {
			planet = pfParser.createPlanetFromFile(planetFile.getAbsolutePath()); 
		}catch (Exception e) {}
		
		setTextFields(planet);
		setPlanetImage(planet);
	}
	
	public Planet buildPlanetFromFields(){
		
		String nameOfPlanet = planetName.getText();
		Double diameter = Double.parseDouble(planetDiameterKM.getText());
		Double temp = Double.parseDouble(planetMeanSurfaceTempC.getText());
		int numbOfMoons = Integer.parseInt(planetNumberOfMoons.getText());
		//TODO: set up select image to determine this.
		String planetImage = planet.getPlanetImg();
		planetDirector.makePlanet(nameOfPlanet, diameter, numbOfMoons, temp, planetImage);
		return planetDirector.getPlanet();
	}

	@FXML
	public void savePlanet() {
		planet = buildPlanetFromFields();
		boolean result = planetValidator.validatePlanet(planet);
		alert.validationAlert(planetValidator.getValidationError(planet));

		if (result) {
			try {
				Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("saved_planets/" + planet.getName()+".txt")));
				writer.write(planet.toString());
				writer.close();
				alert.saveAlert(true);
			}catch (Exception e) {
				alert.saveAlert(false);
			}
		}
	}

	void setTextFields(Planet planet)  {
		//TODO: create an outputString method in unitConverter to save space.
		String planetDiameterInKilometers;
		String planetDiameterInMiles = "";
		String planetTempInCelcius;
		String planetTempInFahrenheit = "";
		saveButton.setDisable(false);
		
		planetName.setText(planet.getName());

		planetDiameterInKilometers = Double.toString(planet.getDiameter());
		planetDiameterKM.setText(planetDiameterInKilometers);
		
		planetDiameterInMiles = Double.toString(unitConverter.kilometerToMile(planet.getDiameter()));
		planetDiameterM.setText(planetDiameterInMiles);
		
		planetTempInCelcius = Double.toString(planet.getTemperature());
		planetMeanSurfaceTempC.setText(planetTempInCelcius);
		
		planetTempInFahrenheit = Double.toString(unitConverter.celciusToFahrenheit(planet.getTemperature()));
		
		planetMeanSurfaceTempF.setText(planetTempInFahrenheit);
		
		planetNumberOfMoons.setText(Integer.toString(planet.getNumberOfMoons()));
		
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
	        	planetDiameterM.setText("");
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
		
		fancyPlanetName.textProperty().bind(planetName.textProperty());
		planetDiameterKM.textProperty().addListener(fromKilometers);
		planetMeanSurfaceTempC.textProperty().addListener(fromCelcius);
		planetName.textProperty().addListener(nothingToSave);
		
		setTextFields(planet);
		setPlanetImage(planet);

	}

}