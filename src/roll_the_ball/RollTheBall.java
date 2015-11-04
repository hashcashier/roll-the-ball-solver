package roll_the_ball;

import java.util.Set;

import grid.Grid;
import search.Problem;
import search.Solution;
import search.space.Node;
import search.space.Operator;
import search.space.State;

public class RollTheBall extends Problem {

	public RollTheBall(Operator[] operators, State initState,
			Set<State> stateSpace) {
		super(operators, initState, stateSpace);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean goalTest(State currState) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int pathCost(State currState) {
		// TODO Auto-generated method stub
		return 0;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public Solution search(Grid grid, String strategy, boolean visualize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Node getInitNode() {
		// TODO Auto-generated method stub
		return null;
	}

}
