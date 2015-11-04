package search.space;

public interface State {
	public int getTurnsNeeded();
	public int getHammingDistance();
	public int getManhattanDistance();
	public String toString();
}
