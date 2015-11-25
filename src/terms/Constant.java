package terms;

import expressions.Term;

public class Constant extends Term {
	
	public Constant(String constant) {
		super(0, null, constant);
	}
}
