package folSymbols;

import folExpressions.Term;

public class Function extends Term{
	private int mArity;
	private Term[] mTerms = new Term[mArity]; 

	public Function(String functionSymbol, int arity, Term[] terms) {
		super(functionSymbol);
		mArity = arity;
		mTerms = terms;
	}

}
