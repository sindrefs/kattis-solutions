package kattis;

import java.util.Scanner;

//Guess the Number
public class Guess {

	public static void main(String[] args) {
		guessing(1, 0, 1000);
	}

	public static void guessing(int cnt, int min, int max) {
		Scanner in = new Scanner(System.in);
		int guess = (max+min) / 2;
		// System.out.println("My guess is " + guess + " Current max " + max
		// + " Current min " + min);
		System.out.println(guess);
		String input = in.nextLine();

		if (input.equals("lower")) {
			max = guess;
		} else if (input.equals("higher"))
			min = guess + 1;
		else if (input.equals("correct"))
			cnt = 10;


		if (cnt < 10)
			guessing(cnt + 1, min, max);
	}

}
