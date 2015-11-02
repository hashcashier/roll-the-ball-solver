package grid;

import java.util.Arrays;

public class Grid {
	private int mRows;
	private int mCols;
	private int mGRow;
	private int mGCol;
	/**
	 * Dimensions: mRows * mCols
	 * Refer to @Cell enum
	 */
	private Cell[][] mCells;
	
	/**
	 * 
	 * @param rows
	 * @param cols
	 * @param initial
	 * @param gRow
	 * @param gCol
	 * @param goal
	 */
	public Grid(int rows, int cols, Cell initial, int gRow, int gCol, Cell goal) {
		mRows = rows;
		mCols = cols;
		mCells = new Cell[rows][cols];
		for(int i = 0; i < rows; i++) {
			Arrays.fill(mCells[i], Cell.BLANK);
		}
		set(0, 0, initial);
		set(gRow, gCol, goal);
		mGRow = gRow;
		mGCol = gCol;
	}

	public int getRows() {
		return mRows;
	}
	
	public int getCols() {
		return mCols;
	}
	
	public int getGRow() {
		return mGRow;
	}
	
	public int getGCol() {
		return mGCol;
	}
	
	/**
	 * Set the cell value at (row, col)
	 * @param row
	 * @param col
	 * @param value
	 */
	public void set(int row, int col, Cell value) {
		if (row < mRows && col < mCols) {
			mCells[row][col] = value;
		}
	}
	
	/**
	 * @param row
	 * @param col
	 * @return @Cell at (row, col) in mCells
	 */
	public Cell get(int row, int col) {
		if (row < mRows && col < mCols) {
			return mCells[row][col];
		}
		return null;
	}
	
	/**
	 * 
	 * @return the @Position of the last reachable cell from the initial cell
	 */
	public Position pathEnd() {
		for(int r = 0, c = 0, p = 0, q = 0;;) {
			Cell current = get(r, c);
			for(int k = 0; k <= GridConfig.DIRECTIONS; k++) {
				// If we have no direction to move in
				if (k == GridConfig.DIRECTIONS) {
					return new Position(r, c);
				}
				// If cell (r, c) is not open from this direction
				if (!GridConfig.cellOpen(current, k)) {
					continue;
				}
				int nextR = r + GridConfig.deltaRow(k);
				int nextC = c + GridConfig.deltaCol(k);
				// If going out of range or back to previous cell (p, q)
				if (!inRange(nextR, nextC) || (nextR == p && nextC == q)) {
					continue;
				}
				Cell next = get(nextR, nextC);
				// If the target cell is not open from the opposite side
				if (!GridConfig.cellOppositeOpen(next, k)) {
					continue;
				}
				p = r;
				q = c;
				r = nextR;
				c = nextC;
				break;
			}
		}
	}
	
	/**
	 * @param r cell row
	 * @param c cell column
	 * @return true when @Cell (r, c) is within the grid range
	 */
	private boolean inRange(int r, int c) {
		return 0 <= r && r < mRows && 0 <= c && c < mCols;
	}
	
	/**
	 * @return A boolean equal to true if the initial cell and goal cell are connected by a path.
	 */
	public boolean isSolved() {
		Position end = pathEnd();
		return end.row == mGRow && end.col == mGCol;
	}
	
	public String toString() {
		String border = "";
		for (int i = 0; i < mCols + 2; i++) {
			border += GridConfig.BORDER_CHAR;
		}
		String result = border;
		for (int i = 0; i < mRows; i++) {
			result += "\n";
			result += GridConfig.BORDER_CHAR;
			for (int j = 0; j < mCols; j++) {
				result += GridConfig.getRepresentation(mCells[i][j]);
			}
			result += GridConfig.BORDER_CHAR;
		}
		result += "\n" + border;
		if (isSolved()) {
			result += "\nSOLVED";
		}
		return result;
	}
}
