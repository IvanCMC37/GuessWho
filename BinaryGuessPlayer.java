import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Binary-search based guessing player.
 * This player is for task C.
 *
 * You may implement/extend other interfaces or classes, but ensure ultimately
 * that this class implements the Player interface (directly or indirectly).
 */
public class BinaryGuessPlayer implements Player
{
	private ArrayList<PersonalDescriptions> people;
	private ArrayList<PersonalDescriptions> personChosen = new ArrayList<PersonalDescriptions>();
	private String[] attributePool = {"hairLength", "glasses", "facialHair", "eyeColor", "pimples", "hat", "hairColor", "noseShape", "faceShape"};
	int personCount = 0;
	private HashMap<String, Integer> attributeCount = new HashMap<String, Integer>();
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
    public BinaryGuessPlayer(String gameFilename, String chosenName)
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
    } // end of BinaryGuessPlayer()

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
    
    public void attributeCount() {    	
    	String keys;
    	int frequency;
    	attributeCount.clear();
    	//go though all people in order to calculate attribute frequency
    	for(int i = 0; i < people.size(); i++) {
    		//go though all attributes of each person (total 9)
    		for(int j = 0; j < attributePool.length; j++) {
    			keys = attributePool[j] +" "+ randomPickTranslator(i, attributePool[j], people);
    			if(attributeCount.containsKey(keys)) {
    				frequency  = attributeCount.get(keys);
    				attributeCount.put(keys, ++frequency);
    			}
    			else
    				attributeCount.put(keys, 1);	
    		}
    	}
    }
    
    public Guess guess() {
    	//check the frequency of each attribute within all people every time before the guess
    	attributeCount();
    	
    	String binaryKey = null;
    	int tempValue;
    	int minValue = 0;
    	//reserves for debugging 
//    	System.out.print("Guess choice: ");
//    	for(int i  =0; i< people.size(); i++)
//    		System.out.print(people.get(i).getName()+" ");
//    	System.out.println();
    	System.out.println(people.size());
    	
    	//if remaining only 1 person to be guessed, just guess without computing
    	if(personCount == 1 )
    		return new Guess(Guess.GuessType.Person, "", people.get(0).getName());
    	else {
    		for(String key : attributeCount.keySet()) {
    			tempValue = attributeCount.get(key);
    			if(minValue == 0 ) {
    				minValue = tempValue;
    				binaryKey  = key;
    			}    				
    			//if we find a key(attribute) that can cut half of the current people base
    			if(tempValue == (personCount/2)) {
    				binaryKey = key;
    				System.out.println("can cut half "+ tempValue);
    				break;
    			}
    			//if not, find the closest
    			else {
    				if(Math.abs(tempValue-(personCount/2))>0) {
    					if((tempValue > minValue)&&tempValue<personCount) {
    						minValue = tempValue;
        					binaryKey = key;
        					
    					}
    				}
    					
//    				if(tempValue>(personCount/2)) {
//    					if((tempValue <= minValue)) {
//    						minValue = tempValue;
//        					binaryKey = key;
//    					}
//    					
//    				}
    			}
    		}
    		System.out.println("min "+minValue);
//    		System.out.println(attributeCount.size());
//    		System.out.println(binaryKey);
    		//return the guess
    		String[] splitStr = binaryKey.split(" ");
    		return new Guess(Guess.GuessType.Attribute, splitStr[0],splitStr[1]);
    	}
    	
       
    } // end of guess()


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
} // end of class BinaryGuessPlayer
