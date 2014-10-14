package assign3;

import java.util.*;

/*
 * Encapsulates a Sudoku grid to be solved.
 * This project is based on the assignment given in CS108 Stanford.
 */
public class Sudoku {
	
	/* "Spot" inner class that represents a single spot
	 * on the grid of the Sudoku game.
	 * Each Spot object knows its place on the Sudoku grid as
	 * it stores the row and column number as fields.
	 */
	private class Spot implements Comparable<Spot> {
		
		/* Properties/fields of each individual Spot */
		private final int row, col;
		private int value;
		private int part;
		
		/* Stores all possible values for a Spot that is empty
		 * according to the rules of the game */
		private Set<Integer> possibleValues;
		
		Spot(int row, int col, int value) {
			this.row = row;
			this.col = col;
			this.value = value;
			part = getPart(row, col);
		
			possibleValues = new HashSet<>();
		}
		
		Spot(Spot s) {
			this(s.row, s.col, s.value);
			part = s.part;
			possibleValues = new HashSet<>(s.possibleValues);
		}
		
		/* Sets the value for this Spot on the Solution Grid (solutionGrid) */
		void setValue(int val) {
			value = val;
		}
		
		/* Returns the value of this Spot */
		int getValue() {
			return value;
		}
		
		/* Returns the part of the grid where this Spot belongs */
		int getPartForSpot() {
			return part;
		}
		
		/* Returns true iff this Spot is not filled */
		boolean isEmpty() {
			return value == 0;
		}
		
		/* Returns a HashSet of all legal values that can be 
		 * filled in this Spot.
		 */
		Set<Integer> getPossibleValues() {
			if (value != 0) return null;

			/* temporarily assign all 9 numbers */
			for (int i = 1; i <= Sudoku.SIZE; i++)
				possibleValues.add(i);
			
			/* Remove all the values that cannot be placed at this Spot */
			possibleValues.removeAll(valInRows.get(row));
			possibleValues.removeAll(valInCols.get(col));
			possibleValues.removeAll(valInParts.get(part));
			return possibleValues;
		}
		
		/* Updates the Spot on the solution grid with the row and col of 
		 * this Spot with the current value that this spot holds.
		 */
		void updateValueInGrid() {
			solutionGrid[row][col].value = value;
		}
		
		/* Returns the row number this Spot belongs to */
		int getRow() {
			return row;
		}

		/* Returns the column number this Spot belongs to */
		int getCol() {
			return col;
		}
		
		@Override
		public int compareTo(Spot that) {
			return this.possibleValues.size() - that.possibleValues.size();
		}
		
		/* Two Spots are equal if their row and column number are equal */
		@Override
		public boolean equals(Object o) {
			if (o == null) return false;
			if (!(o instanceof Spot)) return false;
			
			Spot that = (Spot) o;
			return this.row == that.row && this.col == that.col;
		}
		
		@Override
		public int hashCode() {
			return possibleValues.size() * 25;
		}
		
		@Override
		public String toString() {
			return String.valueOf(value);
		}
		
		/* Helper method that returns the Part in which the 
		 * coordinates x and y belong on the grid. 
		 */
		private final int getPart(int x, int y) {
			if (x < 3) {
				if (y < 3) return PART1;
				else if (y < 6) return PART4;
				else return PART7;
			}
			if (x < 6) {
				if (y < 3) return PART2;
				else if (y < 6) return PART5;
				else return PART8;
			}
			else {
				if (y < 3) return PART3;
				else if (y < 6) return PART6;
				else return PART9;
			}	
		}
	} // End of Spot class
	
	/* Member variables for the puzzle and solution grids */
	private final Spot[][] puzzleGrid;
	private final Spot[][] solutionGrid;
	
	/* List of all the possible solutions for the puzzle represented as
	 * an ArrayList of only the Spots that needed to be filled with a 
	 * solution */
	private final List<ArrayList<Spot>> solutions;
	
	/* The ivars to store the current State of the Grid.
	 * valInRows:- has a HashSet at each index that stores all the filled
	 * in values for that particular row
	 * valInCols:- Same as valInRows but for the columns
	 * valInParts:- For the 3x3 parts of the grid */
	private List<HashSet<Integer>> valInRows, valInCols, valInParts;
	
