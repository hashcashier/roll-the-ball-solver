package roll_the_ball;

public class Node {
	private State<?> nodeState;
	private Node parentNode;
	private Operator operatorApplied;
	private int nodeDepth;
	private int pathCost;

	// This is the parent node
	public Node(State<?> nodeState) {
		this.setNodeState(nodeState);
		this.parentNode = null;
		this.operatorApplied = new Operator("");
		this.nodeDepth = 0;
		this.pathCost = 0;
	}

	public Node(State<?> nodeState, Node parentNode, Operator operatorApplied,
			int nodeDepth, int pathCost) {
		this.setNodeState(nodeState);
		this.parentNode = parentNode;
		this.operatorApplied = operatorApplied;
		this.nodeDepth = nodeDepth;
		this.pathCost = pathCost;
	}

	public Node getParentNode() {
		return parentNode;
	}

	public void setParentNode(Node parentNode) {
		this.parentNode = parentNode;
	}

	public Operator getOperatorApplied() {
		return operatorApplied;
	}

	public void setOperatorApplied(Operator operatorApplied) {
		this.operatorApplied = operatorApplied;
	}

	public int getNodeDepth() {
		return nodeDepth;
	}

	public void setNodeDepth(int nodeDepth) {
		this.nodeDepth = nodeDepth;
	}

	public int getPathCost() {
		return pathCost;
	}

	public void setPathCost(int pathCost) {
		this.pathCost = pathCost;
	}

	public State<?> getNodeState() {
		return nodeState;
	}

	public void setNodeState(State<?> nodeState) {
		this.nodeState = nodeState;
	}

	public static void main(String[] args) {
		State<Integer> nodestate = new State<Integer>(1);
		State<String> parentnodestate = new State<String>("Hi");
		Node parent = new Node(parentnodestate, null, new Operator("+"), 0, 10);
		Node s = new Node(nodestate, parent, new Operator("-"), 1, 8);
	}

}
