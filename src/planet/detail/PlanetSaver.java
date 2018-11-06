package planet.detail;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class PlanetSaver {
	
	public void serializePlanet(Planet planet) {
		String fileName = "c:\\temp\\" + planet.getName() + ".ser";
		FileOutputStream fout = null;
		ObjectOutputStream oos = null; 

		try {

			fout = new FileOutputStream(fileName);
			oos = new ObjectOutputStream(fout);
			oos.writeObject(planet);

			

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			if (fout != null) {
				try {
					fout.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}
}
