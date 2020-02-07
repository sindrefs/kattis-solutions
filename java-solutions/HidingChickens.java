package kattis;

import java.util.ArrayList;

//Hiding Chickens
public class HidingChickens {

	private static class Point {
		double x;
		double y;

		public Point(double x, double y) {
			this.x = x;
			this.y = y;
		}

		private double getDist(Point other) {
			double xx = Math.pow(x - other.x, 2);
			double yy = Math.pow(y - other.y, 2);
			return Math.sqrt(xx + yy);
		}
	}

	private static int N;
	private static int ALL_VISITED;
	private static double[][] map;

	private static double[] dists;

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in, System.out);

		Point start = new Point(io.getDouble(), io.getDouble());
		N = io.getInt();
		ArrayList<Point> points = new ArrayList<Point>(N + 1);
		points.add(start);
		for (int i = 0; i < N; i++)
			points.add(new Point(io.getDouble(), io.getDouble()));
		N++;
		map = new double[N][N];
		for (int i = 0; i < N; i++)
			for (int j = i; j < N; j++) {
				map[i][j] = points.get(i).getDist(points.get(j));
				map[j][i] = map[i][j];
			}

		ALL_VISITED = (int) Math.pow(2, N) - 1;
		dists = new double[ALL_VISITED];
		for (int i = 0; i < ALL_VISITED; i++)
			dists[i] = -1;

		System.out.println(explore(1));
		// System.out.println(Integer.toBinaryString(ALL_VISITED));

		// System.out.println();
		// for (int i = 0; i < N; i++) {
		// System.out.println();
		// for (int j = 0; j < N; j++)
		// System.out.print(map[i][j] + " ");
		// }
	}

	public static double explore(int set) {
		// if (set == ALL_VISITED)
		// return 0.0;

		if (dists[set] != -1)
			return dists[set];

		double dist = Integer.MAX_VALUE;

		int cSet = set;
		int dSet;
		outer: for (int c = 1; c < N; c++) {
			if ((set & (1 << c)) == 0) {
				// Mark c visited
				cSet = set | (1 << c);

				// Start to here
				if (cSet == ALL_VISITED) {
					dist = Math.min(dist, map[0][c]);
					break outer;
					// return map[0][c];
				}

				// Visit this node then back to start
				dist = Math.min(dist, map[0][c] + map[c][0] + explore(cSet));

				// Visit all other nodes then back to start
				for (int d = 1; d < N; d++) {
					if ((cSet & (1 << d)) == 0) {
						dSet = cSet | (1 << d);

						if (dSet == ALL_VISITED) {
							dist = Math.min(dist, (map[0][c] + map[c][d]));
						} else
							dist = Math.min(dist, map[0][c] + map[c][d] + map[d][0] + explore(dSet));
					}
				}

			}
		}
		dists[set] = dist;
		return dist;
	}

}
