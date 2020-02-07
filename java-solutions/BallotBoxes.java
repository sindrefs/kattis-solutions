package kattis;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

//Distributing Ballot Boxes
public class BallotBoxes {
    private static class Entry implements Comparable<Entry> {

        private int key;
        private double value;

        public Entry(int key, double value) {
            this.key = key;
            this.value = value;
        }

        public int getKey() {
            return key;
        }

        public double getValue() {
            return value;
        }

        @Override
        public int compareTo(Entry other) {
            return Double.compare(other.getValue(), value);
        }
    }

    public static void main(String[] args) {
        Kattio io = new Kattio(System.in, System.out);

        for (int test = 0; test < 3; test++) {
            // Number o f cities
            int n = io.getInt();

            // Number of boxes
            int b = io.getInt();

            if (n == -1 && b == -1)
                break;

            int[] inhab = new int[n + 1];
            int[] ballots = new int[n + 1];
            for (int i = 1; i <= n; i++)
                inhab[i] = io.getInt();

            Queue<Entry> pq = new PriorityQueue<>();

            for (int i = 1; i <= n; i++) {
                ballots[i] = 1;
                pq.add(new Entry(i, (inhab[i] / (double) ballots[i])));
            }
            b = b - n;

            while (b > 0) {
                Entry upper = pq.poll();
                int index = upper.getKey();

                ballots[index]++;
                b--;
                pq.add(new Entry(index, (inhab[index] / (double) ballots[index])));
            }

            Entry ans = pq.poll();
            System.out.println((int) Math.ceil(ans.getValue()));

            // double ans = inhab[index] / (double) ballots[index];
            // System.out.println((int) Math.ceil(ans));
        }
    }

}