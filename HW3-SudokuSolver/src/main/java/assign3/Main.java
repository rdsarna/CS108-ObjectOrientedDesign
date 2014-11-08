package assign3;

public class Main {
    /* Just for simple testing */
    public static void main(String[] args) {
        Sudoku sudoku = new Sudoku(SudokuGridFactory.getHardGrid());

        System.out.println(sudoku); // print the raw problem
        int count = sudoku.solve();
        System.out.println("solutions:" + count);
        System.out.println("elapsed:" + sudoku.getElapsed() + "ms");
        System.out.println(sudoku.getSolutionText());
    }
}
