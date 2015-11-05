package search;

import java.util.Collection;

import search.space.Node;
import search.space.Operator;
import search.space.State;

public abstract class Problem {
	private Operator[] mOperators;
	private State mInitState;
	private Node mInitNode;
	private StateSpace mStateSpace = new StateSpace();

	public Problem(State initState) {
		setInitState(initState);
	}

	public abstract boolean goalTest(State currState);

	public abstract int pathCost(Node node);

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
		mInitNode = new Node(mInitState);
	}
	
	public Node getInitNode() {
		return mInitNode;
	}

	public void addToStateSpace(State state) {
		mStateSpace.add(state);
	}
	
	public void addToStateSpace(Collection<State> states) {
		mStateSpace.addAll(states);
	}

	public boolean stateSpaceContains(State state) {
		return mStateSpace.contains(state);
	}
	
	public void clearStateSpace() {
		mStateSpace.clear();
	}
	
	public void removeFromStateSpace(State state) {
		mStateSpace.remove(state);
	}

}
