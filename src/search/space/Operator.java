package search.space;

import java.util.Collection;

public abstract class Operator {
	private static Operator sInstance;
	
	protected abstract Operator newInstance();
	
	public Operator getInstance() {
		return sInstance == null ? (sInstance = newInstance()) : sInstance;
	}
	
	public abstract Collection<Node> apply(Node node);
}
