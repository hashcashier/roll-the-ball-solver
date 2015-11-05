package grid;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class GridGenerator {
	public static int MAX_ROWS = 7;
	public static int MAX_COLS = 7;
	
	public static Grid genGrid() {
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
		initial.setFixed(true);
		
		int gRow = gPos / cols;
		int gCol = gPos % cols;
		Cell goal = randomGoalCell(rows, cols, gRow, gCol, sRow, sCol, initial.getType());
		if (goal == null) {
			return genGrid();
		}
		goal.setFixed(true);
		
		Grid grid = new Grid(rows, cols, initial, gRow, gCol, goal, sRow, sCol);
		
		if (!generateGridBody(grid)) {
			return genGrid();
		}
		
		return grid;
	}
	
	private static Cell randomStartCell(int rows, int cols, int sRow, int sCol) {
		ArrayList<CellType> possibleGoalCells = possibleTargetCells(rows, cols, sRow, sCol);
		return new Cell(possibleGoalCells.get(rand().nextInt(possibleGoalCells.size())));
	}
	
	private static Cell randomGoalCell(int rows, int cols, int gRow, int gCol, int sRow, int sCol, CellType initialType) {
		ArrayList<CellType> possibleGoalCells = possibleTargetCells(rows, cols, gRow, gCol);
		if (gRow == sRow && Math.abs(sCol-gCol) == 1) {
			if (initialType == CellType.END_R) {
				possibleGoalCells.clear();
				possibleGoalCells.add(CellType.END_L);
			} else {
				possibleGoalCells.remove(CellType.END_L);
			}
		} else if (gCol == sCol && Math.abs(gRow-sRow) == 1) {
			if (initialType == CellType.END_D) {
				possibleGoalCells.clear();
				possibleGoalCells.add(CellType.END_U);
			} else {
				possibleGoalCells.remove(CellType.END_U);
			}
		}
		int possibilities = possibleGoalCells.size();
		return possibilities == 0 ? null : new Cell(possibleGoalCells.get(rand().nextInt(possibilities)));
	}
	
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
	
	private static boolean generateGridBody(Grid grid) {
		int free = grid.getRows()*grid.getCols() - 2;
		if (free == 0) {
			return false;
		}
		
		int sRow = grid.getSRow();
		int sCol = grid.getSCol();
		int sPos = sRow*grid.getCols() + sCol;
		Cell start = grid.get(sRow, sCol);
		CellType startType = start.getType();
		int startR = startType == CellType.END_D ? sRow + 1 : startType == CellType.END_U ? sRow - 1 : sRow;
		int startC = startType == CellType.END_R ? sCol + 1 : startType == CellType.END_L ? sCol - 1 : sCol;
		
		int gRow = grid.getGRow();
		int gCol = grid.getGCol();
		int gPos = gRow*grid.getCols() + gCol;
		Cell end = grid.get(gRow, gCol);
		CellType endType = end.getType();
		int finishR = endType == CellType.END_D ? gRow + 1 : endType == CellType.END_U ? gRow - 1 : gRow;
		int finishC = endType == CellType.END_R ? gCol + 1 : endType == CellType.END_L ? gCol - 1 : gCol;
		
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
			CellType cellType = null;
			switch(hType) {
			case 0:
				cellType = CellType.PATH_UR;
				deltaRow--;
				deltaCol--;
				break;
			case 1:
				cellType = CellType.PATH_RD;
				deltaRow--;
				deltaCol--;
				break;
			case 2:
				cellType = CellType.PATH_DL;
				deltaRow--;
				deltaCol--;
				break;
			case 3:
				cellType = CellType.PATH_UL;
				deltaRow--;
				deltaCol--;
				break;
			case 4:
			case 5:
			case 6:
				cellType = CellType.PATH_UD;
				deltaRow--;
				break;
			case 7:
			case 8:
			case 9:
				cellType = CellType.PATH_LR;
				deltaRow--;
				break;
			case 10:
			case 11:
				cellType = CellType.BLOCK;
				break;
			}
			boolean fixed = rand().nextInt(5) == 0 && cellType != CellType.BLOCK;
			addCellToGrid(new Cell(cellType, fixed), grid, posList);
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
