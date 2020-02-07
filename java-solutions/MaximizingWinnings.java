package kattis;

//Maximizing (And Minimizing) Your Winnings
public class MaximizingWinnings {
	private static int[][] matrix;
	private static int rooms;
	private static int[][] max;
	private static int[][] min;


	public static void main(String[] args) {
		Kattio io = new Kattio(System.in, System.out);
		while (true) {
			rooms = io.getInt();
			if (rooms == 0)
				break;

			matrix = new int[rooms][rooms];

			for (int i = 0; i < rooms; i++)
				for (int j = 0; j < rooms; j++)
					matrix[i][j] = io.getInt();

			int turns = io.getInt();
			max = new int[turns][rooms];
			min = new int[turns][rooms];

			for (int i = 0; i < turns; i++)
				for (int j = 0; j < rooms; j++) {
					max[i][j] = -1;
					min[i][j] = -1;
				}

			System.out.println(maxpath(0, 0, turns) + " " + minpath(0, 0, turns));

		}

	}

	private static int maxpath(int here, int turn, int turns) {
		int dist = 0;
		if (turn >= turns)
			return dist;

		if (max[turn][here] == -1)
			for (int adj = 0; adj < rooms; adj++) {
				max[turn][here] = Math.max(max[turn][here], matrix[here][adj] + maxpath(adj, turn + 1, turns));
			}

		return max[turn][here];

	}

	private static int minpath(int here, int turn, int turns) {
		int dist = Integer.MAX_VALUE;
		if (turn >= turns)
			return 0;

		if (min[turn][here] == -1) {
			for (int adj = 0; adj < rooms; adj++)
				dist = Math.min(dist, matrix[here][adj] + minpath(adj, turn + 1, turns));
			min[turn][here] = dist;
		}

		return min[turn][here];

	}

}
