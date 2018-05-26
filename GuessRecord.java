
public class GuessRecord {

	//variables of GameRecord
	private String Attribute;
	private String guessValue;
	
	//constructor
	public GuessRecord(String Attribute, String guessValue) {
		this.Attribute= Attribute;
		this.guessValue= guessValue;
	}

	//getters
	public String getAttribute() {
		return Attribute;
	}

	public String getGuessValue() {
		return guessValue;
	}
	
	
}
