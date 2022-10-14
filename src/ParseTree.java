import java.util.ArrayList;
import java.util.List;

public class ParseTree {
	private Node root;
	ArrayList<Node> leafNodes = new ArrayList<>();
    public ParseTree(String rootData) {
        root = new Node(rootData);
    }

	public Node getRoot() {
		return root;
	}

	public Node foundLeaf(String str)
	{
		updateLeafNodes();
		for (int i = 0; i < leafNodes.size(); i++) {
			if(leafNodes.get(i).data.equals(str))
				return leafNodes.get(i);
		}
		return null;
	}

	void updateLeafNodes()
	{
		leafNodes = new ArrayList<>();
		if(root.children.size() == 0)
		{
			leafNodes.add(root);
			return ;
		}
		//checkChildrens
		for (int i = 0; i <root.children.size() ; i++) {
			if(root.children.get(i).children.size() == 0)
			{
				leafNodes.add(root.children.get(i));
			}
			else
				updateLeafNodes(root.children.get(i));
		}

	}
	void updateLeafNodes(Node n)
	{
		if(n.children.size() == 0)
		{
			leafNodes.add(n);
			return;
		}
		//checkChildrens
		for (int i = 0; i <n.children.size() ; i++) {
			if(n.children.get(i).children.size() == 0)
			{
				leafNodes.add(n.children.get(i));
			}
			else
				updateLeafNodes(n.children.get(i));
		}

	}
	public String printTree() // -1 -> finished, 1 -> continue
	{
		int level = 0;
		String returner = "";

		returner += root.data+"\n";
		if(root.children.size() == 0)
			return returner;
		else
		{
			for (int i = 0; i < root.children.size(); i++) {
				returner += printTreeRecursive(level+1,root.children.get(i));
			}
		}
		System.out.println(returner);
		return returner;
	}
	public String printTreeRecursive(int level,Node node) // -1 -> finished, 1 -> continue
	{
		String returner = "";
		returner += getLevelBlanks(level);
		returner += node.data+"\n";

		if(node.children.size() == 0)
			return returner;
		else
		{
			for (int i = 0; i < node.children.size(); i++) {
				returner += printTreeRecursive(level+1,node.children.get(i));
			}
		}
		return returner;
	}
	public String getleafsString()
	{
		String returner = "";
		for (int i = 0; i < leafNodes.size(); i++) {
			returner +=(leafNodes.get(i).data)+"-";
		}
		return returner;
	}

	private String getLevelBlanks(int level)
	{
		String returner = "";
		for (int i = 0; i < level; i++) {
			if(i== level-1)
				returner +="->";
			returner+="\t";
		}
		return returner;
	}

    public class Node {
       String data;
	   ArrayList<Node> children;

		public Node(String data) {
			this.data = data;
			children = new ArrayList<>();
		}

		public void addChild(String childData)
		{
			Node str = new Node(childData);
			children.add(str);
		}
	}
}