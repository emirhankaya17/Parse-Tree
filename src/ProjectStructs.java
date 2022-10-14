import java.util.ArrayList;
import java.util.HashMap;

public class ProjectStructs {
	
	private HashMap<String, ArrayList<String>> cfgMap;
	private ArrayList<String> cfgVars;
	private ArrayList<String> alphabet;
	
	public ArrayList<String> derivation;
	
	private ParseTree parseTree;
	
	public ProjectStructs() {
		
		cfgMap = new HashMap<String, ArrayList<String>>();
		
		cfgVars = new ArrayList<String>();
		alphabet = new ArrayList<String>();
		
		derivation = new ArrayList<String>();

		
	}
	
	public void initiateTree(String rootData) { parseTree = new ParseTree(rootData); parseTree.updateLeafNodes();}
	
	
	public HashMap<String, ArrayList<String>> getCfgMap() {
		return cfgMap;
	}

	public String cfgMapString() {	return cfgMap.toString();	}
	
	public void addDerivationStep(String step) {
		
		derivation.add(step);
		
	}
	
	public String getDerivationString() {
		String result = "Start ";
		
		for (int i = 0; i < derivation.size(); i++) {
			result += "=> " + derivation.get(i) + " ";
		}
		
		return result;
	}
	
	public ParseTree getParseTree(){ return parseTree; }

	public ArrayList<String> getCfgVars() {
		return cfgVars;
	}

	public ArrayList<String> getAlphabet() {
		return alphabet;
	}
	
	
	
}
