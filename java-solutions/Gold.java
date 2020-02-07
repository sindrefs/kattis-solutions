package kattis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

//Getting Gold
public class Gold {

	private static int W;
	private static int H;

	public static void main(String[] args) {

		// Reading board started
		Kattio io = new Kattio(System.in, System.out);
		W = io.getInt();
		H = io.getInt();

		char[][] board = new char[H][W];
		for (int h = 0; h < H; h++) {
			String thisLine = io.getWord();
			for (int w = 0; w < W; w++) {
				board[h][w] = thisLine.charAt(w);
			}
		}

		// printBoard(board);

		// io.close(); // Reading board complete

		int boardsize = H * W;
		HashMap<Integer, Set<Integer>> connections = new HashMap<Integer, Set<Integer>>();
		for (int i = 0; i <= boardsize; i++) {
			HashSet<Integer> set = new HashSet<Integer>();
			connections.put(i, set);
		}

		int player = -1;
		for (int h = 0; h < H; h++)
			for (int w = 0; w < W; w++) {
				// System.out.println("Vi er på følgende pos h: " + h + " w: " +
				// w);

				int here = h * W + w;

				if (board[h][w] == 'P')
					player = here;

				if (trapNearby(h, w, board)) {
					// System.out.println("breaking");
					continue;
				}

				// Check north
				if (h > 0)
					if (board[h - 1][w] != '#')
						connections.get(here).add(here - W);

				// Check south
				if (h < H - 1)
					if (board[h + 1][w] != '#')
						connections.get(here).add(here + W);

				// Check west
				if (w > 0)
					if (board[h][w - 1] != '#')
						connections.get(here).add(here - 1);

				// Check east
				if (w < W - 1)
					if (board[h][w + 1] != '#')
						connections.get(here).add(here + 1);
			}

		// printConnections(boardsize, connections);

		boolean[] visited = new boolean[boardsize];
		Stack<Integer> stack = new Stack<Integer>();

		stack.push(player);

		while (!stack.isEmpty()) {
			int pos = stack.pop();
			if (visited[pos] == false) {
				visited[pos] = true;
				for (Integer adj : connections.get(pos))
					stack.push(adj);
			}
		}
		int goldCnt = 0;
		for (int i = 0; i < visited.length; i++)
			if (visited[i] == true)
				if (board[(i / W)][(i % W)] == 'G')
					goldCnt++;

		System.out.println(goldCnt);
		io.close();
	}

	private static boolean trapNearby(int h, int w, char[][] board) {
		// Check north
		if (h > 0)
			if (board[h - 1][w] == 'T')
				return true;
		// Check south
		if (h < H - 1)
			if (board[h + 1][w] == 'T')
				return true;
		// Check west
		if (w > 0)
			if (board[h][w - 1] == 'T')
				return true;

		// Check east
		if (w < W - 1)
			if (board[h][w + 1] == 'T')
				return true;

		return false;

	}

	private static void printConnections(int boardsize, HashMap<Integer, Set<Integer>> connections) {
		for (int i = 0; i < boardsize; i++) {
			connections.get(i).forEach(System.out::print);
			System.out.println();
		}
	}

	private static void printBoard(char[][] board) {
		System.out.println("\nHer er kartet ditt:");
		for (int h = 0; h < H; h++) {
			System.out.println();
			for (int w = 0; w < W; w++)
				System.out.print(board[h][w]);
		}
		System.out.println("\n");
	}

}
