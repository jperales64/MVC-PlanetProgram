package unitConverter;

public class UnitConverter {
	
	double kmToMi(double distanceInKm) {
		double distanceInMi = distanceInKm / 1.609344;
		return distanceInMi;
	}
	
	double cToF(double tempInC) {
		double tempInF = (tempInC * (9.0/5)) + 32;
		
		return tempInF;
	}
}
