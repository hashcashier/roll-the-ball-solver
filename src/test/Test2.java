package test;

import grid.Cell;
import grid.CellType;
import grid.Grid;
import roll_the_ball.Main;
import search.Solution;

public class Test2 {

	/* 14 Step Solution
	 ░░░░░░
	 ░═  ═░
	 ░    ░
	 ░    ░
	 ░    ░
	 ░    ░
	 ░    ░
	 ░╞  ╡░
	 ░░░░░░
	 */
	private static Grid sGrid = new Grid(
			7,
			4,
			new Cell(CellType.END_R),
			6,
			0,
			new Cell(CellType.END_L),
			6,
			3);
	
	static {
		sGrid.set(0, 0, new Cell(CellType.PATH_LR));
		sGrid.set(0, 3, new Cell(CellType.PATH_LR));
	}
	
	public static void main(String[] args) {
		Solution s = Main.search(sGrid, "ID", true);
		System.out.println(s.getExpandedNodes() + " Expanded Nodes.");
	}
}
