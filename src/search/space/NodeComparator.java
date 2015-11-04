package search.space;

import java.util.Comparator;

public abstract class NodeComparator implements Comparator<Node>{
	
	@Override
	public int compare(Node o1, Node o2) {
		return value(o1) - value(o2);
	}
	
	public abstract int value(Node n);

}
