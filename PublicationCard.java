package alchemistGame;

public class PublicationCard {
	
	private String name;
	private String requirement; 
	//requirement condition'ları nasıl gelmeli? Sadece string olması dogru degil.
	private int pointValue;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRequirement() {
		return requirement;
	}

	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}

	public int getPointValue() {
		return pointValue;
	}

	public void setPointValue(int pointValue) {
		this.pointValue = pointValue;
	}
	
	
}
