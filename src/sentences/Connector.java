package sentences;

import expressions.Sentence;

public class Connector extends Sentence  {
	public static enum Type {
		CONJUNCTION("∧"), DISJUNCTION("∨"), IMPLICATION("⇒"), BIIMPLICATION("⇔");
		String mS;
		private Type(String s) {
			mS = s;
		}
		public String getSymbol() {
			return mS;
		}
	}
	
	private Type mType;
	
	public Connector(Type t, Sentence phi, Sentence psi) {
		super(2, new Sentence[]{phi, psi}, null);
		mType = t;
	}
	
	public Connector(Type t, int arity, Sentence[] sentences) {
		super(arity, sentences, null);
		mType = t;
	}
	
	public Type getType() {
		return mType;
	}
	
	public String toString() {
		String res = "";
		for (int i = 0; i < getArity(); i++) {
			if (i > 0) res += mType.getSymbol();
			res += getChildren()[i].toString();
		}
		return "(" + res + ")";
	}
}
