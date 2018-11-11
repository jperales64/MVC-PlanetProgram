package planet.detail;
import java.io.*;
import java.text.*;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import planetValidator.*;
import unitConverter.UnitConverter;

public class PlanetController{
	private Planet planet = new Planet();
	public UnitConverter unitConverter = new UnitConverter();
	private PlanetDirector planetDirector = new PlanetDirector();
	private PlanetValidator planetValidator = new PlanetValidator();
    private NumberFormat numberFormat = NumberFormat.getNumberInstance();
    private FileChooser fileChooser = new FileChooser();
    private AlertBuilder alert = new AlertBuilder();
	private PlanetSaver planetSaver = new PlanetSaver();
    private DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
    private String planetDiameterInKilometers, planetDiameterInMiles;
    private String planetTempInCelcius, planetTempInFahrenheit, planetNumOfMoons;
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

	public PlanetController() {
	    planetDiameterInKilometers = planetDiameterInMiles = planetTempInCelcius
	    		= planetTempInFahrenheit = planetNumOfMoons = "";
	}

	@FXML void selectImage(ActionEvent event) {
		fileChooser.setInitialDirectory(new File("images"));
		planet.setPlanetImg(fileChooser.showOpenDialog(loadButton.getScene().getWindow()).getName()); 
		setPlanetImage(planet);
	}
	
	@FXML public void loadPlanet(ActionEvent event) {
		PlanetLoader planetLoader = new PlanetLoader();
		if (!checkForEmptyFields())
			if (alert.loadAlert()) return;
		File planetFile = fileChooser.showOpenDialog(loadButton.getScene().getWindow());
		setTextFields(planetLoader.loadPlanet(planetFile));
		setPlanetImage(planetLoader.loadPlanet(planetFile));
	}
	
	private boolean checkForEmptyFields() {
		boolean isEmpty = false;
		if (planetNameField.getText().equals("") && planetDiameterKMField.getText().equals("")
				&& planetMeanSurfaceTempCField.getText().equals("") && planetNumOfMoonsField.getText().equals("")
				&& planet.getPlanetImg().equals("images/no_image.png")) isEmpty = true;
		return isEmpty;
	}

	public Planet buildPlanetFromFields(){		
		String nameOfPlanet = planetNameField.getText();
		double diameter = planetDiameterKMField.getText().equals("") ? planet.getDiameter() 
				: Double.parseDouble(planetDiameterKMField.getText().replaceAll(",", ""));
		double temp = planetMeanSurfaceTempCField.getText().equals("") ? planet.getTemperature()
				: Double.parseDouble(planetMeanSurfaceTempCField.getText());
		int numOfMoons = planetNumOfMoonsField.getText().equals("") ? planet.getNumberOfMoons()
				: Integer.parseInt(planetNumOfMoonsField.getText());
		String planetImage = planet.getPlanetImg();
		planetDirector.makePlanet(nameOfPlanet, diameter, numOfMoons, temp, planetImage);
		
		return planetDirector.getPlanet();
	}

	@FXML public void savePlanet() {
		planet = buildPlanetFromFields();
		planetSaver.savePlanet(planet);
	}

	void setTextFields(Planet planet)  {
		saveButton.setDisable(false);
		setPlanetText();		
		
		planetNameField.setText(planet.getName());
		planetNumOfMoonsField.setText(planetNumOfMoons);
		planetDiameterKMField.setText(planetDiameterInKilometers);
		planetDiameterMiField.setText(planetDiameterInMiles);
		planetMeanSurfaceTempCField.setText(planetTempInCelcius);
		planetMeanSurfaceTempFField.setText(planetTempInFahrenheit);
	}
	
	void setPlanetText() {
		if (planetNameField.getText().equals(""))
			saveButton.setDisable(true);
		if (planetValidator.validateDiameter(planet.getDiameter()) != ValidationError.DIAMETER_RANGE) {
			planetDiameterInKilometers = decimalFormat.format(planet.getDiameter());
			planetDiameterInMiles = decimalFormat.format(unitConverter.kilometerToMile(planet.getDiameter()));
		}
		if (planetValidator.validateTemp(planet.getTemperature()) != ValidationError.TEMPERATURE_RANGE) {
			planetTempInCelcius = decimalFormat.format(planet.getTemperature());
			planetTempInFahrenheit = decimalFormat.format(unitConverter.celciusToFahrenheit(planet.getTemperature()));
		}
		if (planetValidator.validateNumOfMoons(planet.getNumberOfMoons()) != ValidationError.MOON_NUMBER_RANGE)
			planetNumOfMoons = Integer.toString(planet.getNumberOfMoons());
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
			try {
				Number n = numberFormat.parse(planetDiameterKMField.getText());
	        	this.planet.setDiameter(n.doubleValue());
	        	String formattedDiameter = decimalFormat.format(unitConverter.kilometerToMile(n.doubleValue()));
	        	planetDiameterMiField.setText(formattedDiameter);
			} catch (ParseException e) {
	        	planetDiameterMiField.setText("");
			}
        }
    };
    
	private InvalidationListener fromCelcius = (Observable o) -> {
        if (planetMeanSurfaceTempCField.isFocused()) {
			try {
				Number n = numberFormat.parse(planetMeanSurfaceTempCField.getText());
	        	this.planet.setTemperature(n.doubleValue());
	        	String formattedTemp = decimalFormat.format(unitConverter.celciusToFahrenheit(n.doubleValue()));
	        	planetMeanSurfaceTempFField.setText(formattedTemp);
			} catch (ParseException e) {
	        	planetMeanSurfaceTempFField.setText("");
			}
        }
    };
    
	private InvalidationListener nothingToSave = (Observable o) -> {
		if (planetNameField.isFocused()) {
			if (planetNameField.getText().equals(""))
				saveButton.setDisable(true);
			else
				saveButton.setDisable(false);
		}
    };
    
    private ChangeListener<String> formatDiameter = new ChangeListener<String>() {
	    @Override public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	    	if (newValue.contains("."))
	    		planetDiameterKMField.setText(newValue);
	    	 else
	    		planetDiameterKMField.setText(decimalFormat.format(Double.parseDouble(newValue.replaceAll(",", ""))));
	    }
	};
	
    private ChangeListener<String> formatTemp = new ChangeListener<String>() {
	    @Override public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	    	if (newValue.contains("."))
	    		planetMeanSurfaceTempCField.setText(newValue);
	    	else
	    		planetMeanSurfaceTempCField.setText(decimalFormat.format(Double.parseDouble(newValue.replaceAll(",", ""))));
	    }
	};
	
    private ChangeListener<String> formatNumOfMoons = new ChangeListener<String>() {
	    @Override public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	    	planetNumOfMoonsField.setText(decimalFormat.format(Long.parseLong(newValue.replaceAll(",", ""))));
	    }
	};
    
	public void initialize() {
		fancyPlanetNameLabel.textProperty().bind(planetNameField.textProperty());
		planetDiameterKMField.textProperty().addListener(fromKilometers);
		planetDiameterKMField.textProperty().addListener(formatDiameter);
		planetMeanSurfaceTempCField.textProperty().addListener(fromCelcius);
		planetMeanSurfaceTempCField.textProperty().addListener(formatTemp);
		planetNumOfMoonsField.textProperty().addListener(formatNumOfMoons);
		planetNameField.textProperty().addListener(nothingToSave);
		setTextFields(planet);
		setPlanetImage(planet);
	}
}