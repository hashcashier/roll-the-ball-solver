package grid;

/**
 * Blocks, Start/End Cells and Path Cells.
 */
public enum CellType {
	BLANK,
	END_L,
	END_U,
	END_R,
	END_D,
	BLOCK,
	PATH_UD,
	PATH_LR,
	PATH_UR,
	PATH_RD,
	PATH_DL,
	PATH_UL;
}
