package sentences;

import expressions.Sentence;
import expressions.Term;
import terms.Variable;

public class Negation extends Sentence {
	public Negation(Sentence phi) {
		super(1, new Sentence[]{phi}, "¬");
	}
	
	public static void main(String[] args) {
		Variable x = new Variable("x");
		Predicate p = new Predicate(1, new Term[]{x}, "P");
		Negation n = new Negation(p);
		System.out.println(n);
	}

	public String toString() {
		return toString("", "");
	}
}
