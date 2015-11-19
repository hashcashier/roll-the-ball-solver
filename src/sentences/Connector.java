package sentences;

import expressions.Sentence;

public class Connector extends Sentence  {
	public static enum Type {
		CONJUNCTION, DISJUNCTION, IMPLICATION, BIIMPLICATION
	}
	
	private Type mType;
	
	public Connector(Type t, Sentence phi, Sentence psi) {
		super(2, new Sentence[]{phi, psi}, null);
		mType = t;
	}
}
