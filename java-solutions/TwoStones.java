package kattis;

import java.util.Scanner;

//Take Two Stones
public class TwoStones {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int antall = in.nextInt();
		in.close();

		while (antall > 1) {
			antall = antall - 2;
		}
		if (antall == 1)
			System.out.println("Alice");
		else if (antall == 0)
			System.out.println("Bob");
	}

}
