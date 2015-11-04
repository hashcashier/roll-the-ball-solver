package grid;

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
	
	public void setFixed(boolean fixed) {
		mFixed = fixed;
	}
	
	public boolean getFixed() {
		return mFixed;
	}
	
	public CellType getType() {
		return mType;
	}
}
