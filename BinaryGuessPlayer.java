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
	//variables for whole class to use
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

    //translate desire mAttribute to getter command of the ArrayList
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
    
    //count the frequency of attribute combinations within all people
    public void attributeCount() {   
    	String keys;
    	int frequency;
    	attributeCount.clear();
    	
    	//go though all people in order to calculate attribute frequency
    	for(int i = 0; i < people.size(); i++) {
    		//go though all attributes of each person (total 9)
    		for(int j = 0; j < attributePool.length; j++) {
    			keys = attributePool[j] +" "+ randomPickTranslator(i, attributePool[j], people);
    			//if key of the hashMap already exist, add the value instead of creating new
    			if(attributeCount.containsKey(keys)) {
    				frequency  = attributeCount.get(keys);
    				attributeCount.put(keys, ++frequency);
    			}
    			//creating new key
    			else
    				attributeCount.put(keys, 1);	
    		}
    	}
    }
    
    public Guess guess() {
    	String binaryKey = null;
    	int tempValue;
    	int minValue = 0;
    	boolean cutHalf = false;
    	
    	//check the frequency of each attribute combinations within all people every time before each guess
    	attributeCount();
    	
    	//reserves for debugging 
    	System.out.println(people.size());
    	
    	//if remaining only 1 person to be guessed, just guess without further computing
    	if(personCount == 1 )
    		return new Guess(Guess.GuessType.Person, "", people.get(0).getName());
    	else {	//if more than 1 person remaining
    		//go though all key sets of the hashMap
    		for(String key : attributeCount.keySet()) {
    			tempValue = attributeCount.get(key);
    			if(minValue == 0 ) {
    				minValue = tempValue;
    				binaryKey  = key;
    			}    		
    			
    			//if we find a key(attribute) that can cut half of the current people base
    			if(tempValue == (personCount/2)) {
    				binaryKey = key;
    				cutHalf = true;
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
    			}
    		}
    		
    		//reserve for debugging
    		if(cutHalf==true)
    			System.out.println("elimating half of people...");
    		else
    			System.out.println("elimating " +minValue +"people");
    		
    		//return the guess
    		String[] splitStr = binaryKey.split(" ");
    		return new Guess(Guess.GuessType.Attribute, splitStr[0],splitStr[1]);
    	}
    } // end of guess()


	public boolean answer(Guess currGuess) {
		//check if opponent guess type = person, follow by certain operation
    	if(currGuess.getType()== Guess.GuessType.Person) {
    		
    		//opponent guess person straight
    		if(currGuess.getValue().equals(personChosen.get(0).getName()))
    			return true;
    		//if guess wrong
    		else
    			return false;
    	}
    	else {
    		//means opponent guess attribute
    		String chosenValue = randomPickTranslator(0, currGuess.getAttribute(), personChosen);
    		
    		//if guessed correctly
    		if(chosenValue.equals(currGuess.getValue()))
    				return true;
    		//if not correctly
    		else
    			return false;
    	}
    } // end of answer()


	public boolean receiveAnswer(Guess currGuess, boolean answer) {
		String mValue;
		//check if my guess type = person, follow by certain operation
		if(currGuess.getType()== Guess.GuessType.Person) {
			
			//player got the correct person
			if(answer== true)
				return true;
			else {	//since didn't get the correct person
				
				//go though whole ArrayList
				for(int i = 0; i<people.size(); i++) {
					
					//if found the person that we previously guessed
					if(people.get(i).getName().equals(currGuess.getValue())) {
						
						//set it for deletion later
						people.get(i).setGuessAvailable(false);
						
						//call deletion
						dataRenewal();
						return false;
					}
				}
			}
		}
		else {	//guesstype = attribute
			//go though whole ArrayList
			for(int i =0; i< people.size(); i++) {
				
				//get the mValue that we previously guessed
				mValue = randomPickTranslator(i, currGuess.getAttribute(), people);
				
				//if guessed correctly previously
				if(answer==true) {
					//set all other people that don't have related mAttribute, mValue combination for deletion
					if(!currGuess.getValue().equals(mValue))
						people.get(i).setGuessAvailable(false);
				}
				//if we guessed incorrectly previously 
				else {
					//set all other people that have related mAttribute, mValue combination for deletion
					if(currGuess.getValue().equals(mValue))
						people.get(i).setGuessAvailable(false);
				}
			}
			//call deletion 
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
					//after every deletion, check from beginning again
					break;
				}	
				if(i==people.size()-1) {
					endReached= true;
					//get the latest person count
					personCount = people.size();
				}	
			}
		}
	}
} // end of class BinaryGuessPlayer
