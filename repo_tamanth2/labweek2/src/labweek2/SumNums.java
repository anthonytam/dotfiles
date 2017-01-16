package labweek2;

import java.io.*; 
import java.util.*;
public class SumNums {

	public static void main(String[] args) {
		Scanner scanner=new Scanner(System.in);

		String line;
		int start, end;
		while(true){
			System.out.println("Starting number: ");
			start=scanner.nextInt(); 
			System.out.println("Ending number: ");
			end=scanner.nextInt();
			int sum=sumup(start, end);
			System.out.println("The sum from "+start+ " to "+ end + " is: "+ sum);
		}
	}

	// Create static method sumup(s,e) which returns the sum s+(s+1)+...+(e-1)+e
	// or 0 if this sum does not make sense (ie sumup(3,-4)).
	public static int sumup(int s, int e){
		if (s <= e){
			int sum = 0;
			for(int i = s; i <= e; i++){
				sum += i;
			}
			return sum;
		}
		return 0;
	}
}
