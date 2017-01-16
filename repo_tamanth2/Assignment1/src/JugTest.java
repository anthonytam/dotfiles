import static org.junit.Assert.*;

import org.junit.Test;

public class JugTest {

	@Test
	public void testChangeVolume() {
		// Change the volume of the jug, both increase and decrease the
		// volume of the jug, attempt to over fill as well as remove more
		// then what is in the jug.
		Jug j = new Jug(5);
		assertEquals(0, j.getCurrentVolume());
		j.changeVolume(5);
		assertEquals(5, j.getCurrentVolume());
		j.changeVolume(-2);
		assertEquals(3, j.getCurrentVolume());
		assertEquals(false, j.changeVolume(10));
		assertEquals(true, j.changeVolume(-3));
		assertEquals(0, j.getCurrentVolume());
		j.changeVolume(3);
		assertEquals(3, j.getCurrentVolume());
		j.changeVolume(2);
		assertEquals(5, j.getCurrentVolume());
		j.changeVolume(-1);
		assertEquals(4, j.getCurrentVolume());
		assertEquals(false, j.changeVolume(-7));
	}

	@Test
	public void testToString() {
		// Test the string output of a jug while attempting to over and under
		// fill it.
		Jug j = new Jug(5, 5);
		assertEquals("5/5", j.toString());
		j.changeVolume(-2);
		assertEquals("3/5", j.toString());
		j.changeVolume(-6);
		assertEquals("3/5", j.toString());
		j.changeVolume(1);
		assertEquals("4/5", j.toString());
		j.changeVolume(10);
		assertEquals("4/5", j.toString());
		j.changeVolume(-4);
		assertEquals("0/5", j.toString());
		j.changeVolume(6);
		assertEquals("0/5", j.toString());
		j.changeVolume(5);
		assertEquals("5/5", j.toString());
		j.changeVolume(-4);
		assertEquals("1/5", j.toString());
		j.changeVolume(1);
		assertEquals("2/5", j.toString());
	}
}
