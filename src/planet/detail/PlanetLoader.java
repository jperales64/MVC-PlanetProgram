package planet.detail;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class PlanetLoader {

	public Planet deserialzeAddress(String filename) {

		Planet planet = null;

		FileInputStream fin = null;
		ObjectInputStream ois = null;

		try {

			fin = new FileInputStream(filename);
			ois = new ObjectInputStream(fin);
			planet = (Planet) ois.readObject();

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {

			if (fin != null) {
				try {
					fin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		return planet;

	}

}
