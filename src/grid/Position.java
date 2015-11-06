package grid;

/**
 * A wrapper class for row, column and path length values.
 */
public class Position {
	public int row, col, len;

	public Position(int r, int c) {
		row = r;
		col = c;
	}

	public Position(int r, int c, int L) {
		this(r, c);
		len = L;
	}
}
