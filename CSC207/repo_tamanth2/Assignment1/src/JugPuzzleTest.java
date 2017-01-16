import static org.junit.Assert.*;

import org.junit.Test;

public class JugPuzzleTest {

	@Test
	public void testgetNumberOfMoves() {
		// Simply continue making moves and ensure the move counter
		// continues to increase
		JugPuzzle jp = new JugPuzzle();
		jp.move(1, 2);
		assertEquals(1, jp.getMoves());
		jp.move(1, 2);
		jp.move(1, 2);
		jp.move(1, 2);
		assertEquals(4, jp.getMoves());
		jp.move(1, 2);
		jp.move(1, 2);
		assertEquals(6, jp.getMoves());
		jp.move(1, 2);
		assertEquals(7, jp.getMoves());
		jp.move(1, 2);
		assertEquals(8, jp.getMoves());
		jp.move(1, 2);
		assertEquals(9, jp.getMoves());
		jp.move(1, 2);
		assertEquals(10, jp.getMoves());
		jp.move(1, 2);
		assertEquals(11, jp.getMoves());
		jp.move(1, 2);
		assertEquals(12, jp.getMoves());
	}
	
	@Test
	public void testMove() {
		// Test both valid moves and invalid moves (same jug to and from, 
		// moves that would cause the jug to overflow, moves from an empty
		// jug) and ensure the output is as expected
		JugPuzzle jp = new JugPuzzle();
		assertEquals("0	 0:(8/8) 1:(0/5) 2:(0/3)", jp.toString());
		jp.move(0,  1);
		assertEquals("1	 0:(3/8) 1:(5/5) 2:(0/3)", jp.toString());
		jp.move(1,  2);
		assertEquals("2	 0:(3/8) 1:(2/5) 2:(3/3)", jp.toString());
		jp.move(2,  0);
		assertEquals("3	 0:(6/8) 1:(2/5) 2:(0/3)", jp.toString());
		jp.move(1,  2);
		assertEquals("4	 0:(6/8) 1:(0/5) 2:(2/3)", jp.toString());
		jp.move(1,  2);
		assertEquals("5	 0:(6/8) 1:(0/5) 2:(2/3)", jp.toString());
		jp.move(0,  2);
		assertEquals("6	 0:(5/8) 1:(0/5) 2:(3/3)", jp.toString());
		jp.move(0,  2);
		assertEquals("7	 0:(5/8) 1:(0/5) 2:(3/3)", jp.toString());
		jp.move(1,  2);
		assertEquals("8	 0:(5/8) 1:(0/5) 2:(3/3)", jp.toString());
		jp.move(2,  1);
		assertEquals("9	 0:(5/8) 1:(3/5) 2:(0/3)", jp.toString());
		jp.move(0,  2);
		assertEquals("10\t 0:(2/8) 1:(3/5) 2:(3/3)", jp.toString());
		jp.move(0, 0);
		assertEquals("11\t 0:(2/8) 1:(3/5) 2:(3/3)", jp.toString());
	}
	
}
