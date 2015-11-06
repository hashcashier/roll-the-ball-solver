package search.strategies;

import java.util.PriorityQueue;
import java.util.Queue;

import search.Strategy;
import search.comparators.TurnComparator;
import search.space.Node;

public class GreedyTurnStrategy extends Strategy	 {

	private static TurnComparator sComparator = new TurnComparator();

	@Override
	public Queue<Node> initialize() {
		return new PriorityQueue<>(1, sComparator);
	}

}
