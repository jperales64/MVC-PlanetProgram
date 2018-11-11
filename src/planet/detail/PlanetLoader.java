package planet.detail;

import java.io.File;
import javafx.stage.FileChooser;

public class PlanetLoader {
    private FileChooser fileChooser = new FileChooser();

	public Planet loadPlanet(File file) {		
		Planet planet = null;
		
		PlanetFileParser pfParser = new PlanetFileParser();
		fileChooser.setInitialDirectory(new File("saved_planets"));
		
		try {
			planet = pfParser.createPlanetFromFile(file.getAbsolutePath()); 
		}catch (Exception e) {}
		
		return planet;
	}
}
