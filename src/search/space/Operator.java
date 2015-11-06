package search.space;

import java.util.Collection;

/**
 * Operator ADT
 */
public abstract class Operator {
	public abstract Collection<Node> apply(Node node);
	
}
