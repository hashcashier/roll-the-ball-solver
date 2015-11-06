package roll_the_ball;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import grid.Grid;
import grid.GridGenerator;
import grid.UTFPrinter;
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

/**
 * The main executable for the project.
 */
public class Main {

	// Colored grid output. Needs ANSI Color friendly console.
	private static boolean colored = false;
	// UTF-8 Output writer
	public static final PrintWriter out;
	static {
		PrintWriter writer;
		try {
			writer = new UTFPrinter(System.out);
		} catch (UnsupportedEncodingException e) {
			writer = new PrintWriter(System.out);
		}
		out = writer;
	}

	/**
	 * Combines concrete implementations of abstract components to solve the problem.
	 * @param grid
	 * @param strategy
	 * @param visualize
	 * @return A solution to the problem
	 */
	public static Solution search(Grid grid, String strategy, boolean visualize) {
		// Output initial grid.
		if (colored) {
			grid.printInColor();
		} else {
			out.println(grid.toString());
		}
		// Decide on search strategy.
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
		// Initialize problem with grid
		Problem problem = new RollTheBallProblem(grid);
		// Search for solution
		Solution result = Search.search(problem, searchStrategy);
		// Output solution
		if (visualize) {
			if (result == null) {
				out.println("UNSOLVABLE!");
			} else {
				for (Node node : result.getPath()) {
					if (colored) {
						((Grid) node.getNodeState()).printInColor();
					} else {
						out.println(node.getNodeState().toString());
					}
				}
				out.println(result.getPath().size() - 1 + " Steps");
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		colored = args.length > 0 && args[0].equals("color");
		Grid grid = GridGenerator.genGrid();
		String searchStrategy = "BF";
		if (args.length > 0) {
			searchStrategy = !colored ? args[0] : args.length > 1 ? args[1] : searchStrategy;
		}
		search(grid, searchStrategy, true);
	}

}
