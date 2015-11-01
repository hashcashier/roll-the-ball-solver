package grid;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class GridGenerator {
	public static int MAX_ROWS = 5;
	public static int MAX_COLS = 5;
	
	public static Grid generate() {
		int rows = rand().nextInt(1, MAX_ROWS);
		int cols = rand().nextInt(rows == 1 ? 2 : 1, MAX_COLS);
		Cell initial = rand().nextBoolean() ? Cell.INITIAL_R : Cell.INITIAL_D;
		
		int gRow = rand().nextInt(rows);
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
		
	}
	
	private static ThreadLocalRandom rand() {
		return ThreadLocalRandom.current();
	}
}
