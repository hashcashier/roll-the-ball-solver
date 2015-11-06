package search.strategies;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import search.Strategy;
import search.space.Node;

public class DepthFirstStrategy extends Strategy {

	@Override
	public Queue<Node> initialize() {
		return new LinkedList<Node>();
	}

	@Override
	public void enqueue(Queue<Node> queue, List<Node> nodes) {
		LinkedList<Node> simulatedStack = (LinkedList<Node>) queue;

		if (nodes.isEmpty()) {
			return;
		}
		
		Node n = nodes.get(0);
		Node p = n.getParentNode();
		
		if (p != null) {
			mProblem.addToStateSpace(p.getNodeState());
		}
		
		boolean hasChildren = false;
		

		for (Node node : nodes) {
			if (!mProblem.stateSpaceContains(node.getNodeState())) {
				simulatedStack.addFirst(node);
				hasChildren = true;
			}
		}
		
		if(!hasChildren) {
			removeAncestorsFromStateSpace(p, simulatedStack);
		}

}

	private void removeAncestorsFromStateSpace(Node n, LinkedList<Node> stack) {
		if (stack.isEmpty()) {
			mProblem.clearStateSpace();
			return;
		}
		
		Node target = stack.getFirst().getParentNode();
		while (n != target && n != null) {
			mProblem.removeFromStateSpace(n.getNodeState());
			n = n.getParentNode();
		}
	}
}
