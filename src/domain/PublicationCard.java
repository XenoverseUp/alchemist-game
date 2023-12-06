package domain;

public class PublicationCard {
    private String name;
    private String requirement;
    private int value;


    public PublicationCard(String name, String requirement, int value) {
		this.name = name;
		this.requirement = requirement;
		this.value = value;
		

	}

    public void setName(String name) {
		this.name = name;
	}

	
	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}
	
	public void setValue(int value) {
		this.value = value;
	}

	public String getRequirement(){
		return this.requirement;
	}

	public String getName(){
		return this.name;
	}

	public int getValue(){
		return this.value;
	}



}
