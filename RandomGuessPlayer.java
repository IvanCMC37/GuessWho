import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Random guessing player.
 * This player is for task B.
 *
 * You may implement/extend other interfaces or classes, but ensure ultimately
 * that this class implements the Player interface (directly or indirectly).
 */
public class RandomGuessPlayer implements Player
{

    /**
     * Loads the game configuration from gameFilename, and also store the chosen
     * person.
     *
     * @param gameFilename Filename of game configuration.
     * @param chosenName Name of the chosen person for this player.
     * @throws IOException If there are IO issues with loading of gameFilename.
     *    Note you can handle IOException within the constructor and remove
     *    the "throws IOException" method specification, but make sure your
     *    implementation exits gracefully if an IOException is thrown.
     */
	
	private ArrayList<PersonalDescriptions> people;
	int personCount = 0;
	private ArrayList<PersonalDescriptions> personChosen = new ArrayList<PersonalDescriptions>();
	private String[] attributePool= {"hairLength", "glasses", "facialHair", "eyeColor", "pimples", "hat", "hairColor", "noseShape", "faceShape"};
    private ArrayList<GuessRecord> guessRecord =  new ArrayList<GuessRecord>();	
    private String randomAttribute;
	private String randomValue;
	private int randomPerson;
	
    public RandomGuessPlayer(String gameFilename, String chosenName)
        throws IOException
    {	
    	//import all possible people into the game data
    	people  = new ArrayList<PersonalDescriptions>(DataInitializer.importData(gameFilename));
    	
    	//get number of total person involved in this game
    	personCount = people.size();
    	
    	//loop though all person in order to get details about the chosen one
    	for(int i = 0; i< personCount; i++) {
    		if(chosenName.equals(people.get(i).getName()))
    			personChosen.add(people.get(i));
    	}
    	
    } // end of RandomGuessPlayer()


    public Guess guess() {    	
    	System.out.print("Guess choice: ");
    	for(int i  =0; i< people.size(); i++)
    		System.out.print(people.get(i).getName()+" ");
    	System.out.println();
    	//if remaining only 1 person to be guessed, just guess without computing
    	if(personCount == 1 )
    		return new Guess(Guess.GuessType.Person, "", people.get(0).getName());
    	
		repeatGuessCheck();

    	guessRecord.add(new GuessRecord(randomAttribute,randomValue));
        return new Guess(Guess.GuessType.Attribute, randomAttribute, randomValue);
    } // end of guess()

    public void repeatGuessCheck(){
    	//if more than 1 person remaining, randomly pick one attribute within pool and guess it
    	//pick 1 random attribute, number 0-8 since attributePool.length = 9 
    	Random random = new Random();
		randomAttribute = attributePool[random.nextInt(attributePool.length)];
    	randomPerson = random.nextInt(people.size());
    	randomValue =  randomPickTranslator( randomPerson,randomAttribute, people);
    	for(int i = 0; i<guessRecord.size();i++ ) {	
    		if((guessRecord.get(i).getAttribute().equals(randomAttribute))&&(guessRecord.get(i).getGuessValue().equals(randomValue))) {
    			System.out.println("Same with round "+(i+1)+" of guessing "+randomAttribute+" "+randomValue);
    			System.out.println("repeat guess, replacing...............................");
    			repeatGuessCheck();
    		}   			
    	}  	
    }
    public String randomPickTranslator(int personPick, String mAttribute, ArrayList<PersonalDescriptions> a) {
    	switch(mAttribute) {
    	case "hairLength": return a.get(personPick).getHairLength();
    	case "glasses": return a.get(personPick).getGlasses();
    	case "facialHair": return a.get(personPick).getFacialHair();
    	case "eyeColor": return a.get(personPick).geteyeColor();
    	case "pimples": return a.get(personPick).getPimples();
    	case "hat": return a.get(personPick).getHat();
    	case "hairColor": return a.get(personPick).getHairColor();
    	case "noseShape": return a.get(personPick).getNoseShape();
    	case "faceShape": return a.get(personPick).getFaceShape();
    	default:return null;
    	}	
    }

    public boolean answer(Guess currGuess) {
    	
    	//check if opponent guess type, follow by certain operation
    	if(currGuess.getType()== Guess.GuessType.Person) {
    		//opponent guess person straight
    		if(currGuess.getValue().equals(personChosen.get(0).getName()))
    			return true;
    		else
    			return false;
    	}
    	else {
    		//means opponent guess attribute
    		String chosenValue = randomPickTranslator(0, currGuess.getAttribute(), personChosen);
    		if(chosenValue.equals(currGuess.getValue()))
    				return true;
    		else
    			return false;
    	}
    } // end of answer()


	public boolean receiveAnswer(Guess currGuess, boolean answer) {
		String mValue;
		if(currGuess.getType()== Guess.GuessType.Person) {
			if(answer== true)
				return true;
			else {
				for(int i = 0; i<people.size(); i++) {
					if(people.get(i).getName().equals(currGuess.getValue())) {
						people.get(i).setGuessAvailable(false);
						dataRenewal();
						return false;
					}
				}
			}
		}
		else {
			for(int i =0; i< people.size(); i++) {
				mValue = randomPickTranslator(i, currGuess.getAttribute(), people);
				if(answer==true) {
					if(!currGuess.getValue().equals(mValue))
						people.get(i).setGuessAvailable(false);
				}
				else {
					if(currGuess.getValue().equals(mValue))
						people.get(i).setGuessAvailable(false);
				}
			}
			dataRenewal();			
		}
		return false;
    } // end of receiveAnswer()

	//delete person that is marked as isGuessAvailable == false
	public void dataRenewal() {
		boolean endReached =false;
		while(endReached == false) {
			for (int i = 0; i< people.size(); i++) {
				if(people.get(i).isGuessAvailable()==false) {
					people.remove(i);
					break;
				}	
				if(i==people.size()-1) {
					endReached= true;
					personCount = people.size();
				}	
			}
		}
	}
} // end of class RandomGuessPlayer
