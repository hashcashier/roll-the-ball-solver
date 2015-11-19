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
}
