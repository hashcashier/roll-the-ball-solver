package search;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import search.space.Node;
import search.space.Operator;

/**
 * Search ADT
 */
public class Search {
	
	/**
	 * General Search Algorithm
	 * @param problem
	 * @param queueStrategy
	 * @return A solution, if one exists, of the @Problem using the specified @Strategy
	 */
	public static Solution search(Problem problem, Strategy queueStrategy) {
		int totalExpandedNodesCount = 1;
		Queue<Node> queue = queueStrategy.initialize(problem);
		queueStrategy.enqueue(queue, problem.getInitNode());
		while (!queue.isEmpty()) {
			Node front = queue.remove();
			if (problem.goalTest(front.getNodeState())) {
				return new Solution(front.getPath(), totalExpandedNodesCount);
			}
			List<Node> expanded = expand(front, problem.getOperators());
			totalExpandedNodesCount += expanded.size();
			queueStrategy.enqueue(queue, expanded);
		}
		return null;
	}
	
	/**
	 * Expand a target node using a set of operators
	 * @param node
	 * @param operators
	 * @return A list of all nodes resulting from applying all the operators on the target
	 */
	private static List<Node> expand(Node node, Operator[] operators) {
		List<Node> result = new LinkedList<>();
		for (int i = 0; i < operators.length; i++) {
			result.addAll(operators[i].apply(node));
		}
		return result;
	}

}
