package kattis;

import java.math.BigInteger;

//I Hate The Number Nine
public class Nine {

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in, System.out);
		int N = io.getInt();
		long[] ans = new long[N];
		for (int i = 0; i < N; i++) {
			System.out.println(findMax(io.getLong()).toString());
		}
		// for (long i : ans)
		// System.out.println(i + "");
		io.close();
	}

	private static BigInteger findMax(long digits) {
		BigInteger a = BigInteger.valueOf(9);
		a = a.modPow(BigInteger.valueOf(digits - 1), BigInteger.valueOf(1000000007));
		a = a.multiply(BigInteger.valueOf(8));
		a = a.mod(BigInteger.valueOf(1000000007));
		return a;

		// for (long i = 1; i < digits; i++)
		// ans = (9 * ans) % 1000000007;
		// return ans;

	}

}