	private long timeTakenForSolution;
	
	/* Parts of the grid each of size 3x3. Counting from the
	 * top left to top right then the next row below.
	 * 0 1 2
	 * 3 4 5
	 * 6 7 8
	 */
	private static final int PART1 = 0;
	private static final int PART2 = 1;
	private static final int PART3 = 2;
	private static final int PART4 = 3;
	private static final int PART5 = 4;
	private static final int PART6 = 5;
	private static final int PART7 = 6;
	private static final int PART8 = 7;
	private static final int PART9 = 8;
		
	// Provided easy 1 6 grid
	public static final int[][] easyGrid = Sudoku.stringsToGrid(
	"1 6 4 0 0 0 0 0 2",
	"2 0 0 4 0 3 9 1 0",
	"0 0 5 0 8 0 4 0 7",
	"0 9 0 0 0 6 5 0 0",
	"5 0 0 1 0 2 0 0 8",
	"0 0 8 9 0 0 0 3 0",
	"8 0 9 0 4 0 2 0 0",
	"0 7 3 5 0 9 0 0 1",
	"4 0 0 0 0 0 6 7 9");
	
	
	// Provided medium 5 3 grid
	public static final int[][] mediumGrid = Sudoku.stringsToGrid(
	 "530070000",
	 "600195000",
	 "098000060",
	 "800060003",
	 "400803001",
	 "700020006",
	 "060000280",
	 "000419005",
	 "000080079");
	
	// Provided hard 3 7 grid
	// 1 solution this way, 6 solutions if the 7 is changed to 0
	public static final int[][] hardGrid = Sudoku.stringsToGrid(
	"3 7 0 0 0 0 0 8 0",
	"0 0 1 0 9 3 0 0 0",
	"0 4 0 7 8 0 0 0 3",
	"0 9 3 8 0 0 0 1 2",
	"0 0 0 0 4 0 0 0 0",
	"5 2 0 0 0 6 7 9 0",
	"6 0 0 0 2 1 0 4 0",
	"0 0 0 5 3 0 9 0 0",
	"0 3 0 0 0 0 0 5 1");
	

	
	public static final int SIZE = 9;  // size of the whole 9x9 puzzle
	public static final int PART = 3;  // size of each 3x3 part
	public static final int MAX_SOLUTIONS = 100;
	
	// Provided various static utility methods to
	// convert data formats to int[][] grid.
	
	/**
	 * Returns a 2-d grid parsed from strings, one string per row.
	 * The "..." is a Java 5 feature that essentially
	 * makes "rows" a String[] array.
	 * (provided utility)
	 * @param rows array of row strings
	 * @return grid
	 */
	public static int[][] stringsToGrid(String... rows) {
		int[][] result = new int[rows.length][];
		for (int row = 0; row<rows.length; row++) {
			result[row] = stringToInts(rows[row]);
		}
		return result;
	}


	/**
	 * Given a single string containing 81 numbers, returns a 9x9 grid.
	 * Skips all the non-numbers in the text.
	 * (provided utility)
	 * @param text string of 81 numbers
	 * @return grid
	 */
	public static int[][] textToGrid(String text) {
		int[] nums = stringToInts(text);
		if (nums.length != SIZE*SIZE) {
			throw new RuntimeException("Needed 81 numbers, but got:" + nums.length);
		}
		
		int[][] result = new int[SIZE][SIZE];
		int count = 0;
		for (int row = 0; row<SIZE; row++) {
			for (int col=0; col<SIZE; col++) {
				result[row][col] = nums[count];
				count++;
			}
		}
		return result;
	}
	
	
	/**
	 * Given a string containing digits, like "1 23 4",
	 * returns an int[] of those digits {1 2 3 4}.
	 * (provided utility)
	 * @param string string containing ints
	 * @return array of ints
	 */
	public static int[] stringToInts(String string) {
		int[] a = new int[string.length()];
		int found = 0;
		for (int i=0; i<string.length(); i++) {
			if (Character.isDigit(string.charAt(i))) {
				a[found] = Integer.parseInt(string.substring(i, i+1));
				found++;
			}
		}
		int[] result = new int[found];
		System.arraycopy(a, 0, result, 0, found);
		return result;
	}


