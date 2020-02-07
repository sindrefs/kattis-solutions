package kattis;

//Pivot
public class Pivot {
	private static int[] list;
	private static int[] maxList;
	private static int[] minList;

	private static int n;

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in, System.out);

		n = io.getInt();
		list = new int[n + 1];
		maxList = new int[n + 1];
		minList = new int[n + 1];

		int max = Integer.MIN_VALUE;
		for (int i = 1; i <= n; i++) {
			list[i] = io.getInt();
			if (max < list[i])
				max = list[i];
			maxList[i] = max;
		}

		int min = Integer.MAX_VALUE;
		for (int i = n; i >= 0; i--) {
			if (min > list[i])
				min = list[i];
			minList[i] = min;
		}

		int cnt = 0;

		for (int i = 1; i <= n; i++) {
			if (i == n && maxList[i - 1] <= list[i]) {
				cnt++;
			} else if (maxList[i - 1] <= list[i] && minList[i + 1] > list[i])
				cnt++;
		}
		System.out.println(cnt);
		io.close();

	}

}
