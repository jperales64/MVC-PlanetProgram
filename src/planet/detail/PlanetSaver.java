package planet.detail;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import planetValidator.AlertBuilder;
import planetValidator.PlanetValidator;

public class PlanetSaver {
	private PlanetValidator planetValidator = new PlanetValidator();
    private AlertBuilder alert = new AlertBuilder();

	public void savePlanet(Planet planet) {
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
	

}
