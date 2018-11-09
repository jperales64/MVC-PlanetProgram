package planetValidator;

import planet.detail.Planet;

public class PlanetValidator {

	public boolean validateName(String planetName) {
		
		if (planetName.length() < 1 || planetName.length() > 255) {
			return false;
		}
		else if (!(planetName.matches("^[a-zA-Z0-9-. ]+$"))) {
			return false;
		} 
		else {
			return true;
		}
	}
	public boolean validateDiameter(double planetDiameter){
		if(planetDiameter < 0 || planetDiameter > 200000) {
			return false;
		}
		else {
			return true;
		}
	}
	public boolean validateTemp(double planetTemp){
		if(planetTemp < -273.15 || planetTemp > 500.0) {
			return false;
		}
		else {
			return true;
		}
	}
	public boolean validateNumOfMoons(int numOfMoons){
		if(numOfMoons < 0 || numOfMoons > 1000) {
			return false;
		}
		else {
			return true;
		}
	}

	public boolean validatePlanet(Planet planet){
		if(!(validateName(planet.getPlanetName()) && validateDiameter(planet.getDiameter())
				&& validateTemp(planet.getTemperature()) && validateNumOfMoons(planet.getNumOfMoons()))) {
			return false;
		}
		else {
			return true;
		}
	}
	
}
