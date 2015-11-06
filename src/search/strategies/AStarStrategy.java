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
	
	@Override
	public Queue<Node> initialize() {
		return new PriorityQueue<>(1, new AStarComparator(mProblem));
	}

}
