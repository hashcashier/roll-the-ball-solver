package sentences;

import expressions.Sentence;
import expressions.Term;

public class Equality extends Sentence {
	public Equality(Term t1, Term t2) {
		super(2, new Term[]{t1, t2}, null);
	}
}
