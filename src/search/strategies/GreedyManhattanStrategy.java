package search.strategies;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import search.Strategy;
import search.comparators.ManhattanComparator;
import search.space.Node;

public class GreedyManhattanStrategy extends Strategy {
	
	private static ManhattanComparator sComparator = new ManhattanComparator();

	@Override
	public Queue<Node> initialize() {
		return new PriorityQueue<>(1, sComparator);
	}

}
