package search.space;

/**
 * State ADT
 */
public interface State extends Comparable<State> {
	public int getTurnsNeeded();
	public int getHammingDistance();
	public int getManhattanDistance();
	public String toString();
}
