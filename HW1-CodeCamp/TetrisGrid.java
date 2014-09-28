//
// TetrisGrid encapsulates a tetris board and has
// a clearRows() capability.
package assign1;

public class TetrisGrid {
	
	private boolean[][] tGrid;
	
	/**
	 * Constructs a new instance with the given grid.
	 * Does not make a copy.
	 * @param grid
	 */
	public TetrisGrid(boolean[][] grid) {
		tGrid = grid;
	}
	
	
	/**
	 * Does row-clearing on the grid.
	 */
	public void clearRows() {
		boolean fullRow = true;
		int y = 0;
		while (y < tGrid[0].length) {
			for (int x = 0; x < tGrid.length; x++) {
				if (!tGrid[x][y]) fullRow = false;
			}
			if (fullRow) {
				for (int j = y; j < tGrid[0].length-1; j++) {
					for (int i = 0; i < tGrid.length; i++) {
						tGrid[i][j] = tGrid[i][j+1];
					}
				}
				for (int i = 0; i < tGrid.length; i++)
					tGrid[i][tGrid[0].length-1] = false;
			}
			else {
				y++;
				fullRow = true;
			}
		}
	}
	
	/**
	 * Returns the internal 2d grid array.
	 * @return 2d grid array
	 */
	boolean[][] getGrid() {
		return tGrid;
	}
}
