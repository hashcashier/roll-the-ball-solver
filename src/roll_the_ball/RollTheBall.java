package roll_the_ball;

import java.util.Set;

public class RollTheBall extends Problem {

	public RollTheBall(int[] operators, State<?> initState,
			Set<State<?>> stateSpace) {
		super(operators, initState, stateSpace);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean goalTest(State<?> currState) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int pathCost(State<?> currState) {
		// TODO Auto-generated method stub
		return 0;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
