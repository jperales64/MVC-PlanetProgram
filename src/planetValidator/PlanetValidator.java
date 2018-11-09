package planetValidator;

import planet.detail.Planet;

public class PlanetValidator {
	AlertBuilder alert = new AlertBuilder();

	public ValidationError validateName(String planetName) {
		ValidationError error = ValidationError.NONE;
		
		if (planetName.length() < 1 || planetName.length() > 255) {
			error = ValidationError.NAME_LENGTH;
		}
		else if (!(planetName.matches("^[a-zA-Z0-9-. ]+$"))) {
			error = ValidationError.NAME_REGEX;
		} 
		return error;
	}
	
	public ValidationError validateDiameter(double planetDiameter){
		ValidationError error = ValidationError.NONE;
		
		if(planetDiameter <= 0 || planetDiameter > 200000) {
			error = ValidationError.DIAMETER_RANGE;
		}
		return error;
	}
	
	public ValidationError validateTemp(double planetTemp){
		ValidationError error = ValidationError.NONE;
		
		if(planetTemp < -273.15 || planetTemp > 500.0) {
			error =ValidationError.TEMPERATURE_RANGE;
		}
		return error;
	}
	
	public ValidationError validateNumOfMoons(int numOfMoons){
		ValidationError error = ValidationError.NONE;
		
		if(numOfMoons < 0 || numOfMoons > 1000) {
			error = ValidationError.MOON_NUMBER_RANGE;
		}
		return error;
	}

	public boolean validatePlanet(Planet planet){
		boolean isValid = true;

		if(hasValidationErrors(validateName(planet.getName()), 
				validateDiameter(planet.getDiameter()),
				validateTemp(planet.getTemperature()),
				validateNumOfMoons(planet.getNumberOfMoons()))){
			isValid = false;
		}
		return isValid;
	}
	
	public boolean hasValidationErrors(ValidationError... codes) {
		boolean validationError = false;
		
		for (ValidationError code: codes) {
			if (!(code == ValidationError.NONE)) {
				validationError = true;
				break;
			}
		}
		return validationError;
	}
	
	public ValidationError getValidationError(Planet planet) {
		
		if (validateName(planet.getName()) == ValidationError.NAME_LENGTH){
			return ValidationError.NAME_LENGTH;
		} else if (validateName(planet.getName()) == ValidationError.NAME_REGEX){
			return ValidationError.NAME_REGEX;
		}else if (validateDiameter(planet.getDiameter()) == ValidationError.DIAMETER_RANGE){
			return ValidationError.DIAMETER_RANGE;
		}else if (validateTemp(planet.getTemperature()) == ValidationError.TEMPERATURE_RANGE){
			return ValidationError.TEMPERATURE_RANGE;
		}else if (validateNumOfMoons(planet.getNumberOfMoons()) == ValidationError.MOON_NUMBER_RANGE){
			return ValidationError.MOON_NUMBER_RANGE;
		}else {
			return ValidationError.NONE;
		}

	}
	
}
