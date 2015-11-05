package search.strategies;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import search.Strategy;
import search.space.Node;

public class BreadthFirstStrategy extends Strategy {

	@Override
	public Queue<Node> initialize() {
		return new LinkedList<Node>();
	}

}
