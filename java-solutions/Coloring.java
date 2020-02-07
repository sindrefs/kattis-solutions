package kattis;

import java.util.Scanner;

// Coloring Graphs
public class Coloring {
	private static boolean[][] matrix;
	private static int N;
	private static int[] colors;

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		N = in.nextInt();
		in.nextLine();

		matrix = new boolean[N][N];

		for (int i = 0; i < N; i++) {
			String s = in.nextLine();
			String[] arr = s.split(" ");

			for (String c : arr) {
				int a = Integer.parseInt(c);
				matrix[i][a] = true;
				matrix[a][i] = true;
			}
		}

		// Check if graph is complete
		boolean complete = true;
		for (int i = 0; i < N; i++)
			if (!checkRowComplete(i))
				complete = false;
		if (complete) {
			System.out.println(N);
			System.exit(0);
		}

		int nColors = 0;
		for (int c = 0; c <= N; c++) {
			colors = new int[N];
			nColors = c;
			try {
				coloring(0, c);
			} catch (Exception e) {
				break;
			}

		}

		System.out.println(nColors);
		in.close();

	}

	private static boolean checkRowComplete(int a) {
		for (int b = 0; b < N; b++)
			if (a != b && !matrix[a][b])
				return false;
		return true;
	}

	private static void coloring(int node, int nColors) throws Exception {
		for (int i = 1; i <= nColors; i++) {
			if (canColor(node, i)) {
				colors[node] = i;
				if (node + 1 >= N)
					throw new Exception("Done, kill all threads");
				coloring(node + 1, nColors);
				colors[node] = 0;
			}
		}

	}

	private static boolean canColor(int node, int color) {
		for (int i = 0; i < N; i++)
			if ((matrix[node][i] == true || matrix[i][node] == true) && color == colors[i])
				return false;

		return true;
	}

}
