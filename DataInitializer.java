import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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
		people.clear();
		gmaeConfiguration = new File(gameFilename);
		
		//check if the game configuration files are within input
		if(!gmaeConfiguration.exists()) {
			System.out.println("No game Configuration files are loaded, please check and re-run the program!");
			throw new IOException();
		}
		playerDataImport();
		
		return (people);
	}
	
	public static void playerDataImport() throws IOException {
		String input;
		int descriptionAdded = 0;
		boolean importStart = false;

		BufferedReader br = new BufferedReader(new FileReader(gmaeConfiguration));
		
		while((input = br.readLine())!=null) {
			
			if(input.startsWith("P")) {
				importStart = true;
			}
			if(importStart == true) {
				descriptionAdded++;
				assignData(input);		
			}
			if(descriptionAdded == 10) {
				people.add(new PersonalDescriptions(name, hairLength, glasses, facialHair, eyeColor, pimples, hat,
						hairColor,noseShape, faceShape));
				descriptionAdded = 0;
				importStart = false;
			}
		}	
		br.close();	
	}
	
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
	
//	public HashMap<String, ArrayList<String>> availableProperties(String gameFilename)throws IOException {
//		ArrayList<String> properties = new ArrayList<String>();
//		String key = null;
//		HashMap<String, ArrayList<String>> hashMap = new HashMap<String, ArrayList<String>>();
//		properties.clear();
//		hashMap.clear();
//		gmaeConfiguration = new File(gameFilename);
//		String input;
//		int rowCount = 0;
//		//check if the game configuration files are within input
//		if(!gmaeConfiguration.exists()) {
//			System.out.println("No game Configuration files are loaded, please check and re-run the program!");
//			throw new IOException();
//		}
//		
//		BufferedReader br = new BufferedReader(new FileReader(gmaeConfiguration));
//		while((input = br.readLine())!=null) {
//			if(rowCount < 9) {
//				String[] splitStr = input.split("\\s+");
//				for(int i = 0; 0 < splitStr.length; i++ ) {
//					if(i == 0)
//						key = splitStr[0];
//					else 
//						properties.add(splitStr[i]);
//					
//				}
//				if(key !=null)
//					hashMap.put(key, properties);
//				properties.clear();
//				rowCount++;
//			}
//			else {
//				break;
//			}
//		}
//		br.close();
//		return (hashMap);
//	}
}
