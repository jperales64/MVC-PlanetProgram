package planet.detail;

import java.io.Serializable;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Planet implements PlanetPlan, Serializable   {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;
	double diameter;
	double temperature;
	int numberOfMoons;
	String planetImg;
	
	public Planet() {	
		this.name = "";
		this.diameter = 0;
		this.temperature = -300.00;
		this.numberOfMoons = -1;
		this.planetImg = "images/no_image.png";
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}

	public void setDiameter(double diameter) {
		this.diameter = diameter;
	}
	
	public double getDiameter() {
		return this.diameter;
	}
	
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	
	public double getTemperature() {
		return temperature;
	}
	
	public void setNumberOfMoons(int numberOfMoons) {
		this.numberOfMoons = numberOfMoons;
	}
	
	public int getNumberOfMoons() {
		return numberOfMoons;
	}

	public void setPlanetImg(String planetImg) {
		this.planetImg = planetImg;
	}
	
	public String getPlanetImg() {
		return planetImg;
	}

	@Override
	public String toString() {
		return this.getName() + "\n"
				+ this.getDiameter() + "\n"
				+ this.getTemperature() + "\n"
				+ this.getNumberOfMoons() + "\n"
				+ this.getPlanetImg();
	}


}
