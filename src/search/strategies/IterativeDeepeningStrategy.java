package search.strategies;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import roll_the_ball.Main;
import search.Strategy;
import search.space.Node;

/**
 * Iterative Deepening @Strategy Implementation
 * @author User
 *
 */
public class IterativeDeepeningStrategy extends Strategy {

	private int mMaxDepth = 0;
	private int maxSeenDepth = -1;
	private Node mRootNode;
	
	/**
	 * @LinkedList implements @Queue
	 */
	@Override
	public Queue<Node> initialize() {
		return new LinkedList<Node>();
	}

	/**
	 * Use a @LinkedList to simluate @Stack behavior while maintaining
	 * usage of @Queue interface for DFS traversal. Called at most ONCE
	 * per enqueuing parent in every iteration. 
	 */
	@Override
	public void enqueue(Queue<Node> queue, List<Node> nodes) {
		LinkedList<Node> simulatedStack = (LinkedList<Node>) queue;

		if (!nodes.isEmpty()) {
			Node enqueuedChild = nodes.get(0);
			Node enqueingParent = enqueuedChild.getParentNode();
			
			maxSeenDepth = Math.max(maxSeenDepth, enqueuedChild.getNodeDepth());
			
			if (mRootNode == null && enqueuedChild.getNodeDepth() == 0) {
				mRootNode = enqueuedChild;
			}
			
			if (enqueingParent != null) {
				mProblem.addToStateSpace(enqueingParent.getNodeState());
			}
			
			boolean hasChildren = false;
			if (enqueuedChild.getNodeDepth() <= mMaxDepth) {
				for (Node node : nodes) {
					if (!mProblem.stateSpaceContains(node.getNodeState())) {
						simulatedStack.addFirst(node);
						hasChildren = true;
					}
				}
			}
			
			// Done exploring this chain end
			if(!hasChildren) {
				removeAncestorsFromStateSpace(enqueingParent, simulatedStack);
			}
		}

		// If we are done, either restart with higher depth or
		// terminate depending on whether we expanded a node with
		// our maximum depth level or not.
		if(simulatedStack.isEmpty() && mMaxDepth <= maxSeenDepth) {
			Main.out.println("Iterative Deepening Level: " + mMaxDepth++);
			simulatedStack.add(mRootNode);
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
