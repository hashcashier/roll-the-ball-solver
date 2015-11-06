package search.comparators;

import search.space.Node;
import search.space.NodeComparator;

/**
 * Comparison using F(n) = H(n) + G(n) Where
 * H(N) is the turns needed heuristic
 * G(N) is the path cost to the node
 */
public class AStarComparator extends NodeComparator{

	@Override
	public int value(Node n) {
		return n.getNodeState().getTurnsNeeded() + n.getPathCost();
	}

}
