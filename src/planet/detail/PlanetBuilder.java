package planet.detail;

public class PlanetBuilder {
	
	private Planet planet;
	
	public PlanetBuilder() {
		this.planet = new Planet();
	}
	
	public void buildName(String name) { 
		this.planet.setName(name);
	}
	
	public void buildDiameter(double diameter) {
		this.planet.setDiameter(diameter);
	}
	
	public void buildTemp(double temp) {
		this.planet.setTemperature(temp);
	}
	
	public void buildNumberofMoons(int numOfMoons) {
		this.planet.setNumberOfMoons(numOfMoons);
		
	}
	
	public void buildPlanetImg(String planetImg) {
		this.planet.setPlanetImg(planetImg);
	}
	
	public Planet getPlanet() {
		return this.planet;
	}
	
}
