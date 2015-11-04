package search.strategies;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import search.Strategy;
import search.comparators.HammingComparator;
import search.space.Node;

public class GreedyHammingStrategy extends Strategy	 {

	private static HammingComparator sComparator = new HammingComparator();

	@Override
	public Queue<Node> initialize() {
		return new PriorityQueue<>(1, sComparator);
	}

	@Override
	public void enqueue(Queue<Node> queue, List<Node> nodes) {
		queue.addAll(nodes);
	}

}
