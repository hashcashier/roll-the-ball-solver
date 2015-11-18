package folExpressions;

import folSymbols.*;
import folSymbols.syncategorematicSymbols.*;

public abstract class Sentence {

	public Sentence() {
	}
	// should we leave these as functions?
	// should sentence be so abstract?
	public abstract Sentence negate(Sentence sentence);
	public abstract Sentence equate(Term t1, Term t2);
	public abstract Sentence applyConnector(Sentence sentence1, Sentence sentence2, Connector connector );
	public abstract Sentence applyQuantifier(Quantifier quantifier, Variable var, Sentence sentence);

}
