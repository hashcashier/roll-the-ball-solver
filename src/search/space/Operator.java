package search.space;

import java.util.Collection;

public abstract class Operator {
	public abstract Collection<Node> apply(Node node);
}
