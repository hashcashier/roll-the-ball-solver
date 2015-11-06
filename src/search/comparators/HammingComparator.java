package search.comparators;

import search.space.Node;
import search.space.NodeComparator;

/**
 * Comparison using F(n) = H(n) Where
 * H(N) is the misplaced tiles heuristic
 */
public class HammingComparator extends NodeComparator {

	@Override
	public int value(Node n) {
		return n.getNodeState().getHammingDistance();
	}

}
