package grid;

import java.util.Arrays;

public class Grid {
	private int mRows;
	private int mCols;
	/**
	 * Dimensions: mRows * mCols
	 * Refer to @Cell enum
	 */
	private Cell[][] mCells;
	
	public Grid(int rows, int cols, Cell initial, int gRow, int gCol, Cell goal) {
		mRows = rows;
		mCols = cols;
		mCells = new Cell[rows][cols];
		for(int i = 0; i < rows; i++) {
			Arrays.fill(mCells, Cell.BLANK);
		}
		set(0, 0, initial);
		set(gRow, gCol, goal);
	}
	
	public int getRows() {
		return mRows;
	}
	
	public int getCols() {
		return mCols;
	}
	
	public void set(int row, int col, Cell value) {
		if (row < mRows && col < mCols) {
			mCells[row][col] = value;
		}
	}
	
	public Cell get(int row, int col) {
		if (row < mRows && col < mCols) {
			return mCells[row][col];
		}
		return null;
	}
}
