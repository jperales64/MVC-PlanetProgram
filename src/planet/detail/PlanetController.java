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
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
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
import javafx.stage.FileChooser;
import javafx.util.converter.DateTimeStringConverter;
import javafx.util.converter.DoubleStringConverter;
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
    private DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
	
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
		String diamterStr = planetDiameterKM.getText();
		diamterStr = diamterStr.replace(",", "");
		Double diameter = Double.parseDouble(diamterStr);
		Double temp = Double.parseDouble(planetMeanSurfaceTempC.getText());
		int numbOfMoons = Integer.parseInt(planetNumberOfMoons.getText());
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
		String planetDiameterInKilometers = "";
		String planetDiameterInMiles = "";
		String planetTempInCelcius = "";
		String planetTempInFahrenheit = "";
		saveButton.setDisable(false);
		
		planetName.setText(planet.getName());
		
		if (planetName.getText().equals("")) {
			saveButton.setDisable(true);
		}
		
		if (planetValidator.validateDiameter(planet.getDiameter()) != ValidationError.DIAMETER_RANGE) {
			planetDiameterInKilometers = Double.toString(planet.getDiameter());
			planetDiameterInMiles = Double.toString(unitConverter.kilometerToMile(planet.getDiameter()));
		}
		planetDiameterKM.setText(NumberFormat.getNumberInstance(Locale.US).format(planet.getDiameter()));
		planetDiameterM.setText(NumberFormat.getNumberInstance(Locale.US).format(unitConverter.kilometerToMile(planet.getDiameter())));
		
		if (planetValidator.validateTemp(planet.getTemperature()) != ValidationError.TEMPERATURE_RANGE) {
			planetTempInCelcius = Double.toString(planet.getTemperature());
			planetTempInFahrenheit = Double.toString(unitConverter.celciusToFahrenheit(planet.getTemperature()));
		}
		planetMeanSurfaceTempC.setText(planetTempInCelcius);
		planetMeanSurfaceTempF.setText(planetTempInFahrenheit);
		
		if (planetValidator.validateNumOfMoons(planet.getNumberOfMoons()) != ValidationError.MOON_NUMBER_RANGE) 
			planetNumberOfMoons.setText(Integer.toString(planet.getNumberOfMoons()));
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
        	String formattedDiameter;
			try {
				n = numberFormat.parse(planetDiameterKM.getText());
	        	this.planet.setDiameter(n.doubleValue());
	        	diameterInMiles = unitConverter.kilometerToMile(n.doubleValue());
	        	formattedDiameter = decimalFormat.format(diameterInMiles);
	       
	        	planetDiameterM.setText(formattedDiameter);

			} catch (ParseException e) {
	        	planetDiameterM.setText("");
			}
        }
    };
    
	private InvalidationListener fromCelcius = (Observable o) -> {
        if (planetMeanSurfaceTempC.isFocused()) {
        	Number n;
        	double tempInFahrenheit;
        	String formattedTemp;
			try {
				n = numberFormat.parse(planetMeanSurfaceTempC.getText());
	        	this.planet.setTemperature(n.doubleValue());
	        	tempInFahrenheit = unitConverter.celciusToFahrenheit(n.doubleValue());
	        	formattedTemp = decimalFormat.format(tempInFahrenheit);
	        	planetMeanSurfaceTempF.setText(formattedTemp);
			} catch (ParseException e) {
	        	planetMeanSurfaceTempF.setText("");

			}
        }
    };
    
//	private InvalidationListener formatNumber = (Observable o) -> {
//
//        double formattedNumber;
//		if (planetDiameterKM.isFocused()) {
//			try {
//        	formattedNumber = Double.parseDouble(planetDiameterM.getText());
//			}catch (Exception er) {
//				System.out.println("Nice try");
//			}
//        	
//        } else if (planetMeanSurfaceTempC.isFocused()) {
//
//        	Number n;
//        	double tempInFahrenheit;
//			try {
//				n = numberFormat.parse(planetMeanSurfaceTempC.getText());
//	        	this.planet.setTemperature(n.doubleValue());
//	        	tempInFahrenheit = unitConverter.celciusToFahrenheit(n.doubleValue());
//	        	planetMeanSurfaceTempF.setText(Double.toString(tempInFahrenheit));
//			} catch (ParseException e) {
//			}
//        }
//    };
    
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
	
		planetName.textProperty().addListener(nothingToSave);
		
//		planetDiameterKM.setOnKeyPressed(new EventHandler<KeyEvent>()
//	    {
//	        @Override
//	        public void handle(KeyEvent ke)
//	        {
//	            if (ke.getCode().equals(KeyCode.ENTER))
//	            {
//	                String text = planetDiameterKM.getText();
//	                planetDiameterKM.setText(NumberFormat.getNumberInstance(Locale.US).format(Double.valueOf(text)));
//	            }
//	        }
//	    });
		
		//planetDiameterKM.setText(NumberFormat.getNumberInstance(Locale.US).format(planet.getDiameter()));
		//planetDiameterKM.textProperty().addListener(formatNumber);
		
		setTextFields(planet);
		setPlanetImage(planet);

	}
	public void onEnter(ActionEvent ae){
		   System.out.println("test") ;
		}
	
	

}