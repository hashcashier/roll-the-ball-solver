package roll_the_ball;

import grid.Direction;
import grid.Grid;
import search.Problem;
import search.space.Node;
import search.space.Operator;
import search.space.State;

public class RollTheBallProblem extends Problem {
	
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

	@Override
	public boolean goalTest(State currState) {
		Grid grid = (Grid) currState;
		return grid.isSolved();
	}

	@Override
	public int pathCost(Node node) {
		return node.getNodeDepth();
	}

}
