package search;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import search.space.Node;

/**
 * Strategy ADT
 */
public abstract class Strategy {
	protected Problem mProblem;
	
	public abstract Queue<Node> initialize();
	
	public Queue<Node> initialize(Problem problem) {
		mProblem = problem;
		return initialize();
	}

	/**
	 * Default behavior for BFS, Greedy and A*.
	 * @param queue
	 * @param nodes
	 */
	public void enqueue(Queue<Node> queue, List<Node> nodes) {
		for (Node node : nodes) {
			if (!mProblem.stateSpaceContains(node.getNodeState())) {
				queue.add(node);
				mProblem.addToStateSpace(node.getNodeState());
			}
		}
	}
	
	/**
	 * Wrap single node in list and call implemented enqueue.
	 * @param queue
	 * @param node
	 */
	public void enqueue(Queue<Node> queue, Node node) {
		LinkedList<Node> list = new LinkedList<>();
		list.add(node);
		enqueue(queue, list);
	}
}