	/**
	 * Sets up the Sudoku puzzle grid based on the integers given
	 * as a 2D array or matrix.
	 */
	public Sudoku(int[][] ints) {
		puzzleGrid = new Spot[SIZE][SIZE];
		solutionGrid = new Spot[SIZE][SIZE];
		
		solutions = new ArrayList<>();
		
		valInRows = new ArrayList<>(SIZE);
		valInCols = new ArrayList<>(SIZE);
		valInParts = new ArrayList<>(SIZE);
		
		/* Initializing all the HashSets */
		for (int i = 0; i < SIZE; i++) {
			valInRows.add(new HashSet<Integer>());
			valInCols.add(new HashSet<Integer>());
			valInParts.add(new HashSet<Integer>());
		}
		
		/* Setting up the Sudoku puzzle grid with the appropriate Spots.
		 * And adding all the values in the relevant Rows, Cols and Parts
		 * to set up the initial state of the grid. */
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				int val = ints[i][j];
				Spot newSpot = new Spot(i, j, val);
				puzzleGrid[i][j] = newSpot;
				
				if (!newSpot.isEmpty()) {
					valInRows.get(i).add(val);
					valInCols.get(j).add(val);
					valInParts.get(newSpot.getPartForSpot()).add(val);
				}
			}
		}
	}
	
	/** 
	 * Sets up the Sudoku puzzle grid based on the given text. The
	 * text must contain 81 numbers. Whitespaces are ignored.
	 * If the text is invalid, an exception is thrown.
	 * @param text string of 81 numbers
	 */
	public Sudoku(String text) {
		this(Sudoku.textToGrid(text));
	}

	/**
	 * Solves the puzzle and returns the number of solutions.
	 * @return number of solutions
	 */
	public int solve() {
		/* List of all the empty spots in the puzzleGrid */
		List<Spot> emptySpots = getEmptySpotsList();
		
		/* List of Spots that will hold the solution values for the puzzleGrid */
		List<Spot> solvedSpots = new ArrayList<>();
		
		long startTime = System.currentTimeMillis();
		
		solveSudoku(emptySpots, solvedSpots, 0);
		
		long endTime = System.currentTimeMillis();
		timeTakenForSolution = endTime - startTime;
		
		if (solutions.size() == 0) /* If no solution found */
			return 0;
		
		/* Update the solutionGrid field */
		fillSolutionGrid();
		
		return solutions.size();
	}

	/* Recursive method solves the puzzleGrid
	 * Strategy:
	 * =========
	 * 1. The emptySpots are sorted by the number of possibleValues
	 * 	  as solutions. So start with the one that has the least possible
	 * 	  values to check.
	 * 2. Fill each emptySpot recursively but backtrack immediately when 
	 * 	  a Spot is reached which cannot be filled by any of its possibleValues.
	 * 3. Keep adding the filled(solved) spots to the List "solvedSpots"  
	 * 4. When a complete solution is reached add the current solvedSpots
	 * 	  list to the list of solutions.
	 * 5. Return only when all possible solutions have been exhausted.
	 * 
	 * Note: index holds the current index of the emptySpots ArrayList.
	 */
	private void solveSudoku(List<Spot> emptySpots,
			List<Spot> solvedSpots, int index) {
		
		/* Only allow MAX_SOLUTIONS */
		if (solutions.size() >= Sudoku.MAX_SOLUTIONS)
			return;
		
		/* Base Case: When the current chain of values has arrived at a solution */
		if (index >= emptySpots.size()) {
			solutions.add(new ArrayList<>(solvedSpots));
			return;
		}
		
		/* Current emptySpot that is being considered to be filled */
		Spot currentSpot = new Spot(emptySpots.get(index));
		
		/* Try all the possible values for this empty Spot */
		for (int value : currentSpot.possibleValues) {
			
			/* Check if the value is valid according to the current 
			 * state of the grid */
			if (valueIsValid(value, currentSpot)) {
				currentSpot.setValue(value);
				updateGridStateWithValue(value, currentSpot);
				
				solvedSpots.add(currentSpot);
				
				int newIndex = index + 1;
				solveSudoku(emptySpots, solvedSpots, newIndex);
				
				/* Backtrack when the method above returns */
				emptySpots.get(index).setValue(0);
				solvedSpots.remove(currentSpot);
				updateGridStateWithValue(value, currentSpot);
			}
		}
	}

	/* Fills the solutionGrid field with the first solution that was
	 * found while solving the puzzle.
	 */
	private void fillSolutionGrid() {
		List<Spot> solvedSpots = solutions.get(0);
		
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				solutionGrid[i][j] = new Spot(puzzleGrid[i][j]);
			}
		}
		
		for (Spot spot : solvedSpots)
			spot.updateValueInGrid();
	}

	/* Checks if the given value is valid for the given currentSpot by 
	 * checking it against all values in this Spot's row, column and Part
	 */
	private boolean valueIsValid(int value, Spot currentSpot) {
		int row = currentSpot.getRow();
		int col = currentSpot.getCol();
		int part = currentSpot.getPartForSpot();
		
		return (!valInRows.get(row).contains(value)) &&
				(!valInCols.get(col).contains(value)) &&
				(!valInParts.get(part).contains(value));
	}

	/* Updates the state of the grid. If the given value already exists
	 * as a part of the grid state, then it is removed otherwise it is
	 * added to the current state.
	 */
	private void updateGridStateWithValue(int value, Spot currentSpot) {
		Set<Integer> valsInCurrentRow = valInRows.get(currentSpot.getRow());
		Set<Integer> valsInCurrentCol = valInCols.get(currentSpot.getCol());
		Set<Integer> valsInCurrentPart = valInParts.get(currentSpot.getPartForSpot());
		
		if (valsInCurrentRow.contains(value))
			valsInCurrentRow.remove(value);
		else 
			valsInCurrentRow.add(value);
		
		if (valsInCurrentCol.contains(value))
			valsInCurrentCol.remove(value);
		else 
			valsInCurrentCol.add(value);
		
		if (valsInCurrentPart.contains(value))
			valsInCurrentPart.remove(value);
		else 
			valsInCurrentPart.add(value);
		
	}


	/* Helper method to compute the possible values for each empty spot and
	 * return the spots as an ArrayList sorted by the number of possible values
	 * from low to high.
	 */
	private List<Spot> getEmptySpotsList() {
		List<Spot> result = new ArrayList<>();
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				Spot thisSpot = puzzleGrid[i][j];
				if (thisSpot.isEmpty()) {
					thisSpot.getPossibleValues();
					result.add(thisSpot);
				}
			}
		}
		Collections.sort(result);
		return result;
	}
	
	/**
	 * Returns the Solution to the Sudoku puzzle as a String.
	 * @return solution to the puzzle
	 */
	public String getSolutionText() {
		if (solutions.size() == 0) return "No Solutions";
		String result = "";
		for (Spot[] sArr : solutionGrid) {
			result += "[";
			for (Spot s : sArr)
				result += s.toString() + ", ";
			result += "] \n";
		}
		return result;
	}
	
	/**
	 * Returns the elapsed time spent find the solutions for
	 * the puzzle
	 * @return time taken to solve the puzzle
	 */
	public long getElapsed() {
		return timeTakenForSolution;
	}
	
	@Override
	public String toString() {
		String result = "";
		for (Spot[] sArr : puzzleGrid) {
			result += "[";
			for (Spot s : sArr)
				result += s.toString() + ", ";
			result += "] \n";
		}
		return result;
	}
	
	/* Just for simple testing */
	public static void main(String[] args) {
		Sudoku sudoku;
		sudoku = new Sudoku(easyGrid);
		
		System.out.println(sudoku); // print the raw problem
		int count = sudoku.solve();
		System.out.println("solutions:" + count);
		System.out.println("elapsed:" + sudoku.getElapsed() + "ms");
		System.out.println(sudoku.getSolutionText());
	}

}
