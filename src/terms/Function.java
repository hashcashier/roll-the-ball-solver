package terms;

import expressions.Term;
import sun.security.action.GetBooleanSecurityPropertyAction;

public class Function extends Term {

	public Function(int arity, Term[] terms, String symbol) {
		super(arity, terms, symbol);
	}

	public Term[] getChildren() {
		return (Term[]) super.getChildren();
	}
}
