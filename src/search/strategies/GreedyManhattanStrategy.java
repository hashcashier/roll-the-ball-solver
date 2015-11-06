package search.strategies;

import java.util.PriorityQueue;
import java.util.Queue;

import search.Strategy;
import search.comparators.ManhattanComparator;
import search.space.Node;

/**
 * GR1 @Strategy Implementation
 */
public class GreedyManhattanStrategy extends Strategy {
	
	private static ManhattanComparator sComparator = new ManhattanComparator();

	/**
	 * Use a priority queue with the @ManhattanComparator
	 */
	@Override
	public Queue<Node> initialize() {
		return new PriorityQueue<>(1, sComparator);
	}

}
