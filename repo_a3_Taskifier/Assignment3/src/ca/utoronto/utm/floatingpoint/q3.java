package ca.utoronto.utm.floatingpoint;


public class q3 {
	/**
	 * YOUR ANSWER GOES HERE!!!
	 * 
	 * a) -6.8 as an IEEE754 single is
	 *
	 * 1[10000001]10110011001100110011010
	 * or
	 * 11000000110110011001100110011010
	 *
	 * First bit is 1 since the number is negative
	 * Exponent of 129 since 129-127 is 2
	 * From here we can break down 6.8 into its binary form through normalization. Since this number is 8/10
	 * we will have a repeating binary number. We can round this is its closest even form
	 *
	 * b) 23.1 as an IEEE754 single is
	 *
	 * 0[10000011]01110001100110011001101
	 * or
	 * 01000001101110001100110011001101
	 *
	 * First bit is 0 since the number is non negative
	 * Exponent of 131 since 131-127 = 4
	 * Once again we have 1/10 being represented resulting in a repeating binary representation.
	 * This can be rounded by changing the least significant bit in the string to give its closest even form.
	 */
}
