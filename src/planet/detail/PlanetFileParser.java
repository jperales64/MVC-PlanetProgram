package planet.detail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlanetFileParser {

	public PlanetFileParser() {}
	
	private String[] contentsArray;
	
	public void parseFile(String fileName) {
		try {
			BufferedReader in = new BufferedReader(new FileReader(fileName));
			String fileContents;
			List<String> list = new ArrayList<String>();
			while((fileContents = in.readLine()) != null) {
				list.add(fileContents);
			}
			contentsArray = list.toArray(new String[0]);
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Unable to read file");
		}
	}
	
	public Planet createPlanetFromFile(String fileName) {
		parseFile(fileName);
		Planet planet = new Planet();
		planet.setPlanetName(contentsArray[0]);
		planet.setDiameter(Double.parseDouble(contentsArray[1]));
		planet.setTemperature(Double.parseDouble(contentsArray[2]));
		planet.setNumOfMoons(Integer.parseInt(contentsArray[3]));
		planet.setPlanetImg(contentsArray[4]);
		
		return planet;
	}
}
