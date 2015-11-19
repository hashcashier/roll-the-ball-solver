package terms;

import expressions.Expression;
import expressions.Term;

public class Variable extends Term{
	
	public Variable(String variable) {
		super(0, null, variable);
	}

	public boolean occursIn(Expression e) {
		return false;
	}
}
