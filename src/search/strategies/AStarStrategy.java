package search.strategies;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import search.Strategy;
import search.comparators.AStarComparator;
import search.space.Node;

public class AStarStrategy extends Strategy {
	
	private static AStarComparator sComparator = new AStarComparator();

	@Override
	public Queue<Node> initialize() {
		return new PriorityQueue<>(1, sComparator);
	}

}
