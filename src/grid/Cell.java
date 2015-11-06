package grid;

/**
 * The building block of a grid. Specifies a @CellType and fixed status.
 */
public class Cell {
	private CellType mType;
	private boolean mFixed;
	
	public Cell(CellType type) {
		mType = type;
	}
	
	public Cell(CellType type, boolean fixed) {
		this(type);
		mFixed = fixed;
	}
	
	/**
	 * Sets the fixed status of the Cell
	 * @param fixed
	 * @return a reference to this instance
	 */
	public Cell setFixed(boolean fixed) {
		mFixed = fixed;
		return this;
	}
	
	public boolean getFixed() {
		return mFixed;
	}
	
	public CellType getType() {
		return mType;
	}
}
