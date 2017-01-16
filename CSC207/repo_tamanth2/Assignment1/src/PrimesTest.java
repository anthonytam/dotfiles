import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

public class PrimesTest {

	@Test
	public void testIsPrime() {
		assertEquals(true, Primes.isPrime(3));
		assertEquals(true, Primes.isPrime(5));
		assertEquals(true, Primes.isPrime(7));
		assertEquals(true, Primes.isPrime(2));
		assertEquals(false, Primes.isPrime(6));
		assertEquals(false, Primes.isPrime(100));
		assertEquals(false, Primes.isPrime(1000));
		assertEquals(false, Primes.isPrime(8));
		assertEquals(false, Primes.isPrime(9));
		assertEquals(false, Primes.isPrime(16));
	}

	@Test
	public void testPrimes() {
		assertEquals(true, Arrays.equals(Primes.primes(1), new int[] {2}));
		assertEquals(true, Arrays.equals(Primes.primes(2), new int[] {2,3}));
		assertEquals(true, Arrays.equals(Primes.primes(3), new int[] {2,3,5}));
		assertEquals(true, Arrays.equals(Primes.primes(4), new int[] {2,3,5,7}));
		assertEquals(false, Arrays.equals(Primes.primes(5), new int[] {2,4,3}));
		assertEquals(false, Arrays.equals(Primes.primes(3), new int[] {1}));
		assertEquals(false, Arrays.equals(Primes.primes(1), new int[] {1}));
		assertEquals(false, Arrays.equals(Primes.primes(3), new int[] {5,7}));
		assertEquals(false, Arrays.equals(Primes.primes(5), new int[] {1,2,3,4,5}));
		assertEquals(false, Arrays.equals(Primes.primes(9), new int[] {1,3,5,7,9,11,13,15,17}));
	}

	@Test
	public void testPrimesLessThan() {
		ArrayList<Integer> primes = new ArrayList<Integer>();
		assertEquals(primes, Primes.primesLessThan(1));
		primes.add(2);
		primes.add(3);
		assertEquals(primes, Primes.primesLessThan(4));
		assertEquals(primes, Primes.primesLessThan(5));
		primes.add(5);
		primes.add(7);
		assertEquals(primes, Primes.primesLessThan(8));
		primes.add(11);
		assertEquals(primes, Primes.primesLessThan(13));
		assertEquals(primes, Primes.primesLessThan(12));
		primes.add(13);
		assertEquals(primes, Primes.primesLessThan(14));
		assertEquals(primes, Primes.primesLessThan(15));
		assertEquals(primes, Primes.primesLessThan(16));
		assertEquals(primes, Primes.primesLessThan(17));
	}

	@Test
	public void testPrimesLessThanSieveRemove() {
		for (int i = 3; i <= 50; i++)
			assertEquals(Primes.primesLessThan(i), Primes.primesLessThanSieveRemove(i));
	}

	@Test
	public void testPrimesLessThanSieveAdd() {
		for (int i = 3; i <= 50; i++)
			assertEquals(Primes.primesLessThan(i), Primes.primesLessThanSieveAdd(i));
	}

}
