package assign3;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

//import assign3.Sudoku.Spot;

public class SpotTest {
	
	private Sudoku sd, sd2;
//	private Spot s, s2;
	
	@Before
	public void setUp() throws Exception {
		sd = new Sudoku(Sudoku.easyGrid);
		sd2 = new Sudoku(Sudoku.mediumGrid);
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
	}

}
