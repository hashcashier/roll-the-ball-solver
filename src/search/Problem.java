package search;

import java.util.Collection;

import search.space.Node;
import search.space.Operator;
import search.space.State;

/**
 * Problem ADT
 */
public abstract class Problem {
	private Operator[] mOperators;
	private State mInitState;
	private Node mInitNode;
	private StateSpace mStateSpace = new StateSpace();

	public Problem(State initState) {
		setInitState(initState);
	}

	/**
	 * @param state
	 * @return true iff state is a goal state.
	 */
	public abstract boolean goalTest(State state);

	/**
	 * @param node
	 * @return the cost of the path from the root to node
	 */
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

	/**
	 * Add the target state to the current problem's @StateSpace
	 * @param state
	 */
	public void addToStateSpace(State state) {
		mStateSpace.add(state);
	}
	
	/**
	 * Add the target states to the current problem's @StateSpace
	 * @param state
	 */
	public void addToStateSpace(Collection<State> states) {
		mStateSpace.addAll(states);
	}

	/**
	 * Check if current problem's @StateSpace contains the target state
	 * @param state
	 */
	public boolean stateSpaceContains(State state) {
		return mStateSpace.contains(state);
	}
	
	/**
	 * Clear the current problem's @StateSpace
	 */
	public void clearStateSpace() {
		mStateSpace.clear();
	}
	
	/**
	 * Remove the target state from the current problem's @StateSpace
	 * @param state
	 */
	public void removeFromStateSpace(State state) {
		mStateSpace.remove(state);
	}

}
