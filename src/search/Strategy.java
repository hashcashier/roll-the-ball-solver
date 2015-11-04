package search;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import search.space.Node;

public abstract class Strategy {
	public abstract Queue<Node> initialize();
	
	public abstract void enqueue(Queue<Node> queue, List<Node> nodes);
	
	public void enqueue(Queue<Node> queue, Node node) {
		LinkedList<Node> list = new LinkedList<>();
		list.add(node);
		enqueue(queue, list);
	}
}
