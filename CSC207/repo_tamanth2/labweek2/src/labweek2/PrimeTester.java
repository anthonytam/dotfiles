package labweek2;

import java.util.*;

public class PrimeTester {
	public static void main(String[] args) {
		Scanner scanner=new Scanner(System.in);
		int testingNum;
		
		while(true){
			System.out.println("Enter a number: ");
			testingNum=scanner.nextInt(); 
			if (isPrime(testingNum)) {
				System.out.println(testingNum + " is prime");
			} else {
				System.out.println(testingNum + " is not prime");
			}
		}
	}
	
	public static boolean isPrime(int testNum) {
		for (int i = 2; i < testNum; i++){
			if(testNum%i == 0){
				return false;
			}
		}
		return true;
	}
}
