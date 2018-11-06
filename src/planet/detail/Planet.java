package planet.detail;

import java.io.Serializable;

public class Planet implements PlanetPlan, Serializable   {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;
	Double diameter;
	
	Double temp;
	int numbOfMoons;
	String planetImg;
	
	public Planet() {
		this.name = "";
		this.diameter = -1.0;
		this.temp = -300.0;
		this.numbOfMoons = -1;
		this.planetImg = "images/no_image.png";
	}
	
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

	
	@Override
	public void setPlanetImg(String planetImg) {
		this.planetImg = planetImg;
		
	}
	
	public String getName() {
		return name;
	}

	public Double getDiameter() {
		return diameter;
	}

	public Double getTemp() {
		return temp;
	}

	public int getNumbOfMoons() {
		return numbOfMoons;
	}

	public String getPlanetImg() {
		return planetImg;
	}



}
