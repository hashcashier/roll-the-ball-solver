package roll_the_ball;

import java.util.Set;

/**
 * @author nouran
 *
 */
public abstract class Problem {
	private int[] operators;
	private State<?> initState;
	private Set<State<?>> stateSpace;

	public Problem(int[] operators, State<?> initState, Set<State<?>> stateSpace) {
		this.setOperators(operators);
		this.setInitState(initState);
		this.setStateSpace(stateSpace);
	}

	public abstract boolean goalTest(State<?> currState);

	public abstract int pathCost(State<?> currState);

	public int[] getOperators() {
		return operators;
	}

	public void setOperators(int[] operators) {
		this.operators = operators;
	}

	public State<?> getInitState() {
		return initState;
	}

	public void setInitState(State<?> initState) {
		this.initState = initState;
	}

	public Set<State<?>> getStateSpace() {
		return stateSpace;
	}

	public void setStateSpace(Set<State<?>> stateSpace) {
		this.stateSpace = stateSpace;
	}

}
