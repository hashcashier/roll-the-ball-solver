package terms;

import expressions.Expression;
import expressions.Term;

public class Variable extends Term{
	
	public Variable(String variable) {
		super(0, null, variable);
	}

	public boolean occursIn(Expression e) {
		if (e == this) {
			return true;
		}
		for (int i = 0; i < e.getArity(); i++) {
			if (occursIn(e.getChildren()[i]))
				return true;
		}
		return false;
	}
	
}
