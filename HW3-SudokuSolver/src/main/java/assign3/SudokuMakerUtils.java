package assign3;

public class SudokuMakerUtils {
    /**
     * Given a single string containing 81 numbers, returns a 9x9 grid.
     * Skips all the non-numbers in the text.
     * (provided utility)
     * @param text string of 81 numbers
     * @return grid
     */
    public static int[][] textToGrid(String text) {
        int[] nums = stringToInts(text);
        if (nums.length != Sudoku.SIZE* Sudoku.SIZE) {
            throw new RuntimeException(String.format("Needed %d numbers, but got : %d", Sudoku.SIZE* Sudoku.SIZE, nums.length));
        }

        int[][] result = new int[Sudoku.SIZE][Sudoku.SIZE];
        int count = 0;
        for (int row = 0; row< Sudoku.SIZE; row++) {
            for (int col=0; col< Sudoku.SIZE; col++) {
                result[row][col] = nums[count];
                count++;
            }
        }
        return result;
    }

    /**
     * Returns a 2-d grid parsed from strings, one string per row.
     * @param rows array of row strings
     * @return grid
     */
    public static int[][] stringsToGrid(String...rows) {
        int[][] result = new int[rows.length][];
        for (int row = 0; row<rows.length; row++) {
            result[row] = stringToInts(rows[row]);
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
    private static int[] stringToInts(String string) {
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

}

