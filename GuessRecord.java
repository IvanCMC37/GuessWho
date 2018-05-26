
public class GuessRecord {

	private String Attribute;
	private String guessValue;
	
	public GuessRecord(String Attribute, String guessValue) {
		this.Attribute= Attribute;
		this.guessValue= guessValue;
	}

	public String getAttribute() {
		return Attribute;
	}

	public String getGuessValue() {
		return guessValue;
	}
	
	
}
