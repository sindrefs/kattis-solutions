package kattis;

import java.util.Scanner;

//Oddities
public class Oddities {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int antall = in.nextInt();
		for(int i = 0; i < antall; i++){
			int tall = in.nextInt();
			boolean even = ((tall % 2) == 0);
			if (even)
				System.out.println(tall + " is even");
			else
				System.out.println(tall + " is odd");
		}
		in.close();

	}

}
