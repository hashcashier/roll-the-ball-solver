package expressions;

public abstract class Term extends Expression {

	public Term(int arity, Term[] terms, String symbol) {
		super(arity, terms, symbol);
	}

}
