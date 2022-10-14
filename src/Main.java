
public class Main {

	public static void main(String[] args) {

		SystemOperations sysops = new SystemOperations();
		
		sysops.initiateAlgorithm("(a)+a");
		sysops.structs.getParseTree().printTree();

	}

}
