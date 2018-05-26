
public class PersonalDescriptions {
	
	//basic descriptions of the person
	private String name;
	private String hairLength;
	private String glasses;
	private String facialHair;
	private String eyeColor;
	private String pimples;
	private String hat;
	private String hairColor;
	private String noseShape;
	private String faceShape;
	
	//boolean check if the person is available to be guessed
	private boolean guessAvailable;
	
	//constructor of the class, for player classes to use later on
	public PersonalDescriptions(String name, String hairLength, String glasses, String facialHair, String prieyeColor,
			String pimples, String hat, String hairColor, String noseShape, String faceShape) {
		
		this.name = name;
		this.hairLength = hairLength;
		this.glasses = glasses;
		this.facialHair = facialHair;
		this.eyeColor = prieyeColor;
		this.pimples = pimples;
		this.hat = hat;
		this.hairColor  = hairColor;
		this.noseShape = noseShape;
		this.faceShape = faceShape;
		
		//always assign true when creating a new person
		this.guessAvailable =  true;
	}

	//getters for individual property
	public String getName() {
		return name;
	}

	public String getHairLength() {
		return hairLength;
	}

	public String getGlasses() {
		return glasses;
	}

	public String getFacialHair() {
		return facialHair;
	}

	public String geteyeColor() {
		return eyeColor;
	}

	public String getPimples() {
		return pimples;
	}

	public String getHat() {
		return hat;
	}

	public String getHairColor() {
		return hairColor;
	}

	public String getNoseShape() {
		return noseShape;
	}

	public String getFaceShape() {
		return faceShape;
	}

	public boolean isGuessAvailable() {
		return guessAvailable;
	}

	//setter for guessAvailable, rest properties are not allowed to be set again once created
	public void setGuessAvailable(boolean guessAvailable) {
		this.guessAvailable = guessAvailable;
	}
	

}
