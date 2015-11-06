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
	public static int DIRECTIONS = Direction.class.getEnumConstants().length;
	public static String BORDER_REP = "░";
	private static int CELL_TYPES = CellType.class.getEnumConstants().length;
	private static boolean[][] OPEN;
	private static int[] DROW;
	private static int[] DCOL;
	private static String[] REPRESENTATION;
	static {
		OPEN = new boolean[CELL_TYPES][DIRECTIONS];
		for (int i = 0; i < CELL_TYPES; i++) {
			Arrays.fill(OPEN[i], false);
		}
		OPEN[CellType.END_U.ordinal()][0] 		= true;
		OPEN[CellType.END_R.ordinal()][1] 		= true;
		OPEN[CellType.END_D.ordinal()][3] 		= true;
		OPEN[CellType.END_L.ordinal()][2] 		= true;
		OPEN[CellType.PATH_DL.ordinal()][3] 	= true;
		OPEN[CellType.PATH_DL.ordinal()][2] 	= true;
		OPEN[CellType.PATH_LR.ordinal()][2] 	= true;
		OPEN[CellType.PATH_LR.ordinal()][1] 	= true;
		OPEN[CellType.PATH_RD.ordinal()][1] 	= true;
		OPEN[CellType.PATH_RD.ordinal()][3] 	= true;
		OPEN[CellType.PATH_UD.ordinal()][0] 	= true;
		OPEN[CellType.PATH_UD.ordinal()][3] 	= true;
		OPEN[CellType.PATH_UL.ordinal()][0] 	= true;
		OPEN[CellType.PATH_UL.ordinal()][2] 	= true;
		OPEN[CellType.PATH_UR.ordinal()][0] 	= true;
		OPEN[CellType.PATH_UR.ordinal()][1] 	= true;
		
		DROW = new int[]{-1, 0, 0, 1};
		DCOL = new int[]{0, 1, -1, 0};

		REPRESENTATION = new String[CELL_TYPES];
		REPRESENTATION[CellType.BLANK.ordinal()] = " ";
		REPRESENTATION[CellType.BLOCK.ordinal()] = "■";
		REPRESENTATION[CellType.END_U.ordinal()] = "╨";
		REPRESENTATION[CellType.END_R.ordinal()] = "╞";
		REPRESENTATION[CellType.END_L.ordinal()] = "╡";
		REPRESENTATION[CellType.END_D.ordinal()] = "╥";
		REPRESENTATION[CellType.PATH_DL.ordinal()] = "╗";
		REPRESENTATION[CellType.PATH_LR.ordinal()] = "═";
		REPRESENTATION[CellType.PATH_RD.ordinal()] = "╔";
		REPRESENTATION[CellType.PATH_UD.ordinal()] = "║";
		REPRESENTATION[CellType.PATH_UL.ordinal()] = "╝";
		REPRESENTATION[CellType.PATH_UR.ordinal()] = "╚";		
}
	
	public static boolean cellOpen(CellType cell, int direction) {
		return OPEN[cell.ordinal()][direction];
	}

	public static boolean cellOppositeOpen(CellType cell, int direction) {
		return OPEN[cell.ordinal()][3 - direction];
	}
	
	public static int deltaRow(int direction) {
		return DROW[direction];
	}
	
	public static int deltaCol(int direction) {
		return DCOL[direction];
	}
	
	public static String getRepresentation(CellType cell) {
		return REPRESENTATION[cell.ordinal()];
	}
	
}
