package kattis;

import java.util.ArrayList;

//Supercomputer
public class Supercomputer {
	private static int[] list;
	private static int offs;

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in, System.out);
		int nBits = io.getInt();
		int nRequests = io.getInt();

		int N = 2;
		while ((2 * nBits) + 3 > N)
			N *= 2;
		offs = 2;
		while (nBits + 2 > offs)
			offs *= 2;

		list = new int[N];

		ArrayList<Integer> ans = new ArrayList<Integer>();
		for (int i = 0; i < nRequests; i++) {
			String s = io.getWord();
			if (s.charAt(0) == 'C') {
				int l = io.getInt();
				int r = io.getInt();
				ans.add(count(l, r));
			} else if (s.charAt(0) == 'F') {
				int index = io.getInt();
				flip(index);
			}
		}
		for (int i : ans)
			System.out.println(i);
	}

	private static void flip(int i) {
		// Pos for end node
		int pos = i + offs;

		int changeTo;
		if (list[pos] == 1)
			changeTo = 0;
		else
			changeTo = 1;

		int diff = changeTo - list[pos];

		// Walk up and change each equal to diff
		while (pos > 0) {
			list[pos] += diff;
			pos /= 2;
		}
	}

	private static int count(int l, int r) {
		int L = l + offs - 1;
		int R = r + offs + 1;
		int ans = 0;

		while (true) {
			// Check if the walked so they need to count
			boolean LRIGHT = (L % 2) == 0;
			boolean RLEFT = (R % 2) == 1;
			// Walk up
			L /= 2;
			R /= 2;
			// Have they met?
			if (R == L)
				break;

			if (LRIGHT)
				ans += list[(2 * L) + 1];
			if (RLEFT)
				ans += list[2 * R];
		}
		return ans;
	}

}
