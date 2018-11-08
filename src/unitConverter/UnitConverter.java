package unitConverter;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

public class UnitConverter {
	
	//formula from https://www.metric-conversions.org/length/kilometers-to-miles.htm
	public double kilometerToMile(double kilometers) {
		double result = kilometers * 0.6214;
		
		return result;
	}
	
	//formula from https://www.metric-conversions.org/temperature/celsius-to-fahrenheit.htm
	public double celciusToFahrenheit(double tempInC) {
		double tempInF = (tempInC * 1.8000) + 32;
		
		return tempInF;
	}
}
