package planetValidator;

import planet.detail.Planet;

public class PlanetValidator {

	boolean validateName(String planetName) {
		
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
//	boolean validateDiameter(double planetDiameter){
//		
//	}
//	boolean validateTemp(double planetTemp){
//		
//	}
//	boolean validateNumOfMoons(int numOfMoons){
//		
//	}
//	boolean validateImage(String planetImgPath){
//		
//	}
//	boolean validatePlanet(Planet planet){
//		
//	}
	
}
