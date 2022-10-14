import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;

public class SystemOperations {

	ProjectStructs structs;
	ArrayList<String> results = new ArrayList<String>();
	String testString = "";
	Random rand = new Random();

	private void populateCFG() {

		// reads CFG.txt and adds it to the hashmap

		try {

			File CFG = new File("CFG.txt");
			Scanner myReader = new Scanner(CFG);

			while (myReader.hasNextLine()) {
				String data = myReader.nextLine(); // get line
				String[] temp_data = data.split(">"); // split from ">"

				// split from "|" and convert to ArrayList
				ArrayList<String> temp_cfgExp = new ArrayList<String>(Arrays.asList(temp_data[1].split("\\|")));

				// also determine alphabet and variables
				if (!structs.getCfgVars().contains(temp_data[0])) {
					structs.getCfgVars().add(temp_data[0]);
				}

				for (int i = 0; i < temp_cfgExp.size(); i++) {
					String temp_char = temp_cfgExp.get(i);

					for (int j = 0; j < temp_char.length(); j++) {

						if (!Character.isUpperCase(temp_char.charAt(j)) &&
							!structs.getAlphabet().contains(String.valueOf(temp_char.charAt(j)))) {
							structs.getAlphabet().add(String.valueOf(temp_char.charAt(j)));
						}
					}



				}


				// map variable to its expression
				structs.getCfgMap().put(temp_data[0], temp_cfgExp);


			}

			myReader.close();

		} catch (FileNotFoundException e) {

			System.out.println("CFG.txt not found.");
			e.printStackTrace();
			System.exit(1);
		}

	}

	public void initiateAlgorithm(String inputString) {

		// add the CFG to a hashmap
		structs = new ProjectStructs();
		populateCFG();
		testString = inputString;

		System.out.println("Input: " + testString + " - Size: " + String.valueOf(testString.length()));

		System.out.println("CFG: " + structs.cfgMapString());
		System.out.println("Alphabet: " + structs.getAlphabet().toString() + " - Size: " + String.valueOf(structs.getAlphabet().size()));
		System.out.println("Variables: " + structs.getCfgVars().toString() + " - Size: " + String.valueOf(structs.getCfgVars().size()));

		boolean correctAlphabet = true;

		// check if input string has correct alphabet
		for (int i = 0; i < testString.length(); i++) {
			if(!structs.getAlphabet().contains(String.valueOf(testString.charAt(i)))) {
				System.out.println("String contains unrecognized letters.");
				correctAlphabet = false;
			}
		}

		if (correctAlphabet) {

			int n = 0;
			int counter = 0;
			while (n != 1 && counter <3000000) { // recursive protection
				structs.derivation = new ArrayList<>();
				structs.initiateTree(structs.getCfgVars().get(0));
				n = recursive(structs.getCfgVars().get(0),inputString);
				counter++;
				//System.out.println(structs.getDerivationString());
				//System.out.println("--------------------------------");
			}

			if(n == 1 ) {
				System.out.println("--------------------------------");
				System.out.println(structs.getDerivationString());
				System.out.println("--------------------------------");
				System.out.println("Found");
			}
			else
				System.out.println("Not Found");
		}


	}



	public int recursive(String currentString,String targetString) { // -1, 1 -> final input

		//get variables
		ArrayList<String> tempCapitals = new ArrayList<String>();
		for (int i = 0; i < currentString.length(); i++) {
			if(Character.isUpperCase(currentString.charAt(i)))
				tempCapitals.add(String.valueOf(currentString.charAt(i)));
		}

		//check recursive outputs
		if(currentString.length() >targetString.length() * 5)
			return -1;
		else if(currentString.length() == targetString.length() && tempCapitals.size() == 0)
		{
			return currentString.equals(targetString) ? 1 : 0 ;
		}
		else if(tempCapitals.size() == 0)
			return -1;

		// random choices
		int int_random = rand.nextInt(tempCapitals.size());
		String tempLetter = tempCapitals.get(int_random);
		int mapChoiceRange = structs.getCfgMap().get(tempLetter).size();
		int int_random2 = rand.nextInt(mapChoiceRange);
		String concat = structs.getCfgMap().get(tempLetter).get(int_random2);

		//empty string
		if(concat.equals("#"))
			concat = "";

		// string concat
		for (int i = 0; i < currentString.length(); i++) {
			if(currentString.charAt(i) == tempLetter.charAt(0))
			{
				//tree operations
				structs.getParseTree().updateLeafNodes();
				for (int j = 0; j <structs.getParseTree().leafNodes.size() ; j++) {
					if(structs.getParseTree().leafNodes.get(j).data.equals(tempLetter))
					{
						for (int k = 0; k < concat.length(); k++) {
							structs.getParseTree().leafNodes.get(j).addChild(String.valueOf(concat.charAt(k)));

						}
						break;
					}
				}

				if(i>0 && i<currentString.length()-1)
				{
					String subL = currentString.substring(0,i);
					String subR = currentString.substring(i+1);
					subL += (concat + subR);
					concat = subL;
					break;
				}
				else if(i == 0)
				{
					concat += currentString.substring(i+1); break;
				}
				else {
					concat = currentString.substring(0,currentString.length()-1) + concat; break;
				}
			}
		}
		// final return and derivation
		structs.addDerivationStep(concat);
		return recursive(concat,targetString);
	}



	
}
