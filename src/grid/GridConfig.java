package grid;

import java.util.Arrays;

public class GridConfig {
	/**
	 * OPEN: A truth table for each cell type depicting if it is open from each direction.
	 * DROW: A table depicting the change in row value for each direction.
	 * DCOL: A table depicting the change in column value for each direction.
	 * 0 Up
	 * 1 Right
	 * 2 Left
	 * 3 Down
	 */
	public static int DIRECTIONS = 4;
	private static int CELL_TYPES = Cell.class.getEnumConstants().length;
	private static boolean[][] OPEN;
	private static int[] DROW;
	private static int[] DCOL;
	private static char[] REPRESENTATION;
	static {
		OPEN = new boolean[CELL_TYPES][DIRECTIONS];
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

		REPRESENTATION = new char[CELL_TYPES];
		REPRESENTATION[Cell.BLANK.ordinal()] = ' ';
		REPRESENTATION[Cell.BLOCK.ordinal()] = '■';
		REPRESENTATION[Cell.INITIAL_R.ordinal()] = '╞';
		REPRESENTATION[Cell.INITIAL_D.ordinal()] = '╥';
		REPRESENTATION[Cell.GOAL_U.ordinal()] = '╨';
		REPRESENTATION[Cell.GOAL_R.ordinal()] = '╞';
		REPRESENTATION[Cell.GOAL_L.ordinal()] = '╡';
		REPRESENTATION[Cell.GOAL_D.ordinal()] = '╥';
		REPRESENTATION[Cell.PATH_DL.ordinal()] = '╗';
		REPRESENTATION[Cell.PATH_LR.ordinal()] = '═';
		REPRESENTATION[Cell.PATH_RD.ordinal()] = '╔';
		REPRESENTATION[Cell.PATH_UD.ordinal()] = '║';
		REPRESENTATION[Cell.PATH_UL.ordinal()] = '╝';
		REPRESENTATION[Cell.PATH_UR.ordinal()] = '╚';
/*		REPRESENTATION = new char[CELL_TYPES];
		REPRESENTATION[Cell.BLANK.ordinal()] = ' ';
		REPRESENTATION[Cell.BLOCK.ordinal()] = 'X';
		REPRESENTATION[Cell.INITIAL_R.ordinal()] = '<';
		REPRESENTATION[Cell.INITIAL_D.ordinal()] = '^';
		REPRESENTATION[Cell.GOAL_U.ordinal()] = 'V';
		REPRESENTATION[Cell.GOAL_R.ordinal()] = '<';
		REPRESENTATION[Cell.GOAL_L.ordinal()] = '>';
		REPRESENTATION[Cell.GOAL_D.ordinal()] = '^';
		REPRESENTATION[Cell.PATH_DL.ordinal()] = '╗';
		REPRESENTATION[Cell.PATH_LR.ordinal()] = '═';
		REPRESENTATION[Cell.PATH_RD.ordinal()] = '╔';
		REPRESENTATION[Cell.PATH_UD.ordinal()] = '║';
		REPRESENTATION[Cell.PATH_UL.ordinal()] = '╝';
		REPRESENTATION[Cell.PATH_UR.ordinal()] = '╚';
*/
		
}
	
	public static boolean cellOpen(Cell cell, int direction) {
		return OPEN[cell.ordinal()][direction];
	}

	public static boolean cellOppositeOpen(Cell cell, int direction) {
		return OPEN[cell.ordinal()][3 - direction];
	}
	
	public static int deltaRow(int direction) {
		return DROW[direction];
	}
	
	public static int deltaCol(int direction) {
		return DCOL[direction];
	}
	
	public static char getRepresentation(Cell cell) {
		return REPRESENTATION[cell.ordinal()];
	}
	
}
