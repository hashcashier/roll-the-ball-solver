package grid;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class ColorPrinter extends UTFPrinter {
	public ColorPrinter(PrintStream out) throws UnsupportedEncodingException {
		super(out);
	}
	
	public void print(PrintColor color, String str) {
		print(color.getColor());
		print(str);
		print(PrintColor.DEFAULT.getColor());
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
		
	}
}
