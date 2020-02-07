package kattis;

import java.util.ArrayList;
import java.util.PriorityQueue;

//Island Hopping
public class IslandHopping {

	private static class Edge implements Comparable<Edge> {

		int a;
		int b;
		double length;

		public Edge(int a, int b, double length) {
			this.a = a;
			this.b = b;
			this.length = length;

			// this.length = Math.sqrt(xx * xx + yy * yy);
		}

		private double getLength() {
			return length;
		}

		private int getA() {
			return a;
		}

		private int getB() {
			return b;
		}

		@Override
		public int compareTo(Edge other) {
			return Double.compare(length, other.getLength());
		}
	}

	private static class QuickUnion {
		int[] list;

		public QuickUnion(int N) {
			list = new int[N];
			for (int i = 0; i < N; i++)
				list[i] = i;
		}

		private void union(int p, int q) {
			int pp = find(p);
			int qq = find(q);
			list[qq] = pp;
		}

		private int find(int p) {
			while (list[p] != p)
				p = list[p];
			return p;
		}

		private boolean isConnected(int p, int q) {
			return find(p) == find(q);
		}

	}

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in, System.out);
		int cases = io.getInt();
		double[] res = new double[cases];
		for (int c = 0; c < cases; c++) {
			int islands = io.getInt();

			ArrayList<Double> nodeX = new ArrayList<Double>(islands);
			ArrayList<Double> nodeY = new ArrayList<Double>(islands);

			for (int i = 0; i < islands; i++) {
				double x = io.getDouble();
				double y = io.getDouble();
				nodeX.add(x);
				nodeY.add(y);
			}

			ArrayList<Edge> edges = new ArrayList<Edge>(islands * islands);

			for (int a = 0; a < islands; a++)
				for (int b = a + 1; b < islands; b++) {
					double xx = nodeX.get(a) - nodeX.get(b);
					double yy = nodeY.get(a) - nodeY.get(b);
					double l = Math.sqrt(xx * xx + yy * yy);
					edges.add(new Edge(a, b, l));
				}

			// edges.sort((E1, E2) -> Double.compare(E1.getLength(),
			// E2.getLength()));

			PriorityQueue<Edge> pq = new PriorityQueue<Edge>();
			QuickUnion uf = new QuickUnion(islands);
			for (Edge e : edges)
				pq.add(e);

			double distance = 0;
			while (!pq.isEmpty()) {
				Edge e = pq.poll();
				// System.out.println("Lengde " + e.getLength());
				int a = e.getA();
				int b = e.getB();

				if (!(uf.isConnected(a, b))) {
					// System.out.println("Lengde " + e.getLength());
					distance += e.getLength();
					uf.union(a, b);
				}

			}
			res[c] = distance;
		}
		for (Double d : res)
			System.out.println(d);

		io.close();
	}

}
