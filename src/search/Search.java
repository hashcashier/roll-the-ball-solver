package search;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import search.space.Node;
import search.space.Operator;

public class Search {
	
	public static Solution search(Problem problem, Strategy queueStrategy) {
		int totalExpandedNodesCount = 1;
		Queue<Node> queue = queueStrategy.initialize();
		queue.add(problem.getInitNode());
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
	
	private static List<Node> expand(Node node, Operator[] operators) {
		List<Node> result = new LinkedList<>();
		for (int i = 0; i < operators.length; i++) {
			result.addAll(operators[i].apply(node));
		}
		return result;
	}

}
