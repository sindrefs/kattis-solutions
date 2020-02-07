package kattis;

import java.util.ArrayList;

//The Citrus Intern
public class CitrusIntern {
	private static class Person {
		int cost;
		int index;
		ArrayList<Person> under;

		public Person(int index) {
			this.index = index;
			under = new ArrayList<Person>();

		}

		public int getIndex() {
			return index;
		}

		public void setCost(int c) {
			cost = c;
		}

		public int getCost() {
			return cost;
		}

		public void addUnder(Person p) {
			under.add(p);
		}

		public ArrayList<Person> getUnder() {
			return under;
		}

	}

	private static class Connection {
		Person from;
		Person to;

		public Connection(Person from, Person to) {
			this.from = from;
			this.to = to;
		}

		public Person getFrom() {
			return from;

		}

		public Person getTo() {
			return to;
		}
	}

	private static ArrayList<Person> people;
	private static int N;
	private static long[] with;
	private static long[] best;
	private static long[] without;

	public static void main(String[] args) {
		read();
		// print();

		with = new long[N];
		best = new long[N];
		without = new long[N];

		int boss = getBoss().getIndex();
		fillTable(boss);
		System.out.println(Math.min(with[boss], without[boss]));
	}

	public static void fillTable(int v) {
		// printt("In", in);
		// printt("outDown", outDown);
		// printt("outUp", outUp);

		ArrayList<Person> under = people.get(v).getUnder();

		with[v] = people.get(v).getCost();
		without[v] = Long.MAX_VALUE;
		best[v] = 0;

		if (under.size() == 0)
			return;

		long upgrade = Long.MAX_VALUE;
		long tempOD = 0;

		for (Person u : under) {
			fillTable(u.getIndex());

			with[v] += best[u.getIndex()];
			tempOD += Math.min(with[u.getIndex()], without[u.getIndex()]);
			upgrade = Math.min(upgrade, Math.max(with[u.getIndex()] - without[u.getIndex()], 0));
			best[v] += Math.min(with[u.getIndex()], without[u.getIndex()]);
		}
		without[v] = tempOD + upgrade;

	}

	private static void read() {
		Kattio io = new Kattio(System.in, System.out);
		N = io.getInt();

		ArrayList<Connection> connections = new ArrayList<Connection>();
		people = new ArrayList<Person>(N);
		for (int i = 0; i < N; i++)
			people.add(new Person(i));

		for (int i = 0; i < N; i++) {
			int cost = io.getInt();
			people.get(i).setCost(cost);
			int sub = io.getInt();
			for (int j = 0; j < sub; j++) {
				int adj = io.getInt();
				connections.add(new Connection(people.get(i), people.get(adj)));
			}

		}

		for (int i = 0; i < connections.size(); i++) {
			Person from = connections.get(i).getFrom();
			Person to = connections.get(i).getTo();
			people.get(from.getIndex()).addUnder(to);
		}

	}

	private static void printt(String name, long[] l) {
		System.out.print("\n" + name + "  ");
		for (long i : l)
			System.out.print(i + " ");
	}

	private static void print() {
		for (Person p : people) {
			System.out.print(p.getIndex() + "  kids  ");
			for (Person pp : p.getUnder())
				System.out.print(pp.getIndex() + " ");
			System.out.println();
		}
	}

	public static Person getBoss() {
		boolean[] bool = new boolean[N];

		for (int i = 0; i < N; i++) {
			ArrayList<Person> under = people.get(i).getUnder();
			for (Person c : under)
				bool[c.getIndex()] = true;
		}
		for (int i = 0; i < N; i++)
			if (!bool[i])
				return people.get(i);

		return null;
	}
}
