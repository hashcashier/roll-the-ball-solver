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
		for (Node node : nodes) {
			simulatedStack.addFirst(node);
		}
	}

}
