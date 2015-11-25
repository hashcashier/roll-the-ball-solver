package grid.test;

import grid.Cell;
import grid.CellType;
import grid.Grid;
import roll_the_ball.Main;
import search.Solution;

public class Test1 {

	/* 4 Step Solution
	 ░░░░░░
	 ░�?  �?░
	 ░╞  ╡░
	 ░░░░░░
	 */
	private static Grid sGrid = new Grid(
			2,
			4,
			new Cell(CellType.END_R),
			1,
			0,
			new Cell(CellType.END_L),
			1,
			3);
	
	static {
		sGrid.set(0, 0, new Cell(CellType.PATH_LR));
		sGrid.set(0, 3, new Cell(CellType.PATH_LR));
	}
	
	public static void main(String[] args) {
		Solution s = Main.search(sGrid, "BF", true);
		System.out.println(s.getExpandedNodes() + " Expanded Nodes.");
	}
}
