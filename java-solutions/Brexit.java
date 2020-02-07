package kattis;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

//Brexit
public class Brexit {

	private static int C;
	private static int P;
	private static int X;
	private static int L;
	private static HashMap<Integer, Set<Integer>> partnerships;
	private static HashMap<Integer, Integer> init;

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in, System.out);
		// Nr of countries
		C = io.getInt();
		// Nr of partnerships
		P = io.getInt();
		// My country
		X = io.getInt();
		// First county out of union
		L = io.getInt();

		partnerships = new HashMap<Integer, Set<Integer>>();
		init = new HashMap<Integer, Integer>();

		for (int i = 1; i <= C; i++) {
			HashSet<Integer> set = new HashSet<Integer>();
			partnerships.put(i, set);
		}

		for (int i = 0; i < P; i++) {
			int a = io.getInt();
			int b = io.getInt();
			partnerships.get(a).add(b);
			partnerships.get(b).add(a);
		}

		for (int i = 1; i <= C; i++)
			init.put(i, partnerships.get(i).size());

		Queue<Integer> q = removeCountry(L);
		Queue<Integer> temp;
		int now = -1;
		while (!q.isEmpty()) {
			now = q.poll();
			if (partnerships.get(now).size() <= (init.get(now) / 2)) {
				temp = new LinkedList<Integer>();
				temp = removeCountry(now);
				q.addAll(temp);
			}
		}

		// removeC(L);

		if (partnerships.get(X).size() <= (init.get(X) / 2))
			System.out.println("leave");
		else
			System.out.println("stay");

		io.close();
	}

	

	private static Queue<Integer> removeCountry(int removeThis) {
		Queue<Integer> adj = new LinkedList<Integer>();

		for (Integer n : partnerships.get(removeThis))
			adj.add(n);

		partnerships.get(removeThis).clear();

		for (Integer n : adj)
			partnerships.get(n).remove(removeThis);

		return adj;
	}

}
