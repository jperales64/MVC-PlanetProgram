package planet.detail;

public class PlanetBuilder {
	
	private Planet planet;
	
	public PlanetBuilder() {
		this.planet = new Planet();
	}
	
	public void buildPlanetName(String name) {
		this.planet.setPlanetName(name);
	}
	
	public void buildDiameter(Double diameter) {
		this.planet.setDiameter(diameter);
	}
	
	public void buildTemp(Double temp) {
		this.planet.setTemp(temp);
	}
	
	public void buildNumofMoons(int numOfMoons) {
		this.planet.setNumOfMoons(numOfMoons);
		
	}
	
	public void buildPlanetImg(String planetImg) {
		this.planet.setPlanetImg(planetImg);
	}
	
	public Planet getPlanet() {
		return this.planet;
	}
	
}
