package roll_the_ball;

import grid.Direction;
import grid.Grid;
import search.Problem;
import search.space.Node;
import search.space.Operator;
import search.space.State;

/**
 * Implements the @Problem ADT
 * Specific to the Roll The Ball problem.
 */
public class RollTheBallProblem extends Problem {
	
	/**
	 * Create operators to move cells in all 4 directions
	 */
	private Operator[] mOperators = new Operator[]{
			new MoveBlankOperator(this, Direction.UP),
			new MoveBlankOperator(this, Direction.RIGHT),
			new MoveBlankOperator(this, Direction.LEFT),
			new MoveBlankOperator(this, Direction.DOWN)
	};

	public RollTheBallProblem(Grid initState) {
		super(initState);
		setOperators(mOperators);
	}
	
	/**
	 * Test if the target state is solved by checking if the grid is connected.
	 */
	@Override
	public boolean goalTest(State currState) {
		Grid grid = (Grid) currState;
		return grid.isSolved();
	}

	/**
	 * Trivial case. Path cost is number of moves, or depth of node.
	 */
	@Override
	public int pathCost(Node node) {
		return node.getNodeDepth();
	}

}
