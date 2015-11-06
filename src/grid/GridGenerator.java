package grid;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A generator class for random game grids.
 */
public class GridGenerator {
	/**
	 * Maximum dimensions of a generated grid.
	 */
	public static int MAX_ROWS = 5;
	public static int MAX_COLS = 5;
	
	/**
	 * @return A random unsolved game grid.
	 */
	public static Grid genGrid() {
		// Generate random dimensions within range.
		int rows = rand().nextInt(1, MAX_ROWS);
		int cols = rand().nextInt(rows == 1 ? 2 : 1, MAX_COLS);
		// Generate random starting and goal positions.
		int sPos = rand().nextInt(rows*cols);
		int gPos = rand().nextInt(rows*cols - 1);
		// Resolve possible conflict
		if (sPos == gPos) {
			gPos++;
		}
		// Create random start cell
		int sRow = sPos / cols;
		int sCol = sPos % cols;
		Cell initial = randomStartCell(rows, cols, sRow, sCol);
		initial.setFixed(true);
		// Create random goal cell
		int gRow = gPos / cols;
		int gCol = gPos % cols;
		Cell goal = randomGoalCell(rows, cols, gRow, gCol, sRow, sCol, initial.getType());
		if (goal == null) {
			// Invalid configuration generated. Retry.
			return genGrid();
		}
		goal.setFixed(true);
		// Create a grid object with the two generated cells.
		Grid grid = new Grid(rows, cols, initial, sRow, sCol, goal, gRow, gCol);
		// Fill the grid body randomly and check if it is solved
		if (!generateGridBody(grid) || grid.isSolved()) {
			// Already solved grid or unsatisfied constraints.
			return genGrid();
		}

		// Return random grid.
		return grid;
	}
	
	/**
	 * @param rows
	 * @param cols
	 * @param sRow
	 * @param sCol
	 * @return A possible random cell type within the specified dimensions and position.
	 */
	private static Cell randomStartCell(int rows, int cols, int sRow, int sCol) {
		ArrayList<CellType> possibleStartCells = possibleTargetCells(rows, cols, sRow, sCol);
		return new Cell(possibleStartCells.get(rand().nextInt(possibleStartCells.size())));
	}
	
	/**
	 * @param rows
	 * @param cols
	 * @param gRow
	 * @param gCol
	 * @param sRow
	 * @param sCol
	 * @param initialType
	 * @return A possible random cell type within the specified dimensions and position while taking the start cell into consideration.
	 */
	private static Cell randomGoalCell(int rows, int cols, int gRow, int gCol, int sRow, int sCol, CellType initialType) {
		ArrayList<CellType> possibleGoalCells = possibleTargetCells(rows, cols, gRow, gCol);
		if (gRow == sRow) {
			if (gCol == sCol + 1) {
				if (initialType == CellType.END_R) {
					return null; // Can only return an already solved configuration
				} else {
					possibleGoalCells.remove(CellType.END_L); // Avoid possibility of already solved configuration
				}
			} else if (gCol == sCol - 1) {
				if (initialType == CellType.END_L) {
					return null;
				} else {
					possibleGoalCells.remove(CellType.END_R);
				}
			}
		} else if (gCol == sCol) {
			if (gRow == sRow + 1) {
				if (initialType == CellType.END_D) {
					return null;
				} else {
					possibleGoalCells.remove(CellType.END_U);
				}
			} else if (gRow == sRow - 1) {
				if (initialType == CellType.END_U) {
					return null;
				} else {
					possibleGoalCells.remove(CellType.END_D);
				}
			}
		}
		int possibilities = possibleGoalCells.size();
		return possibilities == 0 ? null : new Cell(possibleGoalCells.get(rand().nextInt(possibilities)));
	}
	
