package search;

import java.util.List;
import java.util.Queue;

import search.space.Node;

public abstract class Strategy {
	public abstract Queue<Node> initialize();
	public abstract void enqueue(Queue<Node> queue, List<Node> nodes);
}
