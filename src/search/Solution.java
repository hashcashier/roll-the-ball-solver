package search;

import java.util.List;

import search.space.Node;

public class Solution {
	private List<Node> mPath;
	private int mExpandedNodes;
	
	public Solution(List<Node> path, int expandedNodes) {
		mPath = path;
		mExpandedNodes = expandedNodes;
	}
	
	public List<Node> getPath() {
		return mPath;
	}
	
	public int getSolutionCost() {
		return mPath.get(mPath.size() - 1).getPathCost();
	}
	
	public int getExpandedNodes() {
		return mExpandedNodes;
	}
}
