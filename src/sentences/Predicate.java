package sentences;

import expressions.Sentence;
import expressions.Term;

public class Predicate extends Sentence{
	public Predicate(int arity, Term[] terms, String symbol) {
		super(arity, terms, symbol);
	}
	
	public Term[] getChildren() {
		return (Term[]) super.getChildren();
	}

}