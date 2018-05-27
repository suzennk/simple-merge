import java.util.ArrayList;

public class FileComparator {
	private int[][] C;
	
	/** <sequence alignment>
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
				if (text1.get(i).equals(text2.get(j)))
					C[i][j] = C[i-1][j-1] + 1;
				else
					C[i][j] = (C[i][j-1] > C[i-1][j])? C[i][j-1] : C[i-1][j];
			}
		}
		
		return C[m][n];
	}
	
	/**
	 *	backtracking을 이용하여 C의 표를 연산한다. 
	 */
	public String backtrack(int[][] C, ArrayList<String> text1, ArrayList<String> text2, int i, int j) {
		if (i == 0 || j == 0)
			return "";
		else if (text1.get(i).equals(text2.get(j)))
			return backtrack(C, text1, text2, i - 1, j - 1) + text1.get(i);
		else {
			if (C[i][j - 1] > C[i - 1][j])
				return backtrack(C, text1, text2, i, j-1);
			else
				return backtrack(C, text1, text2, i-1, j);
		}
	}
	
	
}