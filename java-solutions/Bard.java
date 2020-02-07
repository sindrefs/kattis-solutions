package kattis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

//Bard
public class Bard {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		// System.out.println("Input inhabitants..");
		int inhab = in.nextInt();
		// System.out.println("Input nights..");
		int nights = in.nextInt();
		in.nextLine();

		HashMap<Integer, Set<Integer>> map = new HashMap<Integer, Set<Integer>>();

		for (int i = 1; i <= inhab; i++) {
			HashSet<Integer> set = new HashSet<Integer>();
			map.put(i, set);
		}

		int song = 0;
		for (int i = 0; i < nights; i++) {
			// System.out.println("Gi info for kveld " + (i + 1));
			String[] input = in.nextLine().split(" ");

			HashSet<Integer> present = new HashSet<Integer>();
			for (int j = 1; j < input.length; j++)
				present.add(Integer.parseInt(input[j]));

			if (present.contains(1)) {
				for (Integer pers : present)
					map.get(pers).add(song);
				song++;
				// System.out.println("Vi er på sang nr" + song);
			} else {
				HashSet<Integer> knownSongs = new HashSet<Integer>();
				for (Integer pers : present)
					knownSongs.addAll(map.get(pers));
				for (Integer pers : present)
					map.get(pers).addAll(knownSongs);
			}

		} // end of big for-loop

		HashSet<Integer> allSongs = new HashSet<Integer>();
		for (int i = 0; i < song; i++)
			allSongs.add(i);

		// allSongs.forEach(System.out::println);

		HashSet<Integer> knowAll = new HashSet<Integer>();
		for (int i = 1; i <= map.size(); i++) {
			// System.out.println("Pers " + i);
			// map.get(i).forEach(System.out::println);

			if (map.get(i).containsAll(allSongs))
				knowAll.add(i);
		}
		knowAll.stream().sorted().forEach(System.out::println);
	}

}
