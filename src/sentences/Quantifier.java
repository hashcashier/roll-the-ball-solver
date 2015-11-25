package sentences;

import expressions.Sentence;
import terms.Variable;

public class Quantifier extends Sentence {
	public static enum Type {
		UNIVERSAL("∀"), EXISTENTIAL("∃");
		String mS;
		private Type(String s) {
			mS = s;
		}
		public String getSymbol() {
			return mS;
		}
	}
	
	private Type mType;
	private Variable mVariable;
	
	public Quantifier(Type t, Variable v, Sentence phi) {
		super(1, new Sentence[]{phi}, t.getSymbol() + v.getName());
		mType = t;
		mVariable = v;
	}
	
	public Type getType() {
		return mType;
	}
	
	public Variable getVariable() {
		return mVariable;
	}
	
	public String toString() {
		return toString("[", "]");
	}
}
