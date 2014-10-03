package tetris;

import static org.junit.Assert.*;
import java.util.*;

import org.junit.*;

/*
  Unit test for Piece class -- starter shell.
 */
public class PieceTest {
	// You can create data to be used in the your
	// test cases like this. For each run of a test method,
	// a new PieceTest object is created and setUp() is called
	// automatically by JUnit.
	// For example, the code below sets up some
	// pyramid and s pieces in instance variables
	// that can be used in tests.
	private Piece pyr1, pyr2, pyr3, pyr4;
	private Piece s, sRotated, sqr, sqrRotated;
	private Piece l1, s2, l, stickRotated, l1Rotated, stickRotated2;
	private Piece[] pieces;

	@Before
	public void setUp() throws Exception {
		
		pyr1 = new Piece(Piece.PYRAMID_STR);
		l1 = new Piece(Piece.L1_STR);
		s2 = new Piece(Piece.S2_STR);
		pyr2 = pyr1.computeNextRotation();
		pyr3 = pyr2.computeNextRotation();
		pyr4 = pyr3.computeNextRotation();
		
		s = new Piece(Piece.S1_STR);
		sRotated = s.computeNextRotation();
		
		l = new Piece(Piece.STICK_STR);
		
		pieces = Piece.getPieces();
		stickRotated = pieces[0].fastRotation();
		l1Rotated = pieces[1].fastRotation();
		stickRotated2 = stickRotated.fastRotation();

		sqr = pieces[5];
		sqrRotated = sqr.fastRotation();
	}
	
	// Here are some sample tests to get you started
	
	@Test
	public void testSampleSize() {
		// Check size of pyr piece
		assertEquals(3, pyr1.getWidth());
		assertEquals(2, pyr1.getHeight());
		assertEquals(2, l1.getWidth());
		assertEquals(3, l1.getHeight());
		assertEquals(3, s2.getWidth());
		assertEquals(2, s2.getHeight());
		
		// Now try after rotation
		// Effectively we're testing size and rotation code here
		assertEquals(2, pyr2.getWidth());
		assertEquals(3, pyr2.getHeight());
		
		// Now try with some other piece, made a different way
		assertEquals(1, l.getWidth());
		assertEquals(4, l.getHeight());
		
		// Testing getPieces
		assertEquals(1, pieces[0].getWidth());
		assertEquals(4, pieces[0].getHeight());

		assertEquals(3, l1Rotated.getWidth());
		assertEquals(2, l1Rotated.getHeight());

		assertEquals(4, stickRotated.getWidth());
		assertEquals(1, stickRotated.getHeight());
		assertEquals(1, stickRotated2.getWidth());
		assertEquals(4, stickRotated2.getHeight());

		assertEquals(2, sqr.getWidth());
		assertEquals(2, sqr.getHeight());
		assertEquals(2, sqrRotated.getWidth());
		assertEquals(2, sqrRotated.getHeight());
		
		assertEquals(pieces[0], stickRotated2.fastRotation().fastRotation());
	}
	
	
	// Test the skirt returned by a few pieces
	@Test
	public void testSampleSkirt() {
		// Note must use assertTrue(Arrays.equals(... as plain .equals does not work
		// right for arrays.
		assertTrue(Arrays.equals(new int[] {0, 0, 0}, pyr1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 0}, l1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0, 0}, s2.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0}, l.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0, 1}, pyr3.getSkirt()));
		
		assertTrue(Arrays.equals(new int[] {0, 0, 1}, s.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0}, sRotated.getSkirt()));

		assertTrue(Arrays.equals(new int[] {0, 0, 0, 0}, stickRotated.getSkirt()));

		assertTrue(Arrays.equals(new int[] {0, 0}, sqr.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 0}, sqrRotated.getSkirt()));
	}
	
	
}
