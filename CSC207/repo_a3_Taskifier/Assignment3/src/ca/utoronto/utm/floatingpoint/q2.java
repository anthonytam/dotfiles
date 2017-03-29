package ca.utoronto.utm.floatingpoint;

public class q2 {
	public static void main(String[] args) {
		q2 p = new q2();
		System.out.println(p.solveDiophantine(7.11F));
	}

	/**
	 * The method will calculate a diophantine problem in the most efficient brute force manner.
	 * It will calculate the first valid combination of 4 numbers with the same sum and product.
	 *
	 * Increment 3 numbers in the order a <= b <= c, the find d by taking the factor we are calculating
	 * and subtracting it from the other 3 values.
	 *
	 * To avoid floating point loss, we multiply the prices by 100 in order to work with whole numbers.
	 * These numbers can then be incremented and manipulated without the possibility of number loss.
	 * @param factor The number to solve to
	 * @return A string containing the 4 solutions, null if a solution is not found.
	 */
	public String solveDiophantine(float factor) {
		//Convert dollar value to cents.
		factor *= 100F;

		for(float a = 1; a <= factor; a++)
			for (float b = a; b <= factor; b++)
				for (float c = b; c <= factor; c++) {
					//With 3 variables, calculate the 4th the gives a sum to the desired number
					float d = factor - a - b - c;
					//Since we converted to cents, must increase the multiplication expansion to
					//10^4 dollars. We already multiplied by 100 at the start, to only multiply by
					//10^3 here.
					if (a*b*c*d == factor*Math.pow(100.0, 3.0)) {
						return a/100F + " " + b/100F + " " + c/100F + " " + d/100F;
					}
				}
		return null;
	}
}