package planet.detail;




public class Planet implements PlanetPlan {

	String name;
	Double diameter;
	
	Double temp;
	int numbOfMoons;
	String planetImg;
	
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
	public void setPlanetImg(String planet) {
		// TODO Auto-generated method stub
		
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
