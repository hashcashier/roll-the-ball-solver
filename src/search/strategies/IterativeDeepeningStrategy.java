package search.strategies;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import roll_the_ball.Main;
import search.Strategy;
import search.space.Node;

public class IterativeDeepeningStrategy extends Strategy {

	private int mMaxDepth = 0;
	private int maxSeenDepth = -1;
	private Node mRootNode;
	
	@Override
	public Queue<Node> initialize() {
		return new LinkedList<Node>();
	}

	@Override
	public void enqueue(Queue<Node> queue, List<Node> nodes) {
		LinkedList<Node> simulatedStack = (LinkedList<Node>) queue;

		if (!nodes.isEmpty()) {
			Node n = nodes.get(0);
			Node p = n.getParentNode();
			
			maxSeenDepth = Math.max(maxSeenDepth, n.getNodeDepth());
			
			if (mRootNode == null && n.getNodeDepth() == 0) {
				mRootNode = n;
			}
			
			if (p != null) {
				mProblem.addToStateSpace(p.getNodeState());
			}
			
			boolean hasChildren = false;
			if (n.getNodeDepth() <= mMaxDepth) {
				for (Node node : nodes) {
					if (!mProblem.stateSpaceContains(node.getNodeState())) {
						simulatedStack.addFirst(node);
						hasChildren = true;
					}
				}
			}
			
			if(!hasChildren) {
				removeAncestorsFromStateSpace(p, simulatedStack);
			}
		}


		if(simulatedStack.isEmpty() && mMaxDepth <= maxSeenDepth) {
			Main.out.println("Iterative Deepening Level: " + mMaxDepth++);
			simulatedStack.add(mRootNode);
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
