package search;

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
}
