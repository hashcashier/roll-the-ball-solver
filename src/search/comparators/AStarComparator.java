package search.comparators;

import search.Problem;
import search.space.Node;
import search.space.NodeComparator;

/**
 * Comparison using F(n) = H(n) + G(n) Where
 * H(N) is the turns needed heuristic
 * G(N) is the path cost to the node
 */
public class AStarComparator extends NodeComparator{
	
	public AStarComparator(Problem problem) {
		super(problem);
	}

	@Override
	public int value(Node node) {
		return node.getNodeState().getTurnsNeeded() + mProblem.pathCost(node);
	}

}
