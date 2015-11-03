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
		
		if (!generateGridBody(grid)) {
			return generate();
		}
		
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
	
	private static boolean generateGridBody(Grid grid) {
		int free = grid.getRows()*grid.getCols() - 2;
		if (free == 0) {
			return false;
		}
		
		int sRow = grid.getSRow();
		int sCol = grid.getSCol();
		int sPos = sRow*grid.getCols() + sCol;
		Cell start = grid.get(sRow, sCol);
		int startR = start == Cell.END_D ? sRow + 1 : start == Cell.END_U ? sRow - 1 : sRow;
		int startC = start == Cell.END_R ? sCol + 1 : start == Cell.END_L ? sCol - 1 : sCol;
		
		int gRow = grid.getGRow();
		int gCol = grid.getGCol();
		int gPos = gRow*grid.getCols() + gCol;
		Cell end = grid.get(gRow, gCol);
		int finishR = end == Cell.END_D ? gRow + 1 : end == Cell.END_U ? gRow - 1 : gRow;
		int finishC = end == Cell.END_R ? gCol + 1 : end == Cell.END_L ? gCol - 1 : gCol;
		
		int deltaRow = Math.abs(finishR - startR);
		int deltaCol = Math.abs(finishC - startC);
		
		if (deltaRow + deltaCol >= free) {
			return false;
		}
		
		ArrayList<Integer> posList = new ArrayList<>();
		for (int i = 0; i < grid.getRows()*grid.getCols(); i++) {
			if (i != sPos && i != gPos) {
				posList.add(i);
			}
		}
		
		int nonEmpty = rand().nextInt(deltaRow + deltaCol, free);
		for (int i = 0; i < nonEmpty; i++) {
			int hType = rand().nextInt(12);
			Cell cell = null;
			switch(hType) {
			case 0:
				cell = Cell.PATH_UR;
				deltaRow--;
				deltaCol--;
				break;
			case 1:
				cell = Cell.PATH_RD;
				deltaRow--;
				deltaCol--;
				break;
			case 2:
				cell = Cell.PATH_DL;
				deltaRow--;
				deltaCol--;
				break;
			case 3:
				cell = Cell.PATH_UL;
				deltaRow--;
				deltaCol--;
				break;
			case 4:
			case 5:
			case 6:
				cell = Cell.PATH_UD;
				deltaRow--;
				break;
			case 7:
			case 8:
			case 9:
				cell = Cell.PATH_LR;
				deltaRow--;
				break;
			case 10:
			case 11:
				cell = Cell.BLOCK;
				break;
			}
			cell.fixed = rand().nextBoolean();
			addCellToGrid(cell, grid, posList);
		}
		
		return deltaRow <= 0 && deltaCol <= 0 && posList.size() >= 1;
	}
	
	private static void addCellToGrid(Cell cell, Grid grid, ArrayList<Integer> positions) {
		int hPos = rand().nextInt(positions.size());
		int hRow = positions.get(hPos) / grid.getCols();
		int hCol = positions.get(hPos) % grid.getCols();
		grid.set(hRow, hCol, cell);
		positions.remove(hPos);
	}
	
	private static ThreadLocalRandom rand() {
		return ThreadLocalRandom.current();
	}
}
