import java.util.ArrayList;

public class FileComparator {
	private int[][] C;
	/**
	 * 각 Panel의 difference index를 저장하는 배열
	 * 같으면 0
	 * 다르면 1
	 */
	private ArrayList<Integer> diffLeft;
	private ArrayList<Integer> diffRight;
	private final Integer zero = 0;
	private final Integer one = 1;
	
	public FileComparator() {
		diffLeft = new ArrayList<Integer>();
		diffRight = new ArrayList<Integer>();
	}
	
	/** Computing the length of the LCS
	 * sequence alignment와 같은 알고리즘
	 * 길이가 m인 text1과 길이가 n인 text2를 입력받아 모든 1 <= i <= m 와 1 <= j <= n에 대해
	 * text1[1..i] 와 text2[1..j] 사이의 값에 대해 LCS를 연산하고,
	 * C[i, j]에 저장한다. C[m, n]은 text1과 text2에 대한 LCS 값을 가지게 된다.
	 */
	public int LCSLength(ArrayList<String> text1, ArrayList<String> text2) {
		int m = text1.size();
		int n = text2.size();
		C = new int[m + 1][n + 1];
		
		/* 행이나 열의 index가 0인 element는 0으로 초기화한다 */
		for (int i = 0; i <= m; i++)
			C[i][0] = 0;
		for (int j = 0; j <= n; j++)
			C[0][j] = 0;
	
		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++) {
				if (text1.get(i - 1).equals(text2.get(j - 1)))
					C[i][j] = C[i-1][j-1] + 1;
				else
					C[i][j] = (C[i][j-1] > C[i-1][j])? C[i][j-1] : C[i-1][j];
			}
		}
		return C[m][n];
	}
	
	
	
	/**
	 * print diff
	 */
	public void printDiff(int[][] C, ArrayList<String> text1, ArrayList<String> text2, int i, int j) {
		if (i > 0 && j > 0 && text1.get(i - 1).equals(text2.get(j-1))) {
			printDiff(C, text1, text2, i-1, j-1);
			System.out.println(i-1+":  " + text1.get(i-1));
		}
		else if (j > 0 && (i == 0 || C[i][j-1] >= C[i-1][j])) {
			printDiff(C, text1, text2, i, j - 1);
			System.out.println(i-1+":+ "+ text2.get(j-1));
		}
		else if (i > 0 && (j == 0 || C[i][j-1] < C[i-1][j])) {
			printDiff(C, text1, text2, i-1, j);
			System.out.println(i-1+":- " + text1.get(i-1));
		}
		else
			System.out.println("");
	}
	
	/**
	 * 
	 * @param C: matrix of LCS length between text 1 and text2
	 * @param text1
	 * @param text2
	 * @param i: length(size) of text1
	 * @param j: length(size) of text2
	 */
	public void compare(int[][] C, ArrayList<String> text1, ArrayList<String> text2, int i, int j) {
		if (i > 0 && j > 0 && text1.get(i - 1).equals(text2.get(j - 1))) {
			compare(C, text1, text2, i-1, j-1);
			this.diffLeft.add(i - 1, zero);
			this.diffRight.add(j - 1, zero);
		}
		else if (j > 0 && (i == 0 || C[i][j-1] >= C[i-1][j])) {
			compare(C, text1, text2, i, j - 1);
			this.diffRight.add(j - 1, one);
		}
		else if (i > 0 && (j == 0 || C[i][j-1] < C[i-1][j])) {
			compare(C, text1, text2, i-1, j);
			this.diffLeft.add(i - 1, one);
		}
	}
	
	
	/**
	 * @return C
	 */
	public int[][] getC() {
		return this.C;
	}

	
	
	public static void main(String[] args) {
		FileComparator fc = new FileComparator();
		ArrayList<String> s1 = new ArrayList<String>();
		ArrayList<String> s2 = new ArrayList<String>();
		s1.add("abcdef");
		s1.add("aaa");
		s1.add("same part");
		s1.add("diff part for a");
		s1.add("hihihi");
		
		s2.add("abcdef");
		s2.add("bbb");
		s2.add("same part");
		s2.add("hihihi");
		
		fc.LCSLength(s1, s2);
		//fc.printDiff(fc.getC(), s1, s2, s1.size(), s2.size());
		fc.compare(fc.getC(), s1, s2, s1.size(), s2.size());
		

		System.out.println("Left Panel=========");
		for (int i = 0; i < s1.size(); i++) {
			if(fc.diffLeft.get(i) == 0)
				System.out.print("  ");
			else
				System.out.print("- ");
			
			System.out.println(s1.get(i));
		}

		System.out.println("\nRight Panel=========");
		for (int i = 0; i < s2.size(); i++) {
			if(fc.diffRight.get(i) == 0)
				System.out.print("  ");
			else
				System.out.print("+ ");
			
			System.out.println(s2.get(i));
		}
		
	}
}















