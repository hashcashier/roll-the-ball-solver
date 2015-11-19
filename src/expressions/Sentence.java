package expressions;

public abstract class Sentence extends Expression {

	public Sentence(int arity, Expression[] expressions, String symbol) {
		super(arity, expressions, symbol);
	}

}
