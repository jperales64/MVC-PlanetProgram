package planetValidator;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;


class PlanetValidatorTest {
	
	PlanetValidator planetValidator = new PlanetValidator();

	@Test
	void validateShortNameLengthTest() {
		ValidationError expected = ValidationError.NAME_LENGTH;
		ValidationError result;
		String planetName = "";
		
		result = planetValidator.validateName(planetName);
		
		assertEquals(expected, result);
		
	}
	
	@Test
	void validateLongNameLengthTest() {
		ValidationError expected = ValidationError.NAME_LENGTH;
		ValidationError result;
		String planetName;
		
		char[] charArray = new char[256];
		Arrays.fill(charArray, 'a');
		planetName = new String(charArray);
		result = planetValidator.validateName(planetName);
		
		assertEquals(expected, result);
	}
	
	@Test
	void validateProperNameLengthTest() {
		ValidationError expected = ValidationError.NONE;
		ValidationError result;
		String planetName;
		
		char[] charArray = new char[255];
		Arrays.fill(charArray, 'a');
		planetName = new String(charArray);
		result = planetValidator.validateName(planetName);
		
		assertEquals(expected, result);
	}
	
	@Test
	void validateInproperNameRegexTest() {
		ValidationError expected = ValidationError.NAME_REGEX;
		ValidationError result;
		String planetName = "?$#@!%^&*()";
		
		result = planetValidator.validateName(planetName);
		
		assertEquals(expected, result);
	}
	
	@Test
	void validateProperNameRegexTest() {
		ValidationError expected = ValidationError.NONE;
		ValidationError result;
		String planetName = "AZ az 0123456789 - . ";
		
		result = planetValidator.validateName(planetName);
		
		assertEquals(expected, result);
	}
	
	@Test
	void validateMinDiameterTest() {
		ValidationError expectedMin = ValidationError.DIAMETER_RANGE;
		ValidationError resultMin;
		
		double planetMinDiameter = 0;
		resultMin = planetValidator.validateDiameter(planetMinDiameter);
		
		assertEquals(expectedMin, resultMin);
	}
	
	@Test
	void validateMaxDiameterTest() {
		ValidationError expectedMax = ValidationError.DIAMETER_RANGE;
		ValidationError resultMax;
		
		double planetMaxDiameter = 200001;
		resultMax = planetValidator.validateDiameter(planetMaxDiameter);
		
		assertEquals(expectedMax, resultMax);
	}
	
	@Test
	void validateMinTemperatureTest() {
		ValidationError expectedMin = ValidationError.TEMPERATURE_RANGE;
		ValidationError resultMin;
		
		double planetMinTemperature = -273.16;
		resultMin = planetValidator.validateTemp(planetMinTemperature);
		
		assertEquals(expectedMin, resultMin);
	}
	
	@Test
	void validateMaxTemperatureTest() {
		ValidationError expectedMax = ValidationError.TEMPERATURE_RANGE;
		ValidationError resultMax;
		
		double planetMaxTemperature = 500.1;
		resultMax = planetValidator.validateTemp(planetMaxTemperature);
		
		assertEquals(expectedMax, resultMax);
	}
	
	@Test
	void validateMinNumOfMoonsTest() {
		ValidationError expectedMin = ValidationError.MOON_NUMBER_RANGE;
		ValidationError resultMin;
		
		int planetMaxMoons = -1;
		resultMin = planetValidator.validateNumOfMoons(planetMaxMoons);
		
		assertEquals(expectedMin, resultMin);
	}
	
	@Test
	void validateMaxNumOfMoonsTest() {
		ValidationError expectedMax = ValidationError.MOON_NUMBER_RANGE;
		ValidationError resultMax;
		
		int planetMaxMoons = 1001;
		resultMax = planetValidator.validateNumOfMoons(planetMaxMoons);
		
		assertEquals(expectedMax, resultMax);
	}

}
