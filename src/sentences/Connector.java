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
	
	public Type getType() {
		return mType;
	}
	
	public String toString() {
		return "(" + getChildren()[0].toString() + mType.getSymbol() + getChildren()[1].toString() + ")";
	}
}
