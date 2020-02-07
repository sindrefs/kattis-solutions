package kattis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

//Speedy Escape
public class SpeedyEscape {
	private static class Inter implements Comparable<Inter> {

		int index;
		boolean exit;
		int distTo;
		double speed;

		public Inter(int index, boolean exit) {
			this.exit = exit;
			this.index = index;
			distTo = 0;
			speed = 0;
		}

		private int getDistTo() {
			return distTo;
		}

		private void setDistTo(int l) {
			distTo = l;
		}

		private double getSpeed() {
			return speed;
		}

		private void setSpeed(double s) {
			speed = s;
		}

		private boolean hasExit() {
			return exit;
		}

		private void setExit() {
			exit = true;
		}

		private int getIndex() {
			return index;
		}

		@Override
		public int compareTo(Inter other) {
			return Integer.compare(distTo, other.getDistTo());
		}

	}

	private static class Road implements Comparable<Road> {
		Inter a;
		Inter b;
		int length;

		public Road(Inter a, Inter b, int length) {
			this.a = a;
			this.b = b;
			this.length = length;
		}

		private int getLength() {
			return length;
		}

		private void setLength(int l) {
			length = l;
		}

		private Inter getA() {
			return a;
		}

		private Inter getB() {
			return b;
		}

		@Override
		public int compareTo(Road other) {
			return Integer.compare(length, other.length);
		}

	}

	private static Inter[] inters;
	private static Road[] roads;
	private static int[] distTo;
	private static boolean[] visited;
	private static PriorityQueue<Inter> pq;
	private static int nInters;
	private static int us;
	private static int police;
	private static double[] speed;
	private static int[] distToRob;
	private static int[] distToPol;

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in, System.out);

		nInters = io.getInt() + 1;
		int nRoads = io.getInt();
		int nExits = io.getInt();

		inters = new Inter[nInters];
		roads = new Road[nRoads];

		for (int i = 1; i < nInters; i++)
			inters[i] = (new Inter(i, false));

		for (int i = 0; i < nRoads; i++)
			roads[i] = new Road(inters[io.getInt()], inters[io.getInt()], io.getInt());

		for (int i = 0; i < nExits; i++)
			inters[io.getInt()].setExit();

		us = io.getInt();
		police = io.getInt();

		// Dijkstra for police path
		djikstra(police, true);
		distToPol = Arrays.copyOf(distTo, distTo.length);

		// Dijkstra for robber path
		djikstra(us, false);
		distToRob = Arrays.copyOf(distTo, distTo.length);

		finalPath(us);

		double maxSpeed = Double.MAX_VALUE;
		for (int i = 1; i < nInters; i++)
			if (inters[i].hasExit())
				if (maxSpeed > speed[i])
					maxSpeed = speed[i];
		// System.out.println(i + " " + speed[i]);
		if (maxSpeed < Double.MAX_VALUE)
			System.out.println(maxSpeed);
		else
			System.out.println("IMPOSSIBLE");
		io.close();
	}

	private static void finalPath(int start) {
		distTo = new int[nInters];
		visited = new boolean[nInters];
		speed = new double[nInters];
		pq = new PriorityQueue<Inter>(nInters, (a, b) -> Double.compare(a.getSpeed(), b.getSpeed()));

		for (int i = 1; i < nInters; i++) {
			if (i != start) {
				speed[i] = Double.MAX_VALUE;
				Inter p = inters[i];
				Inter n = new Inter(i, p.hasExit());
				n.setSpeed(Double.MAX_VALUE);
				pq.add(n);
			}
			visited[i] = false;
		}

		speed[start] = 0.0;
		Inter p = inters[start];
		Inter n = new Inter(start, p.hasExit());
		n.setSpeed(0);
		pq.add(n);

		while (!pq.isEmpty()) {
			p = pq.poll();
			if (p.getIndex() == police)
				continue;
			// System.out.println("Tatt ut " + p.getIndex());
			visited[p.getIndex()] = true;
			for (Road r : roads)
				if (r.getA().getIndex() == p.getIndex())
					relaxx(p, r.getB(), r);
				else if (r.getB().getIndex() == p.getIndex())
					relaxx(p, r.getA(), r);

		}
	}

	private static void relaxx(Inter v, Inter u, Road r) {
		double polTime = distToPol[u.getIndex()] / 160.0;
		double robSpeed = distToRob[u.getIndex()] / polTime;
		// System.out.println("polTime " + polTime + " robSpeed " + robSpeed);

		if (!visited[u.getIndex()] && speed[u.getIndex()] > Math.max(speed[v.getIndex()], robSpeed)) {
			speed[u.getIndex()] = Math.max(speed[v.getIndex()], robSpeed);
			pq.remove(u);
			Inter newU = new Inter(u.getIndex(), u.hasExit());
			newU.setSpeed(speed[u.getIndex()]);
			pq.add(newU);

		}

	}

	private static void djikstra(int start, boolean canPassPolice) {
		// int nInters = inters.size();
		distTo = new int[nInters];
		visited = new boolean[nInters];
		pq = new PriorityQueue<Inter>(nInters);

		for (int i = 1; i < nInters; i++) {
			if (i != start) {
				distTo[i] = Integer.MAX_VALUE;
				Inter p = inters[i];
				Inter n = new Inter(i, p.hasExit());
				n.setDistTo(Integer.MAX_VALUE);
				pq.add(n);
			}
			visited[i] = false;
		}
		distTo[start] = 0;
		Inter p = inters[start];
		Inter n = new Inter(start, p.hasExit());
		n.setDistTo(0);
		pq.add(n);

		while (!pq.isEmpty()) {

			p = pq.poll();
			if (!canPassPolice && p.getIndex() == police)
				continue;

			// System.out.println("Tatt ut " + p.getIndex());
			visited[p.getIndex()] = true;
			for (Road r : roads)
				if (r.getA().getIndex() == p.getIndex())
					relax(p, r.getB(), r);
				else if (r.getB().getIndex() == p.getIndex())
					relax(p, r.getA(), r);

		}
	}

	private static void relax(Inter v, Inter u, Road r) {
		// System.out.println("Førsøker å relaxe " + u.getIndex() + " Bool " +
		// visited[u.getIndex()]);
		if (!visited[u.getIndex()] && distTo[u.getIndex()] > distTo[v.getIndex()] + r.getLength()) {
			// System.out.println("Ny dist " + distTo[v.getIndex()] +
			// r.getLength());
			distTo[u.getIndex()] = distTo[v.getIndex()] + r.getLength();
			pq.remove(u);
			Inter newU = new Inter(u.getIndex(), u.hasExit());
			newU.setDistTo(distTo[u.getIndex()]);
			pq.add(newU);
		}

	}

}
