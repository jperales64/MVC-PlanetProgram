package planet.detail;

public class PlanetDirector {
	
	private PlanetBuilder planetBuilder;
	
	public PlanetDirector() {
		this.planetBuilder = new PlanetBuilder();
	}
	
	public Planet getPlanet() {
		return this.planetBuilder.getPlanet();
	}
	
	public void makePlanet(String planetName, double planetDiameter, int numOfMoons, double temp, String planetImg) {
		this.planetBuilder.buildName(planetName);
		this.planetBuilder.buildDiameter(planetDiameter);
		this.planetBuilder.buildNumberofMoons(numOfMoons);
		this.planetBuilder.buildTemp(temp);
		this.planetBuilder.buildPlanetImg(planetImg);
	}
}
