package search.space;

import java.util.LinkedList;
import java.util.List;

public class Node {
	private State mNodeState;
	private Node mParentNode;
	/**
	 * Operator takes 0 for up, 1 for right, 2 for left, 3 for down
	 * we added -1 for the parent node, to be discussed
	 * don't we need to specify the cell that the operator was applied on?!
	 */
	private Operator mOperatorApplied;
	private int mNodeDepth;
	private int mPathCost;

	// This is the parent node
	public Node(State nodeState) {
		setNodeState(nodeState);
		mParentNode = null;
		mOperatorApplied = null;
		mNodeDepth = 0;
		mPathCost = 0;
	}

	public Node(State nodeState, Node parentNode, Operator operatorApplied, int nodeDepth) {
		setNodeState(nodeState);
		mParentNode = parentNode;
		mOperatorApplied = operatorApplied;
		mNodeDepth = nodeDepth;
	}

	public Node(State nodeState, Node parentNode, Operator operatorApplied, int nodeDepth, int pathCost) {
		this(nodeState, parentNode, operatorApplied, nodeDepth);
		mPathCost = pathCost;
	}

	public Node getParentNode() {
		return mParentNode;
	}

	public void setParentNode(Node parentNode) {
		mParentNode = parentNode;
	}

	public Operator getOperatorApplied() {
		return mOperatorApplied;
	}

	public void setOperatorApplied(Operator operatorApplied) {
		mOperatorApplied = operatorApplied;
	}

	public int getNodeDepth() {
		return mNodeDepth;
	}

	public void setNodeDepth(int nodeDepth) {
		mNodeDepth = nodeDepth;
	}

	public int getPathCost() {
		return mPathCost;
	}

	public void setPathCost(int pathCost) {
		mPathCost = pathCost;
	}
	
	public List<Node> getPath() {
		Node ptr = this;
		LinkedList<Node> result = new LinkedList<>();
		while(ptr != null) {
			result.addFirst(ptr);
			ptr = ptr.getParentNode();
		}
		return result;
	}

	public State getNodeState() {
		return mNodeState;
	}

	public void setNodeState(State nodeState) {
		mNodeState = nodeState;
	}

}
