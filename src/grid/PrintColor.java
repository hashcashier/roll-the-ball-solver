package grid;

public enum PrintColor {
	RED("\u001B[31m"),
	GREEN("\u001B[32m"),
	BLUE("\u001B[34m"),
	YELLOW("\u001B[33m"),
	PURPLE("\u001B[35m"),
	CYAN("\u001B[36m"),
	WHITE("\u001B[37m"),
	BLACK("\u001B[30m"),
	DEFAULT("\u001B[0m");

	private String mColor;

	PrintColor(String color) {
		mColor = color;
	}

	public String getColor() {
		return mColor;
	}
}
