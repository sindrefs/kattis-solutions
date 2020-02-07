package kattis;

//Turbo
public class Turbo {
	private static int[] tree;
	private static int[] originalInput;
	private static int offs;
	private static int[] ans;
	private static int runNr;
	private static int length;
	private static int[] posForNum;

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in, System.out);
		length = io.getInt();

		ans = new int[length];
		posForNum = new int[length + 1];

		// Calculating tree size and offset
		int N = 2;
		while ((2 * length) + 3 > N)
			N *= 2;
		offs = 2;
		while (length + 2 > offs)
			offs *= 2;

		tree = new int[N];
		originalInput = new int[length];

		for (int i = 0; i < length; i++) {
			int num = io.getInt();
			originalInput[i] = num;
			posForNum[num] = i;
		}

		int low = 1;
		int hi = length;
		runNr = 0;
		while (low < hi) {
			// Move lo from right to left, and rest (to left) over to right
			int lowPos = posForNum[low];
			ans[runNr] = Math.abs(traverseSum(posForNum[low]) + posForNum[low] - (low - 1));
			move(offs - 1, lowPos + offs, 1);

			runNr++;
			low++;

			// If pointers meet then break
			if (low >= hi)
				break;

			// Move hi from left to right, and rest (to right) over to left
			int hiPos = posForNum[hi];
			ans[runNr] = Math.abs(traverseSum(posForNum[hi]) + posForNum[hi] - (hi - 1));
			move(hiPos + offs, length + offs, -1);
			runNr++;
			hi--;

		}
		for (int i : ans)
			System.out.println(i);

	}

	public static void move(int L, int R, int way) {
		while (true) {
			// Check if the walked so they need to count
			boolean LRIGHT = (L % 2) == 0;
			boolean RLEFT = (R % 2) == 1;
			// Walk up
			L /= 2;
			R /= 2;
			// Have they met?
			if (R == L)
				break;

			if (LRIGHT)
				tree[(L * 2) + 1] += way;
			if (RLEFT)
				tree[R * 2] += way;
		}
	}

	private static int traverseSum(int s) {
		s += offs;
		int ans = 0;
		while (s > 0) {
			ans += tree[s];
			s /= 2;
		}
		return ans;
	}

}
