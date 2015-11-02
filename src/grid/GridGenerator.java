package grid;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class GridGenerator {
	public static int MAX_ROWS = 5;
	public static int MAX_COLS = 5;
	
	public static Grid generate() {
		int rows = rand().nextInt(1, MAX_ROWS);
		int cols = rand().nextInt(rows == 1 ? 2 : 1, MAX_COLS);
		Cell initial = (rand().nextBoolean() && cols > 1) || rows == 1 ? Cell.INITIAL_R : Cell.INITIAL_D;
		
		int gRow = rand().nextInt(cols == 1 ? 1 : 0, rows);
		int gCol = rand().nextInt(gRow == 0 ? 1 : 0, cols);
		Cell goal = randomGoalCell(rows, cols, gRow, gCol, initial);
		
		Grid grid = new Grid(rows, cols, initial, gRow, gCol, goal);
		
		generateGridBody(grid);
		
		return grid;
	}
	
	private static Cell randomGoalCell(
			int rows,
			int cols,
			int gRow,
			int gCol,
			Cell initial) {
		ArrayList<Cell> possibleGoalCells = new ArrayList<>();
		if (gRow > 0) {
			possibleGoalCells.add(Cell.GOAL_U);
		}
		if (gCol > 0) {
			possibleGoalCells.add(Cell.GOAL_L);
		}
		if (gRow < rows - 1) {
			possibleGoalCells.add(Cell.GOAL_D);
		}
		if (gCol < cols - 1) {
			possibleGoalCells.add(Cell.GOAL_R);
		}
		if (gRow == 0 && gCol == 1) {
			if (initial == Cell.INITIAL_R) {
				possibleGoalCells.clear();
				possibleGoalCells.add(Cell.GOAL_L);
			} else {
				possibleGoalCells.remove(Cell.GOAL_L);
			}
		} else if (gCol == 0 && gRow == 1) {
			if (initial == Cell.INITIAL_D) {
				possibleGoalCells.clear();
				possibleGoalCells.add(Cell.GOAL_U);
			} else {
				possibleGoalCells.remove(Cell.GOAL_U);
			}
		}
		return possibleGoalCells.get(rand().nextInt(possibleGoalCells.size()));
	}
	
	private static void generateGridBody(Grid grid) {
		int free = grid.getRows()*grid.getCols() - 2;
		if (free == 0) {
			return;
		}
		Cell start = grid.get(0, 0);
		int startR = start == Cell.INITIAL_D ? 1 : 0;
		int startC = start == Cell.INITIAL_R ? 1 : 0;
		int gRow = grid.getGRow();
		int gCol = grid.getGCol();
		Cell end = grid.get(gRow, gCol);
		int finishR = end == Cell.GOAL_D ? gRow + 1 : end == Cell.GOAL_U ? gRow - 1 : gRow;
		int finishC = end == Cell.GOAL_R ? gCol + 1 : end == Cell.GOAL_L ? gCol - 1 : gCol;
		
	}
	
	private static ThreadLocalRandom rand() {
		return ThreadLocalRandom.current();
	}
}
