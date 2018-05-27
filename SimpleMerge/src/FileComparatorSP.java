import java.util.ArrayList;

public class FileComparatorSP {
	/**
	 * LCS length를 저장하는 배열
	 * sequence alignment 알고리즘이나 LCS 알고리즘 참고
	 */
	private int[][] C;
	/**
	 * 각 Panel의 difference index를 저장하는 배열
	 * 같은 내용을 가진 줄은 서로의 index를 저장하고
	 * 다른 내용을 가진 줄에는 -1을 저장한다.
	 * ex)
	 * <Left>	<diffLeft>	<Right>	<diffRight>
	 * abc		0			abc		0
	 * de		-1			fgh		2
	 * fgh		1
	 */
	private ArrayList<Integer> diffLeft;
	private ArrayList<Integer> diffRight;

	FileComparatorSP() {
		
		System.out.println("We need two files");
	}

	FileComparatorSP(String s1, String s2){
		
		LCSLength(s1.toCharArray(),s2.toCharArray());
		diffLeft = new ArrayList<Integer>();
		diffRight = new ArrayList<Integer>();
		compare();
	}
	
	/**
	 * Computing the length of the LCS sequence alignment와 같은 알고리즘 길이가 m인 text1과 길이가
	 * n인 text2를 입력받아 모든 1 <= i <= m 와 1 <= j <= n에 대해 text1[1..i] 와 text2[1..j] 사이의
	 * 값에 대해 LCS를 연산하고, C[i, j]에 저장한다. C[m, n]은 text1과 text2에 대한 LCS 값을 가지게 된다.
	 */
	void LCSLength(char[] text1, char[] text2) {
		int m = text1.length;
		int n = text2.length;
		C = new int[m + 1][n + 1];

		/* 행이나 열의 index가 0인 element는 0으로 초기화한다 */
		for (int i = 0; i <= m; i++)
			C[i][0] = 0;
		for (int j = 0; j <= n; j++)
			C[0][j] = 0;

		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++) {
				if (text1[i - 1] == text2[j - 1])
					C[i][j] = C[i - 1][j - 1] + 1;
				else
					C[i][j] = (C[i][j - 1] > C[i - 1][j]) ? C[i][j - 1] : C[i - 1][j];
			}
		}
		;
	}
	
	/*
	 diffLeft와 diffRight에는 같은 부분의 내용의 index가 들어간다. 고로, 두 ArrayList의 size는 같게 된다.
	 (이렇게 하는 이유는 MergeController 보면 알 수 있다.)
	 */
	void compare(){
		
		int i = C.length-1;										//Last index of rows
		int j = C[0].length-1;									//Last index of columns
		
		
		while(C[i][j]!=0){										//terminate at 0th row, 0th column
			
			if(C[i][j] == C[i][j-1]){							
				
				j--;
			}
			
			else if(C[i][j] == C[i-1][j]){
				
				i--;
			}
			
			else{
				
				this.diffLeft.add(0,i-1);						//i가 아닌 i-1은 diffLeft index를 위함
				this.diffRight.add(0,j-1);						//j가 아닌 j-1은 diffRight index를 위함
				
				i--;
				j--;
			}
		}
	}

	
	/**
	 * @return diffLeft
	 */
	ArrayList<Integer> getDiffLeft(){
		return this.diffLeft;
	}
	
	/**
	 * @return diffRight
	 */
	ArrayList<Integer> getDiffRight(){
		return this.diffRight;
	}

	public static void main(String[] args) {
		/* This main is a test for this class.
		 * You can delete this if you don't need.
		 * */
		FileComparatorSP fc = new FileComparatorSP("AGGTAB","GXXTAYYB");					//이 예제대로면 값은 {1,3,4,5} & {0,3,4,7}
		ArrayList<Integer> s1 = fc.getDiffLeft();
		ArrayList<Integer> s2 = fc.getDiffRight();

		System.out.println("Left Panel=========");
		for (int i = 0; i < s1.size(); i++) {
			System.out.println(s1.get(i));
		}

		System.out.println("\nRight Panel=========");
		for (int i = 0; i < s2.size(); i++) {
			System.out.println(s2.get(i));
		}

	}
}
