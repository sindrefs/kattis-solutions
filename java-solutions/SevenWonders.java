package kattis;

import java.util.Scanner;

//Seven Wonders
public class SevenWonders {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String input = in.nextLine();
		int type1 = 0;
		int type2 = 0;
		int type3 = 0;
		int unique = 0;
		int result = 0;

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c == 'T')
				type1++;
			else if (c == 'C')
				type2++;
			else if (c == 'G')
				type3++;
		}
		unique = Math.min(Math.min(type1, type2), type3);
		result = type1 * type1 + type2 * type2 + type3 * type3 + unique * 7;
		System.out.println(result);

	}

}
