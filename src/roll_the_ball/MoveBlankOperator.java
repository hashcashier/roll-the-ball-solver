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

/**
 * Implements the @Operator ADT
 * Moves a blank cell in one of the configured @Direction(s)
 */
public class MoveBlankOperator extends Operator {

	// The direction this operator moves the blank cells in
	private Direction mDirection;
	// The problem this operator is concerned with
	private Problem mProblem;
	
	public MoveBlankOperator(Problem p, Direction d) {
		mProblem = p;
		mDirection = d;
	}

	/**
	 * Attempt to apply the operator on each blank in the node state's grid.
	 */
	@Override
	public Collection<Node> apply(Node node) {
		// List of child nodes
		LinkedList<Node> result = new LinkedList<>();
		Grid grid = (Grid) node.getNodeState();
		for (int i = 0; i < grid.getRows(); i++) {
			for (int j = 0; j < grid.getCols(); j++) {
				Cell current = grid.get(i, j);
				
				try {
					// Attempt to move only blank cells.
					if (current.getType() != CellType.BLANK) {
						continue;
					}
				} catch (NullPointerException e) {
					System.out.println("MEMORY ERROR: " + i + " " + j);
					throw e;
				}
				
				// Find target cell
				int targetRow = i + GridConfig.deltaRow(mDirection.ordinal());
				int targetCol = j + GridConfig.deltaCol(mDirection.ordinal());
				// Is the target outside the board?
				if (!grid.inRange(targetRow, targetCol)) {
					continue;
				}
				// Check if the target is movable and is non-blank
				Cell target = grid.get(targetRow, targetCol);
				if (target.getFixed() || target.getType() == CellType.BLANK) {
					continue;
				}
				// Duplicate the state and move the cell
				Grid nextState = new Grid(grid);
				nextState.set(targetRow, targetCol, current);
				nextState.set(i, j, target);
				// Create the child node
				Node nextNode = new Node(nextState, node, this, node.getNodeDepth() + 1);
				nextNode.setPathCost(mProblem.pathCost(nextNode));
				result.add(nextNode);
			}
		}
		return result;
	}

}
