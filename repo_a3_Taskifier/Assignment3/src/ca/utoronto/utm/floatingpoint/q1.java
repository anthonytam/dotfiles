package ca.utoronto.utm.floatingpoint;

public class q1 {
	public static void main(String[] args) {
		q1 p = new q1();
		System.out.println(p.solve711());
	}
	
	/**
	 * The solution does attempt to solve the problem, however it does not find a solution.
	 * It does not account for floating point loss.
	 * The program will loop through 4 variables between 0 and 7.11 testing to see if the multiplication
	 * and addition of the numbers are equal to 7.11. However it runs in O(n^4) time (Where n = 7.11)
	 * and is only precise to 0.01F. This margin of error is not accounted for, resulting in solutions being
	 * skipped. This also causes issues when the numbers are multiplied and added skipping values.
	 * @return Will not find a solution.
	 */
	public String solve711() {
		float a, b, c, d;
		for (a = 0.00f; a < 7.11f; a = a + .01f) {
			for (b = 0.00f; b < 7.11f; b = b + .01f) {
				for (c = 0.00f; c < 7.11f; c = c + .01f) {
					for (d = 0.00f; d < 7.11f; d = d + .01f) {
						if (a * b * c * d == 7.11f && a + b + c + d == 7.11f) {
							return (a + " " + b + " " + c + " " + d);
						}
					}
				}
			}
		}
		return "";
	}
}