	/**
	 * @param rows
	 * @param cols
	 * @param cRow
	 * @param cCol
	 * @return A list of possible cell types that would not be blocked by a border within the specified location.
	 */
	private static ArrayList<CellType> possibleTargetCells(int rows, int cols, int cRow, int cCol) {
		ArrayList<CellType> result = new ArrayList<>();
		if (cRow > 0) {
			result.add(CellType.END_U);
		}
		if (cCol < cols - 1) {
			result.add(CellType.END_R);
		}
		if (cCol > 0) {
			result.add(CellType.END_L);
		}
		if (cRow < rows - 1) {
			result.add(CellType.END_D);
		}
		return result;
	}
	
	/**
	 * Fills the grid
	 * @param grid
	 * @return true if the generation constraints were satisfied.
	 */
	private static boolean generateGridBody(Grid grid) {
		int free = grid.getRows()*grid.getCols() - 2;
		// Must have at least one free cell
		if (free <= 0) {
			return false;
		}

		// Can we place enough cells to reach the goal?
		Position manhattan = grid.getManhattanComponents();
		int minPathCells = manhattan.row + manhattan.col; 
		if (minPathCells >= free) {
			return false;
		}
		
		// Generate a list of possible positions for cell placement
		ArrayList<Integer> posList = new ArrayList<>();
		for (int row = 0; row < grid.getRows(); row++) {
			for (int col = 0; col < grid.getCols(); col++) {
				if (row == grid.getSRow() && col == grid.getSCol())
					continue;
				if (row == grid.getGRow() && col == grid.getGCol())
					continue;
				posList.add(row*grid.getCols() + col);
			}
		}
		
		// Place random cells within the grid body.
		int toPlace = rand().nextInt(minPathCells, free);
		int minHinges = grid.getTurnsNeeded();
		for (int i = 0; i < toPlace; i++) {
			int hType = rand().nextInt(12);
			CellType cellType = null;
			switch(hType) {
			case 0: // 1 in 12 chance of hinge cell
				cellType = cellType != null ? cellType : CellType.PATH_UR;
			case 1: // 1 in 12 chance of hinge cell
				cellType = cellType != null ? cellType : CellType.PATH_RD;
			case 2: // 1 in 12 chance of hinge cell
				cellType = cellType != null ? cellType : CellType.PATH_DL;
			case 3: // 1 in 12 chance of hinge cell
				cellType = cellType != null ? cellType : CellType.PATH_UL;
				manhattan.row--;
				manhattan.col--;
				minHinges--;
				break;
			case 4: case 5: case 6: // 1 in 4 chance of straight path cell
				cellType = CellType.PATH_UD;
				manhattan.row--;
				break;
			case 7: case 8: case 9: // 1 in 4 chance of straight path cell
				cellType = CellType.PATH_LR;
				manhattan.col--;
				break;
			case 10: case 11: // 1 in 6 chance of block cell
				cellType = CellType.BLOCK;
				break;
			}
			// 1 in 4 chance of fixed path cell.
			boolean fixed = rand().nextInt(5) == 0 && cellType != CellType.BLOCK;
			addCellToGrid(new Cell(cellType, fixed), grid, posList);
		}
		
		// True if we still have at least on free cell
		return minHinges <= 0 && manhattan.row <= 0 && manhattan.col <= 0 && posList.size() >= 1;
	}
	
	/**
	 * Add the cell randomly into one of the available positions within the grid
	 * @param cell
	 * @param grid
	 * @param positions
	 */
	private static void addCellToGrid(Cell cell, Grid grid, ArrayList<Integer> positions) {
		int hPos = rand().nextInt(positions.size());
		int hRow = positions.get(hPos) / grid.getCols();
		int hCol = positions.get(hPos) % grid.getCols();
		grid.set(hRow, hCol, cell);
		positions.remove(hPos);
	}
	
	/**
	 * @return The current thread's local @Random object.
	 */
	private static ThreadLocalRandom rand() {
		return ThreadLocalRandom.current();
	}
}
