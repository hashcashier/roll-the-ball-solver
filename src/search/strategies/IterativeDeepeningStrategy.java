package search.strategies;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import search.Strategy;
import search.space.Node;

public class IterativeDeepeningStrategy extends Strategy {

	private int mMaxDepth;
	
	@Override
	public Queue<Node> initialize() {
		mMaxDepth = -1;
		return new LinkedList<Node>();
	}

	@Override
	public void enqueue(Queue<Node> queue, List<Node> nodes) {
		if (nodes.isEmpty()) {
			return;
		}
		LinkedList<Node> simulatedStack = (LinkedList<Node>) queue;

		Node n = nodes.get(0);
		if (n.getNodeDepth() == 0) {
			mMaxDepth++;
			simulatedStack.addLast(n);
		}
		
		for (Node node : nodes) {
			if (node.getNodeDepth() <= mMaxDepth) {
				simulatedStack.addFirst(node);
			}
		}
	}

}
