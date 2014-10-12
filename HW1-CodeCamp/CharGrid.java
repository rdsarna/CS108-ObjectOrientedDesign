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
	 * Returns the area for the given char in the grid.
	 * Given a char to look for, find the smallest rectangle
	 *  that contains all the occurrences of that char and 
	 *  return the rectangle's area. If there is only a single
	 *  occurrence of the char, then the rectangle to enclose 
	 *  it is 1x1 and the area is 1. For example, given the grid…
	 *		abcd
	 *		a cb
	 *		xbca
	 *	The area for 'a' is 12 (3 x 4) while for 'c' it is 3 (3 x 1). 
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
	 * Returns the count of '+' figures in the grid.
	 * A + is made of single character in the middle and four "arms" extending 
	 * out up, down, left, and right. The arms start with the middle char and 
	 * extend until the first different character or grid edge. To count as a +, 
	 * all the arms should have two or more chars and should all be the same 
	 * length. For example, the grid below contains exactly 2 +'s...
	 *	p
	 *	p x
	 *	ppppp xxx
	 *	p y x
	 *	p yyy
	 *	zzzzzyzzz
	 *	xx y
	 * 
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
	 * Recursive helper method for countPlus that returns true iff the char 
	 * passed as the argument makes a plus shape on the grid. 
	 */
	private boolean isPlus(char ch, int i, int j, int position) {
		int newI1 = i - position, newI2 = i + position;
		int newJ1 = j - position, newJ2 = j + position;
		
		/* Base case - 
		 * If there has been even a single step where the char had arms on all
		 * sides then check if stops growing outward uniformly or if any one
		 * arm longer than the others. If it has stopped growing uniformly then
		 * it makes a plus hence return true. Otherwise its not a plus hence false.
		 */
		if (position > 1) {
			if ((((newI1 >= 0) && (grid[newI1][j] != ch)) || (newI1 < 0)) &&
					(((newJ1 >= 0) && (grid[i][newJ1] != ch)) || (newJ1 < 0))) {
				if ((((newI2 < grid.length) && (grid[newI2][j] != ch)) || (newI2 >= grid.length)) &&
						(((newJ2 < grid[0].length) && (grid[i][newJ2] != ch)) || (newJ2 >= grid[0].length)))
					return true;
			}
		}
		
		/* Recursive Step -
		 * Check if the char ch is spreading one step outwards considering i, j
		 * as the center. All arms should have the same char for this to be
		 * true. If it is true then recursively check the next step outwards.
		 */
		if (((newI1 >= 0) && (grid[newI1][j] == ch)) &&
				((newJ1 >= 0) && (grid[i][newJ1] == ch))) {
			if (((newI2 < grid.length) && (grid[newI2][j] == ch)) &&
					((newJ2 < grid[0].length) && (grid[i][newJ2] == ch)))
				return isPlus(ch, i, j, ++position);
		}
		return false;
	}
	
}
