package kattis;

import java.util.HashSet;
import java.util.Set;

//Map Colouring
public class MapColouring {
	private static boolean[][] map;
	private static int[] colors;
	private static int nCountries;

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in, System.out);
		int nTests = io.getInt();
		String[] ans = new String[nTests];
		for (int test = 0; test < nTests; test++) {
			nCountries = io.getInt();
			int nBoarders = io.getInt();

			map = new boolean[nCountries][nCountries];
			colors = new int[nCountries];

			for (int i = 0; i < nBoarders; i++) {
				int a = io.getInt();
				int b = io.getInt();
				map[a][b] = true;
				map[b][a] = true;
			}

			int nColors = 1;
			for (int n = 1; n < 5; n++) {
				colors = new int[nCountries];
				nColors = n;
				try {
					color(0, n);
				} catch (Exception e) {
					break;
				}

			}

			if (allHasColor(colors))
				ans[test] = "" + nColors;
			else
				ans[test] = "many";
		}
		for (String s : ans)
			System.out.println(s);
	}

	private static boolean allHasColor(int[] list) {
		for (int i : list)
			if (i == 0)
				return false;
		return true;
	}

	private static void color(int node, int nColors) throws Exception {
		for (int i = 1; i <= nColors; i++) {
			if (canColor(node, i)) {
				colors[node] = i;
				if (node + 1 >= nCountries)
					throw new Exception("Done, kill all threads");
				color(node + 1, nColors);
				colors[node] = 0;
			}
		}
	}

	private static boolean canColor(int node, int color) {
		// For all nodes which are adjacent and has same color return false
		for (int i = 0; i < nCountries; i++)
			if (color == colors[i] && (map[node][i] == true || map[i][node] == true))
				return false;
		return true;
	}

}
