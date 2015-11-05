package search;

import java.util.Collection;
import java.util.HashSet;

import search.space.State;

public class StateSpace {
	private HashSet<String> mSet = new HashSet<>();
	
	public boolean contains(State state) {
		return mSet.contains(state.toString());
	}
	
	public void add(State state) {
		mSet.add(state.toString());
	}
	
	public void addAll(Collection<? extends State> states) {
		for (State state : states) {
			mSet.add(state.toString());
		}
	}
	
	public void clear() {
		mSet.clear();
	}
	
	public void remove(State state) {
		if (!mSet.remove(state.toString())) {
			System.out.println("TERMINATED: STATE SPACE INVARIANT VIOLATED.");
			System.exit(1);
		}
	}
}
