package search.comparators;

import search.space.Node;
import search.space.NodeComparator;

/**
 * Comparison using F(n) = H(n) Where
 * H(N) is the turns needed heuristic
 */

public class TurnComparator extends NodeComparator{

	@Override
	public int value(Node n) {
		return n.getNodeState().getTurnsNeeded();
	}

}
