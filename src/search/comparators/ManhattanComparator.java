package search.comparators;

import search.space.Node;
import search.space.NodeComparator;

public class ManhattanComparator extends NodeComparator {
	
	@Override
	public int value(Node n) {
		return n.getNodeState().getManhattanDistance();
	}

}
