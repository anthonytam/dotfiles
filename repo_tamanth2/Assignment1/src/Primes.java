/**
 * Part f) using my algorithm and code design, Sieve Remove runs the fastest. It does not
 * shift the elements in an ArrayList, and does not use modulous as division is far slower
 * to compute compared to multiplication. On my personal computer, I was able to calulate 
 * 1 million primes in 74ms.
**/



import java.util.*;

public class Primes {
    public static void main(String [] args){
            System.out.println(primes(10000)[9999]);
            System.out.println(primesLessThan(10000));
            
            int n = 1000000;
            ArrayList<Integer> a;
            long start, end;
            
            start = System.currentTimeMillis();
            a = primesLessThan(n);
            end = System.currentTimeMillis();
            System.out.println(a.size()+ " " + (end-start));
            
            start = System.currentTimeMillis();
            a = primesLessThanSieveRemove(n);
            end = System.currentTimeMillis();
            System.out.println(a.size()+ " " + (end-start));
            
            start = System.currentTimeMillis();
            a = primesLessThanSieveAdd(n);
            end = System.currentTimeMillis();
            System.out.println(a.size()+ " " + (end-start));
    }
    
    /**
     * Returns whether or not a given integer is a prime number.
     * @param testNum The number that will be tested to be prime.
     * @return A boolean value. True if testNum is prime. False otherwise
     */
    public static boolean isPrime(int testNum) {
    	if(testNum>1){
    		// We only need to check until sqrt of the number.
    		// All multiples would have been found before this point.
    		// http://www.programmingsimplified.com/c/source-code/c-program-for-prime-number
    		if (testNum == 2)
    			return true;
			for (int i = 2; i <= (int)Math.ceil(Math.sqrt(testNum)); i++)
				if(testNum % i == 0)
					return false;	
			return true;
		}
		return false;
	}
    
    /**
     * Returns an integer array which contains the requested
     * number of primes. Starting at index 0, the array length
     * will match the number of requested primes.
     * @param primesToCreate An integer containing the number of 
     * primes to calculate
     * @return Integer array containing the requested number of primes
     */
    public static int[] primes(int primesToCreate){
    	int numberOfPrimes = 0;
    	int testingNum = 0;
    	int[] foundPrimes = new int[primesToCreate];
    	while(numberOfPrimes < primesToCreate){
    		if(isPrime(testingNum)){
    			foundPrimes[numberOfPrimes] = testingNum;
    			numberOfPrimes ++;
    		}
    		testingNum ++;
    	}
    	return foundPrimes;
    }
    
    /**
     * Generate all of the prime numbers below a given end point.
     * <p>
     * Implemented by finding divisibility between numbers from 2
     * to the square root of the end point.
     * @param endPoint An integer containing the upper bound of
     * primes to calculate
     * @return An ArrayList containing all prime integers from 3
     * to the upper bound
     */
    public static ArrayList<Integer> primesLessThan(int endPoint){
    	ArrayList<Integer> foundPrimes = new ArrayList<Integer>();
    	for (int i = 2; i < endPoint; i++)
    		if (isPrime(i))
    			foundPrimes.add(i);
    	return foundPrimes;
    }
    
    /**
     * Generate all the prime numbers below a given end point.
     * <p>
     * Implemented using a Sieve of Eratosthenes approach. Finds
     * Multiples of a number, removing them from a list and keeping
     * the original number.
     * @param endPoint An integer containing the upper bound of primes
     * to calculate
     * @return An ArrayList containing all the prime integers from 3
     * to the upper bound
     * 
     */
    public static ArrayList<Integer> primesLessThanSieveRemove(int endPoint){
    	boolean[] primes = new boolean[endPoint];
    	//Let the index rep the number, the value if it is prime or not
    	for (int i = 2; i < endPoint; i++)
    		primes[i] = true;
    	
    	//Find prime values
		for (int i = 2; i <= (int)Math.ceil(Math.sqrt(endPoint)); i++)
			if(primes[i])
				for (int j = i; i*j < endPoint; j++)
					primes[i*j] = false;
		
		//Changeover boolean array to an ArrayList
		ArrayList<Integer> primeList = new ArrayList<Integer>();
		for (int i = 2; i < endPoint; i++)
			if (primes[i])
				primeList.add(i);
		return primeList;
    }
    
    /**
     * Generate all the prime numbers below a given end point.
     * <p>
     * Implemented using reverse Sieve of Eratosthenes approach. Finds
     * Checks a list forMultiples of a number, if none are found the
     * number is added to the list.
     * @param endPoint An integer containing the upper bound of primes
     * to calculate
     * @return An ArrayList containing all the prime integers from 3
     * to the upper bound
     * 
     */
    public static ArrayList<Integer> primesLessThanSieveAdd(int endPoint){
    	ArrayList<Integer> primeList = new ArrayList<Integer>();
		for (int i = 2; i < endPoint; i++) {
			boolean addNumber = true;
			for(int j = 0; j <= primeList.size() - 1; j++)
				if(i % primeList.get(j) == 0){
					addNumber = false;
					break;
				}
			if (addNumber)
				primeList.add(i);
		}
		return primeList;
    }
}
