package kattis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

//Muzicari
public class Muzicari {

	private static class Person {

		private int key;
		private int value;
		private boolean first;

		public Person(int key, int value) {
			this.key = key;
			this.value = value;
			this.first = false;
		}

		public int getKey() {
			return key;
		}

		public int getValue() {
			return value;
		}

		public void setFirst(boolean b) {
			first = b;
		}

		public boolean isFirst() {
			return first;
		}

		// @Override
		// public int compareTo(Person other) {
		// return Double.compare(other.getValue(), value);
		// }
	}

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in, System.out);

		// Length of concert
		int T = io.getInt();

		// Number of musicians
		int N = io.getInt();

		ArrayList<Person> people = new ArrayList<Person>();

		for (int i = 0; i < N; i++)
			people.add(new Person(i, io.getInt()));

		people.sort((personA, personB) -> personA.getValue() - personB.getValue());
		// Collections.sort(people);

		// for (int i = 0; i < N; i++) {
		// System.out.println(people.get(i).getKey() + " " +
		// people.get(i).getValue());
		// }

		int[][] knapsack = new int[N][T+1];

		for (int p = 0; p < N; p++)
			for (int w = 0; w < T+1 ; w++) {
				if (w == 0)
					knapsack[p][w] = 0;
				else if (p == 0) {
					if (people.get(p).getValue() <= w)
						knapsack[p][w] = people.get(p).getValue();
					else
						knapsack[p][w] = 0;
				} else if (people.get(p).getValue() <= w) {
					knapsack[p][w] = max(knapsack[p - 1][w],
							people.get(p).getValue() + knapsack[p - 1][w - people.get(p).getValue()]);
				} else
					knapsack[p][w] = knapsack[p - 1][w];
			}

		// for (int p = 0; p < N; p++) {
		// System.out.println();
		// for (int w = 0; w < T; w++) {
		// System.out.print(knapsack[p][w] + " ");
		// }
		// }

		int w = T;
		int p = N-1;

		while (p >= 0 && w > 0) {
			// System.out.println("p " + p + " w" + w);
			if (p == 0 && knapsack[p][w] != 0) {
				people.get(p).setFirst(true);
				break;
			}
			else if (knapsack[p][w] == knapsack[p - 1][w]) {
				p = p - 1;
			} else if (knapsack[p][w] != knapsack[p - 1][w]) {
				people.get(p).setFirst(true);
				w = w - people.get(p).getValue();
			} else
				break;

		}

		people.sort((personA, personB) -> personA.getKey() - personB.getKey());

		int first = 0;
		int snd = 0;
		int index = 0;

		while (index < N) {
			if (people.get(index).isFirst()) {
				System.out.print(first + " ");
				first = first + people.get(index).getValue();
				index++;
			} else {
				System.out.print(snd + " ");
				snd = snd + people.get(index).getValue();
				index++;
			}

		}

	}

	private static int max(int a, int b) {
		if (a > b)
			return a;
		else
			return b;
	}

}
