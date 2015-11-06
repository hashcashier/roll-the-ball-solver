package grid;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

/**
 * Prints colored output to the console window in UTF-8 encoding.
 */
public class ColorPrinter extends UTFPrinter {
	public ColorPrinter(PrintStream out) throws UnsupportedEncodingException {
		super(out);
	}
	
	/**
	 * Wrap a string in color tags before printing to the console.
	 * @param color
	 * @param str
	 */
	public void print(PrintColor color, String str) {
		print(color.getColor() + str + PrintColor.DEFAULT.getColor());
		flush();
	}
	
	public void red(String str) {
		print(PrintColor.RED, str);
	}

	public void green(String str) {
		print(PrintColor.GREEN, str);
	}

	public void blue(String str) {
		print(PrintColor.BLUE, str);
	}
	
	@Override
	public void close() {
		// Do nothing by default.
	}
}
