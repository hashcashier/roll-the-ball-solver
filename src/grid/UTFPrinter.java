package grid;

import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class UTFPrinter extends PrintWriter {
	public UTFPrinter(PrintStream out) throws UnsupportedEncodingException {
		super(new OutputStreamWriter(out, "UTF-8"), true);
	}

}
