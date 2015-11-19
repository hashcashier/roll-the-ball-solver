package sentences;

import expressions.Sentence;
import terms.Variable;

public class Quantifier extends Sentence {
	public static enum Type {
		UNIVERSAL, EXISTENTIAL
	}
	
	private Type mType;
	private Variable mVariable;
	
	public Quantifier(Type t, Variable v, Sentence phi) {
		super(1, new Sentence[]{phi}, null);
		mType = t;
		mVariable = v;
	}
}
