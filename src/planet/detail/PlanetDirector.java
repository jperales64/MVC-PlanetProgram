package planet.detail;

public class PlanetDirector {
	
	private PlanetBuilder planetBuilder;
	
	public PlanetDirector() {
		this.planetBuilder = new PlanetBuilder();
	}
	
	public Planet getPlanet() {
		return this.planetBuilder.getPlanet();
	}
	
	public void makePlanet() {
		this.planetBuilder.buildPlanetName("Neptune");
		this.planetBuilder.buildDiameter(22.0);
		this.planetBuilder.buildNumofMoons(5);
		this.planetBuilder.buildTemp(13.5);
		this.planetBuilder.buildPlanetImg("neptune.png");
	}
}
