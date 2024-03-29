package search.strategies;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import roll_the_ball.Main;
import search.Strategy;
import search.space.Node;

/**
 * BFS @Strategy Implementation
 */
public class BreadthFirstStrategy extends Strategy {
	
	private int maxSoFar = 0;

	/**
	 * @LinkedList implements @Queue
	 */
	@Override
	public Queue<Node> initialize() {
		return new LinkedList<Node>();
	}
	
	/**
	 * Use a normal queue and state the depth level reached so far.
	 */
	@Override
	public void enqueue(Queue<Node> queue, List<Node> nodes) {
		super.enqueue(queue, nodes);
		if (nodes.isEmpty()) {
			return;
		}
		
		int level = nodes.get(0).getNodeDepth();
		if (level > maxSoFar) {
			Main.out.println("Breadth Level: " + level);
			maxSoFar = level;
		}
	}

}
