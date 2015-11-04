package grid;

import java.util.Arrays;

import search.space.State;

public class Grid implements State {
	private int mRows;
	private int mCols;
	private int mGRow;
	private int mGCol;
	private int mSRow;
	private int mSCol;
	/**
	 * Dimensions: mRows * mCols
	 * Refer to @Cell enum
	 */
	private Cell[][] mCells;
	
	private Grid(int rows, int cols, int gRow, int gCol, int sRow, int sCol) {
		mRows = rows;
		mCols = cols;
		mSRow = sRow;
		mSCol = sCol;
		mGRow = gRow;
		mGCol = gCol;
	}
	
	public Grid(Grid grid) {
		this(grid.getRows(), grid.getCols(), grid.getGRow(), grid.getGCol(), grid.getSRow(), grid.getSCol());
		mCells = new Cell[mRows][mCols];
		for (int i = 0; i < mRows; i++) {
			for (int j = 0; j < mCols; j++) {
				mCells[i][j] = grid.get(i, j);
			}
		}
	}
	
	/**
	 * 
	 * @param rows
	 * @param cols
	 * @param initial
	 * @param gRow
	 * @param gCol
	 * @param goal
	 * @param sCol
	 * @param soal
	 */
	public Grid(int rows, int cols, Cell initial, int gRow, int gCol, Cell goal, int sRow, int sCol) {
		this(rows, cols, gRow, gCol, sRow, sCol);
		mCells = new Cell[rows][cols];
		for(int i = 0; i < rows; i++) {
			Arrays.fill(mCells[i], new Cell(CellType.BLANK));
		}
		set(sRow, sCol, initial);
		set(gRow, gCol, goal);
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
	
	public int getSRow() {
		return mSRow;
	}
	
	public int getSCol() {
		return mSCol;
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
		if (inRange(row, col)) {
			return mCells[row][col];
		}
		return null;
	}
	
	/**
	 * 
	 * @return the @Position of the last reachable cell from the initial cell
	 */
	public Position pathEnd(int startRow, int startCol) {
		for(int r = startRow, c = startCol, p = r, q = c, len = 1;;) {
			Cell current = get(r, c);
			for(int k = 0; k <= GridConfig.DIRECTIONS; k++) {
				// If we have no direction to move in
				if (current == null || k == GridConfig.DIRECTIONS) {
					return new Position(r, c, len);
				}
				// If cell (r, c) is not open from this direction
				if (!GridConfig.cellOpen(current.getType(), k)) {
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
				if (!GridConfig.cellOppositeOpen(next.getType(), k)) {
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
	public boolean inRange(int r, int c) {
		return 0 <= r && r < mRows && 0 <= c && c < mCols;
	}
	
	/**
	 * @return A boolean equal to true if the initial cell and goal cell are connected by a path.
	 */
	public boolean isSolved() {
		Position end = pathEnd(mSRow, mSCol);
		return end.row == mGRow && end.col == mGCol;
	}
	
	public int getManhattanDistance() {
		Position reachFromStart = pathEnd(mSRow, mSCol);
		return Math.abs(mGRow - reachFromStart.row) + Math.abs(mGCol - reachFromStart.col);
	}
	
	public int getHammingDistance() {
		Position reachFromStart = pathEnd(mSRow, mSCol);
		Position reachFromGoal = pathEnd(mGRow, mGCol);
		return mRows * mCols - (reachFromStart.len + reachFromGoal.len);
	}
	
	public int getTurnsNeeded() {
		Position reached = pathEnd(mSRow, mSCol);
		CellType endType = get(mGRow, mGCol).getType();
		if (reached.row != mGRow && reached.col != mGCol) {
			if (
					((reached.row < mGRow && reached.col > mGCol) && (endType == CellType.END_R || endType == CellType.END_U)) ||
					((reached.row < mGRow && reached.col < mGCol) && (endType == CellType.END_L || endType == CellType.END_U)) ||
					((reached.row > mGRow && reached.col < mGCol) && (endType == CellType.END_L || endType == CellType.END_D)) ||
					((reached.row > mGRow && reached.col > mGCol) && (endType == CellType.END_R || endType == CellType.END_D))) {
				return 1;
			}
			return 2;
		} else {
			if (
					(reached.col > mGCol && endType == CellType.END_L) ||
					(reached.col < mGCol && endType == CellType.END_R) ||
					(reached.row > mGRow && endType == CellType.END_U) ||
					(reached.row > mGRow && endType == CellType.END_D)) {
				return 3;
			} else if (
					(reached.col > mGCol && endType != CellType.END_R) ||
					(reached.col < mGCol && endType != CellType.END_L) ||
					(reached.row > mGRow && endType != CellType.END_D) ||
					(reached.row > mGRow && endType != CellType.END_U)) {
				return 2;
			}
		}
		return 0;
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
				result += GridConfig.getRepresentation(mCells[i][j].getType());
			}
			result += GridConfig.BORDER_CHAR;
		}
		result += "\n" + border;
		if (isSolved()) {
			result += "\nSOLVED";
		}
		return result;
	}

	@Override
	public int compareTo(State o) {
		return toString().compareTo(o.toString());
	}
}
