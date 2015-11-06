package grid;

/**
 * Generate a grid and print it.
 */
public class GridTest {

	public static void main(String[] args) {
		Grid test = GridGenerator.genGrid();
		System.out.println(test.toString());
	}

}
