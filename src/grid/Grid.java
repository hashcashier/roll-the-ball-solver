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
	 * OPEN: A truth table for each cell type depicting if it is open from each direction.
	 * DROW: A table depicting the change in row value for each direction.
	 * DCOL: A table depicting the change in column value for each direction.
	 * 0 Up
	 * 1 Right
	 * 2 Left
	 * 3 Down
	 */
	private static int CELL_TYPES = 14;
	private static boolean[][] OPEN;
	private static int[] DROW;
	private static int[] DCOL;
	static {
		OPEN = new boolean[CELL_TYPES][4];
		for (int i = 0; i < CELL_TYPES; i++) {
			Arrays.fill(OPEN[i], false);
		}
		OPEN[Cell.INITIAL_D.ordinal()][3] 	= true;
		OPEN[Cell.INITIAL_R.ordinal()][1] 	= true;
		OPEN[Cell.GOAL_U.ordinal()][0] 		= true;
		OPEN[Cell.GOAL_R.ordinal()][1] 		= true;
		OPEN[Cell.GOAL_D.ordinal()][3] 		= true;
		OPEN[Cell.GOAL_L.ordinal()][2] 		= true;
		OPEN[Cell.PATH_DL.ordinal()][3] 	= true;
		OPEN[Cell.PATH_DL.ordinal()][2] 	= true;
		OPEN[Cell.PATH_LR.ordinal()][2] 	= true;
		OPEN[Cell.PATH_LR.ordinal()][1] 	= true;
		OPEN[Cell.PATH_RD.ordinal()][1] 	= true;
		OPEN[Cell.PATH_RD.ordinal()][3] 	= true;
		OPEN[Cell.PATH_UD.ordinal()][0] 	= true;
		OPEN[Cell.PATH_UD.ordinal()][3] 	= true;
		OPEN[Cell.PATH_UL.ordinal()][0] 	= true;
		OPEN[Cell.PATH_UL.ordinal()][2] 	= true;
		OPEN[Cell.PATH_UR.ordinal()][0] 	= true;
		OPEN[Cell.PATH_UR.ordinal()][1] 	= true;
		
		DROW = new int[]{-1, 0, 0, 1};
		DCOL = new int[]{0, 1, -1, 0};
	}

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
			Arrays.fill(mCells, Cell.BLANK);
		}
		set(0, 0, initial);
		set(gRow, gCol, goal);
		mGRow = gRow;
		mGCol = gCol;
	}

	/**
	 * 
	 * @return
	 */
	public int getRows() {
		return mRows;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getCols() {
		return mCols;
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
			for(int k = 0; k <= 4; k++) {
				// If we have no direction to move in
				if (k == 4) {
					return new Position(r, c);
				}
				// If cell (r, c) is not open from this direction
				if (!OPEN[current.ordinal()][k]) {
					continue;
				}
				int nextR = r + DROW[k];
				int nextC = c + DCOL[k];
				// If going out of range or back to previous cell (p, q)
				if (!inRange(nextR, nextC) || (nextR == p && nextC == q)) {
					continue;
				}
				Cell next = get(nextR, nextC);
				// If the target cell is not open from the opposite side
				if (!OPEN[next.ordinal()][3 - k]) {
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
}
