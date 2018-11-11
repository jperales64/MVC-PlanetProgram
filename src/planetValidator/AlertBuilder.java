package planetValidator;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.stage.Window;

public class AlertBuilder {
	
	public void validationAlert(ValidationError error){
    	Alert errorAlert = new Alert(AlertType.ERROR);
		errorAlert.setTitle("Invalid Entry");
		errorAlert.setHeaderText(null);
		if(error.equals(ValidationError.NAME_LENGTH)) {
			errorAlert.setContentText("The planet name should be between 0 and 256 characters.");		
		}else if (error.equals(ValidationError.NAME_REGEX)) {
			errorAlert.setContentText("You can only use the following characters: "
					+ "A-Z, a-z, 0-9, spaces, hyphens, periods");
		}else if(error.equals(ValidationError.DIAMETER_RANGE)) {
			errorAlert.setContentText("Diameter should be between 0 and 200,000 meters.");
		}else if(error.equals(ValidationError.TEMPERATURE_RANGE)) {
			errorAlert.setContentText("Temperature should be between -273.15 C and 500.00 C.");
		}else if(error.equals(ValidationError.MOON_NUMBER_RANGE)) {
			errorAlert.setContentText("Number of moons cannot be less than 0 or greater than 1000.");
		}else if (error.equals(ValidationError.EMPTY_FIELD)) {
			errorAlert.setContentText("Fields cannot be left empty.");
		}else { return; }
		errorAlert.showAndWait();
	}
	
	public void saveAlert(Boolean saveStatus) {
		Alert saveAlert = new Alert(AlertType.INFORMATION);
		saveAlert.setHeaderText(null);
		
		if (saveStatus) {
			saveAlert.setTitle("Save Successful");
			saveAlert.setContentText("Your planet has been saved!");
		}else {
			saveAlert.setTitle("Save Error!");
			saveAlert.setContentText("An error occured while trying to save.");
		}
		saveAlert.showAndWait();
	}
	
	public boolean loadAlert() {
		boolean cancelLoad = true;
		
        ButtonType okButton = new ButtonType("Ok");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        Alert loadAlert = new Alert(Alert.AlertType.CONFIRMATION,"",okButton,cancelButton);
        loadAlert.setHeaderText(null);
        loadAlert.setContentText("Do you want to overwrite your work?");
        Window window = loadAlert.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(e -> loadAlert.hide());
        Optional<ButtonType> result = loadAlert.showAndWait();
        
        if (result.get() == okButton)
        	cancelLoad = false;

		return cancelLoad;
	}
}