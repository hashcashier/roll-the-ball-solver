package expressions;

public abstract class Expression {
	private int mArity;
	private Expression[] mChildren = new Term[mArity];
	private String mSymbol;
	
	public Expression(int arity, Expression[] expressions, String symbol) {
		mArity = arity;
		mChildren = expressions;
		mSymbol = symbol;
	}
	
	public Expression[] getChildren() {
		return mChildren;
	}
	
	public String getName() {
		return mSymbol;
	}
	
	public int getArity() {
		return mArity;
	}
	
	public String toString() {
		return toString("(", ")");
	}

	protected String toString(String openBracket, String closeBracket) {
		String result = getName();
		if (mArity > 0) {
			result += openBracket;
			for (int i = 0; i < mArity; i++) {
				if (i > 0) result += ", ";
				result += getChildren()[i].toString();
			}
			result += closeBracket;
		}
		
		return result;
	}
	
	public boolean equals(Expression other) {
		boolean same = getName().equals(other.getName()) && mArity == other.mArity;
		for (int i = 0; same && i < mArity; i++) {
			same &= mChildren[i].equals(other.mChildren[i]);
		}
		return same;
	}
}
