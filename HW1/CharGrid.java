// HW1 2-d array Problems
// CharGrid encapsulates a 2-d grid of chars and supports
// a few operations on the grid.

package assign1;

public class CharGrid {
	private char[][] grid;

	/**
	 * Constructs a new CharGrid with the given grid.
	 * Does not make a copy.
	 * @param grid
	 */
	public CharGrid(char[][] grid) {
		this.grid = grid;
	}
	
	/**
	 * Returns the area for the given char in the grid. (see handout).
	 * @param ch char to look for
	 * @return area for given char
	 */
	public int charArea(char ch) {
		int x1 = grid[0].length + 1, x2 = -1;
		int y1 = grid.length + 1, y2 = -1;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] == ch) {
					if (j <= x1) x1 = j;
					if (j >= x2) x2 = j;
					if (i <= y1) y1 = i;
					if (i >= y2) y2 = i;
				}
			}
		}
		if (x2 < 0 || y2 < 0) return 0;
		return (x2 - x1 + 1) * (y2 - y1 + 1);
	}
	
	/**
	 * Returns the count of '+' figures in the grid (see handout).
	 * @return number of + in grid
	 */
	public int countPlus() {
		int numPlus = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (isPlus(grid[i][j], i, j, 1)) numPlus++;
			}
		}
		return numPlus;
	}
	
	/**
	 * Helper method for countPlus that returns true iff the char 
	 * passed as the argument makes a plus shape on the grid. 
	 */
	private boolean isPlus(char ch, int i, int j, int position) {
		if (position > 1) {
			if (((i-position >= 0) && (grid[i-position][j] != ch)) &&
					((j-position >= 0) && (grid[i][j-position] != ch))) {
				if (((i+position < grid.length) && (grid[i+position][j] != ch)) &&
						((j+position < grid[0].length) && (grid[i][j+position] != ch)))
					return true;
			}
			else if ((i-position < 0) && (j-position < 0)) {
				if ((i+position >= grid.length) && (j+position >= grid[0].length))
					return true;
			}
			else return false;
		}
		
		if (((i-position >= 0) && (grid[i-position][j] == ch)) &&
				((j-position >= 0) && (grid[i][j-position] == ch))) {
			if (((i+position < grid.length) && (grid[i+position][j] == ch)) &&
					((j+position < grid[0].length) && (grid[i][j+position] == ch)))
				return isPlus(ch, i, j, ++position);
		}
		return false;
	}
	
}
