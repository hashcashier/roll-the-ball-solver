package grid;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class GridGenerator {
	public static int MAX_ROWS = 7;
	public static int MAX_COLS = 7;
	
	public static Grid generate() {
		int rows = rand().nextInt(1, MAX_ROWS);
		int cols = rand().nextInt(rows == 1 ? 2 : 1, MAX_COLS);
		
		int sPos = rand().nextInt(rows*cols);
		int gPos = rand().nextInt(rows*cols - 1);
		
		if (sPos == gPos) {
			gPos++;
		}
		
		int sRow = sPos / cols;
		int sCol = sPos % cols;
		Cell initial = randomStartCell(rows, cols, sRow, sCol);
		initial.fixed = true;
		
		int gRow = gPos / cols;
		int gCol = gPos % cols;
		Cell goal = randomGoalCell(rows, cols, gRow, gCol, sRow, sCol, initial);
		if (goal == null) {
			return generate();
		}
		goal.fixed = true;
		
		Grid grid = new Grid(rows, cols, initial, gRow, gCol, goal, sRow, sCol);
		
		generateGridBody(grid);
		
		return grid;
	}
	
	private static Cell randomStartCell(int rows, int cols, int sRow, int sCol) {
		ArrayList<Cell> possibleGoalCells = possibleTargetCells(rows, cols, sRow, sCol);
		return possibleGoalCells.get(rand().nextInt(possibleGoalCells.size()));
	}
	
	private static Cell randomGoalCell(int rows, int cols, int gRow, int gCol, int sRow, int sCol, Cell initial) {
		ArrayList<Cell> possibleGoalCells = possibleTargetCells(rows, cols, gRow, gCol);
		if (gRow == sRow && Math.abs(sCol-gCol) == 1) {
			if (initial == Cell.END_R) {
				possibleGoalCells.clear();
				possibleGoalCells.add(Cell.END_L);
			} else {
				possibleGoalCells.remove(Cell.END_L);
			}
		} else if (gCol == sCol && Math.abs(gRow-sRow) == 1) {
			if (initial == Cell.END_D) {
				possibleGoalCells.clear();
				possibleGoalCells.add(Cell.END_U);
			} else {
				possibleGoalCells.remove(Cell.END_U);
			}
		}
		int possibilities = possibleGoalCells.size();
		return possibilities == 0 ? null : possibleGoalCells.get(rand().nextInt(possibilities));
	}
	
	private static ArrayList<Cell> possibleTargetCells(int rows, int cols, int cRow, int cCol) {
		ArrayList<Cell> result = new ArrayList<>();
		if (cRow > 0) {
			result.add(Cell.END_U);
		}
		if (cCol < cols - 1) {
			result.add(Cell.END_R);
		}
		if (cCol > 0) {
			result.add(Cell.END_L);
		}
		if (cRow < rows - 1) {
			result.add(Cell.END_D);
		}
		return result;
	}
	
	private static void generateGridBody(Grid grid) {
		int free = grid.getRows()*grid.getCols() - 2;
		if (free == 0) {
			return;
		}
		int sRow = grid.getSRow();
		int sCol = grid.getSCol();
		Cell start = grid.get(sRow, sCol);
		int startR = start == Cell.END_D ? sRow + 1 : start == Cell.END_U ? sRow - 1 : sRow;
		int startC = start == Cell.END_R ? sCol + 1 : start == Cell.END_L ? sCol - 1 : sCol;
		int gRow = grid.getGRow();
		int gCol = grid.getGCol();
		Cell end = grid.get(gRow, gCol);
		int finishR = end == Cell.END_D ? gRow + 1 : end == Cell.END_U ? gRow - 1 : gRow;
		int finishC = end == Cell.END_R ? gCol + 1 : end == Cell.END_L ? gCol - 1 : gCol;
		
	}
	
	private static ThreadLocalRandom rand() {
		return ThreadLocalRandom.current();
	}
}
