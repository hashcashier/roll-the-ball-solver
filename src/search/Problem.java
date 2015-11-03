package search;

import java.util.Set;

import search.space.Node;
import search.space.Operator;
import search.space.State;

/**
 * @author nouran
 *
 */
public abstract class Problem {
	private Operator[] mOperators;
	private State mInitState;
	private Set<State> mStateSpace;

	public Problem(Operator[] operators, State initState, Set<State> stateSpace) {
		setOperators(operators);
		setInitState(initState);
		setStateSpace(stateSpace);
	}

	public abstract boolean goalTest(State currState);

	public abstract int pathCost(State currState);

	public Operator[] getOperators() {
		return mOperators;
	}

	public void setOperators(Operator[] operators) {
		mOperators = operators;
	}

	public State getInitState() {
		return mInitState;
	}

	public void setInitState(State initState) {
		mInitState = initState;
	}
	
	public abstract Node getInitNode();

	public Set<State> getStateSpace() {
		return mStateSpace;
	}

	public void setStateSpace(Set<State> stateSpace) {
		mStateSpace = stateSpace;
	}

}
