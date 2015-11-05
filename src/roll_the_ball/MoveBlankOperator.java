package roll_the_ball;

import java.util.Collection;
import java.util.LinkedList;

import grid.Cell;
import grid.CellType;
import grid.Direction;
import grid.Grid;
import grid.GridConfig;
import search.Problem;
import search.space.Node;
import search.space.Operator;

public class MoveBlankOperator extends Operator {
	
	private Direction mDirection;
	private Problem mProblem;
	
	public MoveBlankOperator(Problem p, Direction d) {
		mProblem = p;
		mDirection = d;
	}

	@Override
	public Collection<Node> apply(Node node) {
		LinkedList<Node> result = new LinkedList<>();
		Grid grid = (Grid) node.getNodeState();
		for (int i = 0; i < grid.getRows(); i++) {
			for (int j = 0; j < grid.getCols(); j++) {
				Cell current = grid.get(i, j);
				try {
					if (current.getType() != CellType.BLANK) {
						continue;
					}
				} catch (NullPointerException e) {
					System.out.println("INDICES: " + i + " " + j);
					throw e;
				}
				
				int targetRow = i + GridConfig.deltaRow(mDirection.ordinal());
				int targetCol = j + GridConfig.deltaCol(mDirection.ordinal());
				
				if (!grid.inRange(targetRow, targetCol)) {
					continue;
				}
				
				Cell target = grid.get(targetRow, targetCol);
				if (target.getFixed() || target.getType() == CellType.BLANK) {
					continue;
				}
				
				Grid nextState = new Grid(grid);
				nextState.set(targetRow, targetCol, current);
				nextState.set(i, j, target);
				
				Node nextNode = new Node(nextState, node, this, node.getNodeDepth() + 1);
				nextNode.setPathCost(mProblem.pathCost(nextNode));
				result.add(nextNode);
			}
		}
		return result;
	}

}
