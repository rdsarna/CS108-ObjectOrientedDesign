package assign3;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import assign3.Sudoku.Spot;

public class SudokuTest {
	
	private Sudoku sd, sd2, sd3;
//	private Spot s, s2;
	
	@Before
	public void setUp() throws Exception {
		sd = new Sudoku(Sudoku.easyGrid);
		sd2 = new Sudoku(Sudoku.mediumGrid);
		sd3 = new Sudoku(Sudoku.hardGrid2);
	}
	
	@Test
	public void testSudoku() {
		System.out.println(sd); // print the raw problem
		int count = sd.solve();
		System.out.println("solutions:" + count);
		System.out.println("elapsed:" + sd.getElapsed() + "ms");
		System.out.println(sd.getSolutionText());
		
//		System.out.println(sd2); // print the raw problem
//		int count2 = sd2.solve();
//		System.out.println("solutions:" + count2);
//		System.out.println("elapsed:" + sd2.getElapsed() + "ms");
//		System.out.println(sd2.getSolutionText());
		
		System.out.println(sd3); // print the raw problem
		int count3 = sd3.solve();
		System.out.println("solutions:" + count3);
		System.out.println("elapsed:" + sd3.getElapsed() + "ms");
		System.out.println(sd3.getSolutionText());
	}

	@Test
	public void testSpot() {
//		assertFalse(s.isEmpty());
//		assertEquals(7, s.getValue());
//		s.setValue(0);
//		assertEquals(0, s.getValue());
//		assertTrue(s.isEmpty());
//		assertEquals(1, s.getPartForSpot());
//		
//		assertFalse(s2.isEmpty());
//		assertEquals(2, s2.getValue());
//		assertEquals(9, s2.getPartForSpot());
		
//		System.out.println(sd.puzzleGrid[0][1].getPossibleValues());
//		System.out.println(sd.puzzleGrid[1][1].getPossibleValues());
//		System.out.println(sd.puzzleGrid[0][7].getPossibleValues());
//		System.out.println(sd.puzzleGrid[8][3].getPossibleValues());
//		
//		System.out.println();
//		
//		System.out.println(sd2.puzzleGrid[1][1].getPossibleValues());
//		System.out.println(sd2.puzzleGrid[6][5].getPossibleValues());
		
//		ArrayList<Spot> emptySpotList = sd.getEmptySpotsList();
//		
//		ArrayList<Spot> emptySpotList2 = sd2.getEmptySpotsList();
//		
//		ArrayList<Spot> emptySpotList3 = sd3.getEmptySpotsList();
//		
//		System.out.println(emptySpotList);
//		System.out.println(emptySpotList2);
//		System.out.println(emptySpotList3);
		
	}

}
