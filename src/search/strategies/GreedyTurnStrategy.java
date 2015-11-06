package search.strategies;

import java.util.PriorityQueue;
import java.util.Queue;

import search.Strategy;
import search.comparators.TurnComparator;
import search.space.Node;

/**
 * GR2 Implementation
 */
public class GreedyTurnStrategy extends Strategy	 {

	/**
	 * Use a priority queue with the @TurnComparator
	 */
	@Override
	public Queue<Node> initialize() {
		return new PriorityQueue<>(1, new TurnComparator(mProblem));
	}

}
