package unitConverter;

public class UnitConverter {
	
	//formula from https://www.metric-conversions.org/length/kilometers-to-miles.htm
	public double kilometerToMile(double distanceInKm) {
		double distanceInMi = distanceInKm * 0.621371;
		return distanceInMi;
	}
	//formula from https://www.metric-conversions.org/temperature/celsius-to-fahrenheit.htm
	public double celciusToFahrenheit(double tempInC) {
		double tempInF = (tempInC * 1.8000) + 32;
		
		return tempInF;
	}
}
