import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DataInitializer {

	//Task A basically -  data importing and structuring
	static File gmaeConfiguration;
	private static String name;
	private static String hairLength;
	private static String glasses;
	private static String facialHair;
	private static String eyeColor;
	private static String pimples;
	private static String hat;
	private static String hairColor;
	private static String noseShape;
	private static String faceShape;
	static ArrayList<PersonalDescriptions> people = new ArrayList<PersonalDescriptions>();
	
	public static ArrayList<PersonalDescriptions> importData(String gameFilename) throws IOException {
		//clear ArrayList to prevent unwanted error
		people.clear();	
		
		//check if the game configuration files are within input
		gmaeConfiguration = new File(gameFilename);
		if(!gmaeConfiguration.exists()) {
			System.out.println("No game Configuration files are loaded, please check and re-run the program!");
			throw new IOException();
		}
		
		//call function to import data one by one
		playerDataImport();
		
		return (people);
	}
	
	//function to read though game configuration and fill into constructor 
	public static void playerDataImport() throws IOException {
		String input;
		int descriptionAdded = 0;
		boolean importStart = false;

		BufferedReader br = new BufferedReader(new FileReader(gmaeConfiguration));
		
		while((input = br.readLine())!=null) {
			
			//import start when read a name
			if(input.startsWith("P")) {
				importStart = true;
			}
			if(importStart == true) {
				descriptionAdded++;
				assignData(input);		
			}
			
			//since there are total 10 attributes in each person, reset afterwards
			if(descriptionAdded == 10) {
				//fill all known attributes to constructor then to ArrayList
				people.add(new PersonalDescriptions(name, hairLength, glasses, facialHair, eyeColor, pimples, hat,
						hairColor,noseShape, faceShape));
				descriptionAdded = 0;
				importStart = false;
			}
		}	
		//close stream to prevent memory leak
		br.close();	
	}
	
	//assign different attribute depends on readline result
	public static void assignData(String input) {
		String[] splitStr = input.split("\\s+");
		
		switch(splitStr[0]) {
		case "hairLength":
			hairLength = splitStr[1];
			break;
		case "glasses":
			glasses = splitStr[1];
			break;
		case "facialHair":
			facialHair = splitStr[1];
			break;
		case "eyeColor":
			eyeColor = splitStr[1];
			break;
		case "pimples":
			pimples = splitStr[1];
			break;
		case "hat":
			hat = splitStr[1];
			break;
		case "hairColor":
			hairColor = splitStr[1];
			break;
		case "noseShape":
			noseShape = splitStr[1];
			break;
		case "faceShape":
			faceShape = splitStr[1];
			break;
		default:
			name = splitStr[0];
			break;
		}
	}
}
