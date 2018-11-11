package planet.detail;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import planetValidator.AlertBuilder;
import planetValidator.PlanetValidator;
import planetValidator.ValidationError;
import unitConverter.UnitConverter;

public class PlanetController{
	private PlanetLoader planetLoader = new PlanetLoader();
	private PlanetSaver planetSaver = new PlanetSaver();
	private Planet planet = new Planet();
	public UnitConverter unitConverter = new UnitConverter();
	private PlanetDirector planetDirector = new PlanetDirector();
	private PlanetValidator planetValidator = new PlanetValidator();
    private NumberFormat numberFormat = NumberFormat.getNumberInstance();
    private FileChooser fileChooser = new FileChooser();
    private AlertBuilder alert = new AlertBuilder();
    private DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
	
	@FXML private ImageView planetImage;
	@FXML private Button selectImageButton;
	@FXML private TextField planetNameField;
	@FXML private TextField planetDiameterKMField;
	@FXML private TextField planetDiameterMiField;
	@FXML private TextField planetMeanSurfaceTempCField;
	@FXML private TextField planetMeanSurfaceTempFField;
	@FXML private TextField planetNumOfMoonsField;
	@FXML private Label fancyPlanetNameLabel;
	@FXML private Button saveButton;
	@FXML private Button loadButton;

	public PlanetController() {}

	@FXML 
	void selectImage(ActionEvent event) {
		fileChooser.setInitialDirectory(new File("images"));
		File planetImage = fileChooser.showOpenDialog(loadButton.getScene().getWindow());
		planet.setPlanetImg(planetImage.getName()); 
		setPlanetImage(planet);
	}
	
	@FXML
	public void loadPlanet() {
		if (!checkForEmptyFields()) {
			if(alert.loadAlert()){
				return;
			}
		}

		PlanetFileParser pfParser = new PlanetFileParser();
		fileChooser.setInitialDirectory(new File("saved_planets"));
		File planetFile = fileChooser.showOpenDialog(loadButton.getScene().getWindow());
		
		try {
			planet = pfParser.createPlanetFromFile(planetFile.getAbsolutePath()); 
		}catch (Exception e) {}
		
		setTextFields(planet);
		setPlanetImage(planet);
	}
	
	private boolean checkForEmptyFields() {
		boolean isEmpty = false;
		if (planetNameField.getText().equals("") && planetDiameterKMField.getText().equals("")
			&& planetMeanSurfaceTempCField.getText().equals("") && planetNumOfMoonsField.getText().equals("")
			&& planet.getPlanetImg().equals("images/no_image.png")) {
			isEmpty = true;
		}
		return isEmpty;
	}

