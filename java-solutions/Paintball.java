package kattis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

//Paintball
public class Paintball {
	private static boolean[][] map;
	private static int N;
	private static int[] matching;

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in, System.out);
		N = io.getInt();

		int nConnections = io.getInt();
		map = new boolean[N][N];
		for (int i = 0; i < nConnections; i++) {
			int a = io.getInt() - 1;
			int b = io.getInt() - 1;
			map[a][b] = true;
			map[b][a] = true;
		}

		matching = new int[N];
		for (int i = 0; i < N; i++)
			matching[i] = -1;

		boolean[] checked;
		int cnt = 0;
		for (int i = 0; i < N; i++) {
			checked = new boolean[N];
			if (!match(i, checked)) {
				System.out.println("Impossible");
				System.exit(0);
			}
		}

		int[] ans = new int[N];
		for (int i = 0; i < N; i++)
			ans[matching[i]] = i;
		for (int i : ans)
			System.out.println((i + 1));

	}

	private static boolean match(int p, boolean[] checked) {
		for (int adj = 0; adj < N; adj++) {
			if (map[p][adj] && adj != p) {
				if (matching[adj] < 0) {
					matching[adj] = p;
					return true;
				}
			}
		}
		for (int adj = 0; adj < N; adj++) {
			if (map[p][adj] && !checked[adj] && adj != p) {
				checked[adj] = true;
				if (match(matching[adj], checked)) {
					matching[adj] = p;
					return true;
				}
			}
		}

		return false;

	}

}
