package search.strategies;

import java.util.PriorityQueue;
import java.util.Queue;

import search.Strategy;
import search.comparators.AStarComparator;
import search.space.Node;

/**
 * A* @Strategy Implementation
 */
public class AStarStrategy extends Strategy {
	
	private static AStarComparator sComparator = new AStarComparator();

	/**
	 * Use a priority queue with the @AStarComparator
	 */
	@Override
	public Queue<Node> initialize() {
		return new PriorityQueue<>(1, sComparator);
	}

}