	public Planet buildPlanetFromFields(){
		String nameOfPlanet, planetImage;
		double diameter, temp;
		int numOfMoons;
		
		nameOfPlanet = planetNameField.getText();

		
		if (planetDiameterKMField.getText().equals("")) {
			diameter = planet.getDiameter();
		}else {
			diameter = Double.parseDouble(planetDiameterKMField.getText().replaceAll(",", ""));
		}

		
		if (planetMeanSurfaceTempCField.getText().equals("")) {
			temp = planet.getTemperature();
		}else {
			temp = Double.parseDouble(planetMeanSurfaceTempCField.getText());
		}
		
		if (planetNumOfMoonsField.getText().equals("")) {
			numOfMoons = planet.getNumberOfMoons();
		}else {
			numOfMoons = Integer.parseInt(planetNumOfMoonsField.getText());
		}
		
		planetImage = planet.getPlanetImg();
		
		planetDirector.makePlanet(nameOfPlanet, diameter, numOfMoons, temp, planetImage);
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
		String planetDiameterInKilometers = "";
		String planetDiameterInMiles = "";
		String planetTempInCelcius = "";
		String planetTempInFahrenheit = "";
		String planetNumOfMoons = "";
		saveButton.setDisable(false);
		
		planetNameField.setText(planet.getName());
		
		if (planetNameField.getText().equals("")) {
			saveButton.setDisable(true);
		}
		
		if (planetValidator.validateDiameter(planet.getDiameter()) != ValidationError.DIAMETER_RANGE) {
			planetDiameterInKilometers = decimalFormat.format(planet.getDiameter());
			planetDiameterInMiles = decimalFormat.format(unitConverter.kilometerToMile(planet.getDiameter()));
		}
		planetDiameterKMField.setText(planetDiameterInKilometers);
		planetDiameterMiField.setText(planetDiameterInMiles);
		
		if (planetValidator.validateTemp(planet.getTemperature()) != ValidationError.TEMPERATURE_RANGE) {
			planetTempInCelcius = decimalFormat.format(planet.getTemperature());
			planetTempInFahrenheit = decimalFormat.format(unitConverter.celciusToFahrenheit(planet.getTemperature()));
		}
		planetMeanSurfaceTempCField.setText(planetTempInCelcius);
		planetMeanSurfaceTempFField.setText(planetTempInFahrenheit);
		
		if (planetValidator.validateNumOfMoons(planet.getNumberOfMoons()) != ValidationError.MOON_NUMBER_RANGE) {
			planetNumOfMoons = Integer.toString(planet.getNumberOfMoons());
		}
		planetNumOfMoonsField.setText(planetNumOfMoons);

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
        if (planetDiameterKMField.isFocused()) {
        	Number n;
        	double diameterInMiles;
        	String formattedDiameter;
			try {
				n = numberFormat.parse(planetDiameterKMField.getText());
	        	this.planet.setDiameter(n.doubleValue());
	        	diameterInMiles = unitConverter.kilometerToMile(n.doubleValue());
	        	formattedDiameter = decimalFormat.format(diameterInMiles);
	        	planetDiameterMiField.setText(formattedDiameter);
			} catch (ParseException e) {
	        	planetDiameterMiField.setText("");
			}
        }
    };
    
	private InvalidationListener fromCelcius = (Observable o) -> {
        if (planetMeanSurfaceTempCField.isFocused()) {
        	Number n;
        	double tempInFahrenheit;
        	String formattedTemp;
			try {
				n = numberFormat.parse(planetMeanSurfaceTempCField.getText());
	        	this.planet.setTemperature(n.doubleValue());
	        	tempInFahrenheit = unitConverter.celciusToFahrenheit(n.doubleValue());
	        	formattedTemp = decimalFormat.format(tempInFahrenheit);
	        	planetMeanSurfaceTempFField.setText(formattedTemp);
			} catch (ParseException e) {
	        	planetMeanSurfaceTempFField.setText("");

			}
        }
    };
    
	private InvalidationListener nothingToSave = (Observable o) -> {
		if (planetNameField.isFocused()) {
			if (planetNameField.getText().equals("")) {
				saveButton.setDisable(true);
			}else {
				saveButton.setDisable(false);
			}
		}
    };
    
    private ChangeListener<String> formatDiameter = new ChangeListener<String>() {
	    @Override
	    public void changed(ObservableValue<? extends String> observable, 
	    		String oldValue, String newValue) {
	    	if (newValue.contains(".")) {
	    		planetDiameterKMField.setText(newValue);
	    	} else {
	    		planetDiameterKMField.setText(decimalFormat.format(Double.parseDouble(newValue.replaceAll(",", ""))));
	    	}	    	
	    }
	};
	
    private ChangeListener<String> makeIntField = new ChangeListener<String>() {
	    @Override
	    public void changed(ObservableValue<? extends String> observable, String oldValue, 
	        String newValue) {
	        if (!newValue.matches("\\d*")) {
	            planetNumOfMoonsField.setText(newValue.replaceAll("[^\\d]", ""));
	        }
	    }
	};
    
	public void initialize() {

		fancyPlanetNameLabel.textProperty().bind(planetNameField.textProperty());
		planetDiameterKMField.textProperty().addListener(fromKilometers);
		planetDiameterKMField.textProperty().addListener(formatDiameter);
		planetMeanSurfaceTempCField.textProperty().addListener(fromCelcius);
		planetNumOfMoonsField.textProperty().addListener(makeIntField);
		
		planetNameField.textProperty().addListener(nothingToSave);
		
		setTextFields(planet);
		setPlanetImage(planet);

	}

}