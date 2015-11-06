package test;

import grid.Cell;
import grid.CellType;
import grid.Grid;
import roll_the_ball.Main;

public class Test4 {

	/* 15 Step Solution
		░░░░░░░
		░╞  ╗╝░
		░══╥╝╝░
		░■■║║ ░
		░ ═║═■░
		░╗╚ ║═░
		░░░░░░░
	 */
	private static Grid sGrid = new Grid(
			5,
			5,
			new Cell(CellType.END_R),
			0,
			0,
			new Cell(CellType.END_D),
			1,
			2);
	
	static {
		sGrid.set(0, 3, new Cell(CellType.PATH_DL).setFixed(true));
		sGrid.set(0, 4, new Cell(CellType.PATH_UL));
		
		sGrid.set(1, 0, new Cell(CellType.PATH_LR));
		sGrid.set(1, 1, new Cell(CellType.PATH_LR));
		sGrid.set(1, 3, new Cell(CellType.PATH_UL));
		sGrid.set(1, 4, new Cell(CellType.PATH_UL));
		
		sGrid.set(2, 0, new Cell(CellType.BLOCK));
		sGrid.set(2, 1, new Cell(CellType.BLOCK));
		sGrid.set(2, 2, new Cell(CellType.PATH_UD));
		sGrid.set(2, 3, new Cell(CellType.PATH_UD));
		
		sGrid.set(3, 1, new Cell(CellType.PATH_LR).setFixed(true));
		sGrid.set(3, 2, new Cell(CellType.PATH_UD));
		sGrid.set(3, 3, new Cell(CellType.PATH_LR));
		sGrid.set(3, 4, new Cell(CellType.BLOCK));
		
		sGrid.set(4, 0, new Cell(CellType.PATH_RD));
		sGrid.set(4, 1, new Cell(CellType.PATH_UR));
		sGrid.set(4, 3, new Cell(CellType.PATH_UD));
		sGrid.set(4, 4, new Cell(CellType.PATH_LR));
	}
	
	public static void main(String[] args) {
		Main.search(sGrid, "AS", true);
	}
}
