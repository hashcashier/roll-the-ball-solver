package search.strategies;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import search.Strategy;
import search.space.Node;

/**
 * DFS @Strategy Implementation
 */
public class DepthFirstStrategy extends Strategy {


	/**
	 * @LinkedList implements @Queue
	 */
	@Override
	public Queue<Node> initialize() {
		return new LinkedList<Node>();
	}

	/**
	 * Use a @LinkedList to simluate @Stack behavior while maintaining
	 * usage of @Queue interface for DFS traversal. Called ONCE per enqueuing parent.
	 */
	@Override
	public void enqueue(Queue<Node> queue, List<Node> nodes) {
		LinkedList<Node> simulatedStack = (LinkedList<Node>) queue;

		if (nodes.isEmpty()) {
			return;
		}
		
		Node enqueuedChild = nodes.get(0);
		Node enqueingParent = enqueuedChild.getParentNode();
		
		// Prevent parent from being revisited by subtree
		if (enqueingParent != null) {
			mProblem.addToStateSpace(enqueingParent.getNodeState());
		}
		
		boolean hasChildren = false;

		for (Node node : nodes) {
			if (!mProblem.stateSpaceContains(node.getNodeState())) {
				simulatedStack.addFirst(node);
				hasChildren = true;
			}
		}
		
		// Reached the end of this chain
		if(!hasChildren) {
			removeAncestorsFromStateSpace(enqueingParent, simulatedStack);
		}
	}

	/**
	 * Clear the state space of all chain members that have no children to be visited.
	 * @param leaf
	 * @param stack
	 */
	private void removeAncestorsFromStateSpace(Node leaf, LinkedList<Node> stack) {
		// All done
		if (stack.isEmpty()) {
			mProblem.clearStateSpace();
			return;
		}
		// Go up chain until we reach the parent of the next node to be visited
		Node target = stack.getFirst().getParentNode();
		while (leaf != target && leaf != null) {
			mProblem.removeFromStateSpace(leaf.getNodeState());
			leaf = leaf.getParentNode();
		}
	}
}
