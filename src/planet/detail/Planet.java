package planet.detail;

import java.io.Serializable;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Planet implements PlanetPlan, Serializable   {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;
	Double temp;
	int numbOfMoons;
	String planetImg;
	
	DoubleProperty diameterProperty = new SimpleDoubleProperty();
	StringProperty nameProperty = new SimpleStringProperty();
	DoubleProperty tempProperty = new SimpleDoubleProperty();
	
	public Planet() {
		this.name = "";
		//this.temp = -300.0;
		this.numbOfMoons = -1;
		this.planetImg = "images/no_image.png";
		
		this.nameProperty.set("");
		this.diameterProperty.set(-1.0);
		this.tempProperty.set(-300.00);
		
	}
	
	public void setPlanetName(String name) {
		this.nameProperty.set(name);
	}
	
	public String getPlanetName() {
		return this.nameProperty.get();
	}
	
	public StringProperty getPlanetNameProperty() {
		return this.nameProperty;
	}

	public void setDiameter(double diameter) {
		this.diameterProperty.set(diameter);
	}
	
	public double getDiameter() {
		return this.diameterProperty.get();
	}
	
	public DoubleProperty getDiameterProperty() {
		return this.diameterProperty;
	}
	
	public void setTemperature(double tempInCelcius) {
		this.tempProperty.set(tempInCelcius);
	}
	
	public double getTemperature() {
		return this.tempProperty.get();
	}
	
	public DoubleProperty getTemperatureProperty() {
		return this.tempProperty;
	}

	public void setNumOfMoons(int numOfMoons) {
		this.numbOfMoons = numOfMoons;
	}
	
	@Override
	public void setPlanetImg(String planetImg) {
		this.planetImg = planetImg;
	}
	
	public int getNumbOfMoons() {
		return numbOfMoons;
	}

	public String getPlanetImg() {
		return planetImg;
	}


}
