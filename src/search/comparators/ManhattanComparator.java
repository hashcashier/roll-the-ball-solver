package search.comparators;

import search.Problem;
import search.space.Node;
import search.space.NodeComparator;

/**
 * Comparison using F(n) = H(n) Where
 * H(N) is the Manhattan distance heuristic
 */

public class ManhattanComparator extends NodeComparator {
	
	public ManhattanComparator(Problem problem) {
		super(problem);
	}
	
	@Override
	public int value(Node n) {
		return n.getNodeState().getManhattanDistance();
	}

}
