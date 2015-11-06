package test;

import grid.Cell;
import grid.CellType;
import grid.Grid;
import roll_the_ball.Main;

public class Test3 {

	/* 14 Step Solution
	 ░░░░░░░░░
	 ░  ═ ═  ░
	 ░ ═ ═ ═ ░
	 ░       ░
	 ░╞  ■  ╡░
	 ░░░░░░░░░
	 */
	private static Grid sGrid = new Grid(
			4,
			7,
			new Cell(CellType.END_R),
			3,
			0,
			new Cell(CellType.END_L),
			3,
			6);
	
	static {
		sGrid.set(1, 1, new Cell(CellType.PATH_LR));
		sGrid.set(0, 2, new Cell(CellType.PATH_LR));
		sGrid.set(1, 3, new Cell(CellType.PATH_LR));
		sGrid.set(0, 4, new Cell(CellType.PATH_LR));
		sGrid.set(1, 5, new Cell(CellType.PATH_LR));
		sGrid.set(3, 3, new Cell(CellType.BLOCK));
	}
	
	public static void main(String[] args) {
		Main.search(sGrid, "BF", true);
	}
}
