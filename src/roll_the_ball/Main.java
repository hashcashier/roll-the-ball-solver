package roll_the_ball;

import grid.Grid;
import grid.GridGenerator;
import search.Problem;
import search.Search;
import search.Solution;
import search.Strategy;
import search.space.Node;
import search.strategies.AStarStrategy;
import search.strategies.BreadthFirstStrategy;
import search.strategies.DepthFirstStrategy;
import search.strategies.GreedyManhattanStrategy;
import search.strategies.GreedyTurnStrategy;
import search.strategies.IterativeDeepeningStrategy;

public class Main {

	public static Solution search(Grid grid, String strategy, boolean visualize) {
		Strategy searchStrategy;
		switch(strategy) {
		case "BF":
			searchStrategy = new BreadthFirstStrategy();
			break;
		case "DF":
			searchStrategy = new DepthFirstStrategy();
			break;
		case "ID":
			searchStrategy = new IterativeDeepeningStrategy();
			break;
		case "GR1":
			searchStrategy = new GreedyTurnStrategy();
			break;
		case "GR2":
			searchStrategy = new GreedyManhattanStrategy();
			break;
		case "AS":
		default:
			searchStrategy = new AStarStrategy();
			break;
		}
		Problem problem = new RollTheBallProblem(grid);
		Solution result = Search.search(problem, searchStrategy);
		if (visualize) {
			if (result == null) {
				System.out.println("UNSOLVABLE!");
			} else {
				for (Node node : result.getPath()) {
					System.out.println(node.getNodeState().toString());
				}
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		Grid grid = GridGenerator.genGrid();
		System.out.println(grid.toString());
		search(grid, "BF", true);
	}

}
