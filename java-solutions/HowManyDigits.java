package kattis;

//How Many Digits?
public class HowManyDigits {

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		double[] list = new double[1000001];
		list[0] = 1.0;
		for (int i = 1; i < list.length; i++)
			list[i] = list[i - 1] + Math.log10(i);

		while (io.hasMoreTokens()) {
			int n = io.getInt();
			if (n == -1)
				break;
			System.out.println((int) Math.floor(list[n]));
		}
	}

}
