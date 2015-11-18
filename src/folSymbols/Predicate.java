package folSymbols;

import folExpressions.Sentence;
import folExpressions.Term;

public class Predicate extends Sentence{
	private String mPredicateSymbol;
	private int mArity;
	private Term[] mTerms = new Term[mArity];

	public Predicate(String predicateSymbol, int arity, Term[] terms) {
		mPredicateSymbol = predicateSymbol;
		mArity = arity;
		mTerms = terms;
	}
}