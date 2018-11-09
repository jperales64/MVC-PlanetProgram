package planet.detail;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFileChooser;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import planetValidator.PlanetValidator;
import unitConverter.UnitConverter;

public class PlanetController {
	private PlanetLoader planetLoader = new PlanetLoader();
	private PlanetSaver planetSaver = new PlanetSaver();
	//final JFileChooser planetFileChooser = new JFileChooser("c:\\temp\\");
	private Planet planet = new Planet();
	public UnitConverter unitConverter = new UnitConverter();
	private PlanetDirector planetDirector = new PlanetDirector();
	private PlanetValidator planetValidator = new PlanetValidator();
	private Desktop desktop = Desktop.getDesktop();


	
	
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
	
    private NumberFormat numberFormat = NumberFormat.getNumberInstance();
    private FileChooser fileChooser = new FileChooser();

	public PlanetController() {
	}

	@FXML
	void selectImage(ActionEvent event) {
		File imageFolder = new File("images");
		try {
			Desktop.getDesktop().browse(imageFolder.toURI());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    @FXML public void handleButtonAction(ActionEvent event) throws ParseException {
        if(event.getSource() == loadButton) {
        	loadPlanet();
        }else if (event.getSource() == saveButton) {
        	savePlanet();
        }//else if (event.getSource() == selectImageButton) {
        	//selectImage();
        //}
    }
	
	public void loadPlanet() {	
		PlanetFileParser pfParser = new PlanetFileParser();
		fileChooser.setInitialDirectory(new File("saved_planets"));
		File planetFile = fileChooser.showOpenDialog(loadButton.getScene().getWindow());
		System.out.println(pfParser.createPlanetFromFile(planetFile.getAbsolutePath()));
		planet = pfParser.createPlanetFromFile(planetFile.getAbsolutePath());
		setTextFields(planet);
		setPlanetImage(planet);
	}
	
	public Planet buildPlanetFromFields(){
		String nameOfPlanet = planetName.getText();
		Double diameter = Double.parseDouble(planetDiameterKM.getText());
		Double temp = Double.parseDouble(planetMeanSurfaceTempC.getText());
		int numbOfMoons = Integer.parseInt(planetNumberOfMoons.getText());
		String planetImage = "images/" + nameOfPlanet.toLowerCase() + ".png";
		planetDirector.makePlanet(nameOfPlanet, diameter, numbOfMoons, temp, planetImage);
		return planetDirector.getPlanet();
		
	}

	public void savePlanet() {
		planet = buildPlanetFromFields();
		//check for valid planet info here. will throw Alert if not correct.
//		boolean result = planetValidator.validatePlanet(planet);
//		if(!result){
//			
//		}
		System.out.println(planet);
		try {
			String planetLines[] = planet.toString().split("\\r?\\n");
			List<String> planetDetails = Arrays.asList(planetLines);
			Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("saved_planets/" + planet.getName())));
			writer.write(planet.toString());
		}catch (Exception e) {
			System.out.println("Could not save.");
		}
	}

	void setTextFields(Planet planet)  {
		//TODO: create an outputString method in unitConverter to save space.
		String planetDiameterInKilometers;
		String planetDiameterInMiles;
		String planetTempInCelcius;
		String planetTempInFahrenheit;
		
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
		
		//loadButton.setDefaultButton(true);
		setTextFields(planet);
		setPlanetImage(planet);
				
		planetDiameterKM.textProperty().addListener(fromKilometers);
		planetMeanSurfaceTempC.textProperty().addListener(fromCelcius);
		planetName.textProperty().addListener(nothingToSave);
		
		fancyPlanetName.textProperty().bind(planetName.textProperty());

	}

}