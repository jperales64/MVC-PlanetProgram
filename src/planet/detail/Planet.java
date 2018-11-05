package planet.detail;

import java.awt.image.BufferedImage;


public class Planet implements PlanetPlan {

	String name;
	Double diameter;
	Double temp;
	int numbOfMoons;
	BufferedImage planetImg;
	
	public void setPlanetName(String name) {
		this.name = name;

	}

	public void setDiameter(Double diameter) {
		this.diameter = diameter;
	

	}

	public void setTemp(Double temp) {
		this.temp = temp;

	}

	public void setNumOfMoons(int numOfMoons) {
		this.numbOfMoons = numOfMoons;

	}

	public void setPlanetImg(BufferedImage planet) {
		this.planetImg = planet;
	}

}
