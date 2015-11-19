package sentences;

import expressions.Sentence;

public class Negation extends Sentence {
	public Negation(Sentence phi) {
		super(1, new Sentence[]{phi}, null);
	}
}
