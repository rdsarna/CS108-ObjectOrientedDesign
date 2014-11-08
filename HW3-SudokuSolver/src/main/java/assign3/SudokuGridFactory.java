package assign3;

public class SudokuGridFactory {

    public static int[][] getHardGrid() {
    // Provided hard 3 7 grid
    // 1 solution this way, 6 solutions if the 7 is changed to 0
       return stringsToGrid(
               "3 7 0 0 0 0 0 8 0",
               "0 0 1 0 9 3 0 0 0",
               "0 4 0 7 8 0 0 0 3",
               "0 9 3 8 0 0 0 1 2",
               "0 0 0 0 4 0 0 0 0",
               "5 2 0 0 0 6 7 9 0",
               "6 0 0 0 2 1 0 4 0",
               "0 0 0 5 3 0 9 0 0",
               "0 3 0 0 0 0 0 5 1");
    }
    public static int[][] getEasyGrid(){
    // Provided easy 1 6 grid
        return stringsToGrid(
                "1 6 4 0 0 0 0 0 2",
                "2 0 0 4 0 3 9 1 0",
                "0 0 5 0 8 0 4 0 7",
                "0 9 0 0 0 6 5 0 0",
                "5 0 0 1 0 2 0 0 8",
                "0 0 8 9 0 0 0 3 0",
                "8 0 9 0 4 0 2 0 0",
                "0 7 3 5 0 9 0 0 1",
                "4 0 0 0 0 0 6 7 9");
    }

    public static int[][] getMediumGrid(){
    // Provided medium 5 3 grid
      return stringsToGrid(
              "530070000",
              "600195000",
              "098000060",
              "800060003",
              "400803001",
              "700020006",
              "060000280",
              "000419005",
              "000080079");
    }

    /**
     * Returns a 2-d grid parsed from strings, one string per row.
     * @param rows array of row strings
     * @return grid
     */
    public static int[][] stringsToGrid(String...rows) {
        int[][] result = new int[rows.length][];
        for (int row = 0; row<rows.length; row++) {
            result[row] = Sudoku.stringToInts(rows[row]);
        }
        return result;
    }
}
