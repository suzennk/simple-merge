import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/*패널간의 메소드를 관리하는 컨트롤러(interpanel controller),
 * compare, traverse, merge 메소드 포함 */
public class MergeController {

	private PanelController leftPanel;
	private PanelController rightPanel;
	private ArrayList<String> leftFileContents;
	private ArrayList<String> rightFileContents;
	/**
	 * 이거 수정해야돼
	 */
	private int[] currentIndex;
	/**
	 * 각 Panel의 difference index를 저장하는 배열
	 * index는 0이 아닌 1부터 시작!
	 * 같은 내용을 가진 줄에는 서로의 index, 다른 내용을 가진 줄에는 (-1)*index,
	 * 공백줄은 0을 저장
	 */
	private ArrayList<Integer> leftDiffIndex;
	private ArrayList<Integer> rightDiffIndex;
	private ArrayList<Integer> leftViewIndex;
	private ArrayList<Integer> rightViewIndex;
	private boolean hasTraversed;

	MergeController() {
		System.out.println("No input panels");
	}

	MergeController(PanelController leftPanel, PanelController rightPanel) {
		this.leftPanel = leftPanel;
		this.rightPanel = rightPanel;
		this.currentIndex = new int[] { -1, -1, -1, -1 };

		/* panel contents 받아와서 parsing 후 arraylist에 저장 */
		String[] array = leftPanel.getFileContent().split("\r\n");
		this.leftFileContents = new ArrayList<String>(Arrays.asList(array));
		this.leftFileContents.add(0, "");

		array = rightPanel.getFileContent().split("\r\n");
		this.rightFileContents = new ArrayList<String>(Arrays.asList(array));
		this.rightFileContents.add(0, "");
		
		/* FileComparator를 이용하여 compare 후 difference를 저장한 index 돌려받기 */
		FileComparator fc = new FileComparator(leftFileContents, rightFileContents);
		this.leftDiffIndex = fc.getDiffLeft();
		this.rightDiffIndex = fc.getDIffRight();
		this.leftViewIndex = new ArrayList<Integer>();
		this.rightViewIndex = new ArrayList<Integer>();
		
		
		this.compare();
		this.hasTraversed = false;
	}

	/**
	 * previous = { left start point, left end point, right start point, right end
	 * point }
	 * 
	 * @return 현재 index 이전의 index중 값이 -1인 부분의 start, end index를 찾아서 return 찾지 못하거나 맨
	 *         처음에 다다르면 currentIndex값을 다시 return
	 */
	public int[] traversePrevious() {
		int[] previous = new int[4];

		/* 처음으로 탐색하는 경우 traverseNext값을 return 함 */
		if (this.hasTraversed == false) {
			previous = this.traverseNext();
			return previous;
		}

		for (int i = 0; i < 4; i++)
			this.currentIndex[i] = previous[i];
		this.hasTraversed = true;

		return previous;
	}

	/**
	 * @return 현재 index 이후의 index중 값이 -1인 부분의 start, end index를 찾아서 return 찾지 못하거나
	 *         마지막에 다다르면 currentIndex값을 다시 return
	 */
	public int[] traverseNext() {
		int[] next = new int[4];

		return next;
	}

	/**
	 * clear all the string and copy from right to left
	 */
	public void copyToLeft() {
		this.leftFileContents.clear();
		this.leftFileContents.addAll(this.rightFileContents);
	}

	/**
	 * clear all the string and copy from left to right
	 */
	public void copyToRight() {
		this.rightFileContents.clear();
		this.rightFileContents.addAll(this.leftFileContents);
	}

	public void compare() {
		this.leftViewIndex.clear();
		this.rightViewIndex.clear();
		int L = 0, R = 0;
		int L_Max = this.leftDiffIndex.size();
		int R_Max = this.rightDiffIndex.size();
		
		while (L < L_Max && R < R_Max) {
			/* 같은 string인 경우 해당 string의 index를 저장*/
			if (this.leftDiffIndex.get(L) != -1 && this.rightDiffIndex.get(R) != -1) {
				this.leftViewIndex.add(L++);
				this.rightViewIndex.add(R++);				
			}
			/* 다른 string이지만 같은 line에 있는 경우 해당 string의 index*(-1)을 저장 */
			else if (this.leftDiffIndex.get(L) == -1 && this.rightDiffIndex.get(R) == -1) {
				this.leftViewIndex.add(L * (-1)); L++;
				this.rightViewIndex.add(R * (-1)); R++;
			}
			/* 한쪽 패널에만 내용이 존재하는 경우 내용을 해당 패널에 저장하고 다른 패널에는 공백(0)을 저장 */
			else {
				while (this.rightDiffIndex.get(R) == -1) {
					this.leftViewIndex.add(0);
					this.rightViewIndex.add(R++);
				}
				while (this.leftDiffIndex.get(L) == -1) {
					this.leftViewIndex.add(L++);
					this.rightViewIndex.add(0);
				}
			}
		}

	}

	public ArrayList<String> getLeftFileContents() {
		return this.leftFileContents;
	}

	public ArrayList<String> getRightFileContents() {
		return this.rightFileContents;
	}

	public ArrayList<Integer> getLeftDiffIndex() {
		return this.leftDiffIndex;
	}

	public ArrayList<Integer> getRightDiffIndex() {
		return this.leftDiffIndex;
	}
	
	public ArrayList<Integer> getLeftViewIndex() {
		return this.leftViewIndex;
	}
	
	public ArrayList<Integer> getRightViewIndex() {
		return this.rightViewIndex;
	}

	
	/**
	 * Used for the test of main class of this class.
	 * You can delete this method.
	 * It prints the index which includes blanks.
	 */
	public void printArranged() {
		System.out.println("Left Panel=========");
		for (int i = 1; i < this.leftViewIndex.size(); i++)
			System.out.println("["+i+"] "+this.leftViewIndex.get(i));

		System.out.println("\nRight Panel=========");
		for (int i = 1; i < this.rightViewIndex.size(); i++)
			System.out.println("["+i+"] "+this.rightViewIndex.get(i));
	}
	
	/**
	 * Used for the test of main class of this class.
	 * You can delete this method.
	 * It prints the index which does not include blanks.
	 */
	public void printAll() {
		System.out.println("Left Panel=========");
		for (int i = 1; i < this.leftFileContents.size(); i++)
			System.out.println(this.leftFileContents.get(i));

		System.out.println("\nRight Panel=========");
		for (int i = 1; i < this.rightFileContents.size(); i++)
			System.out.println(this.rightFileContents.get(i));
	}

	public static void main(String[] args) {
		/*
		 * This main is a test for this class. You can delete this if you don't need.
		 */
		Scanner s = new Scanner(System.in);
		int[] index = new int[2];
		PanelController left = new PanelController();
		PanelController right = new PanelController();

		left.setFileContent("same part1\r\n" + 
				"same part2\r\n" + 
				"different but same line.1\r\n" + 
				"different but same line.2\r\n" + 
				"same part3\r\n" + 
				"same part4\r\n" + 
				"same part5\r\n" + 
				"same part6\r\n" + 
				"different part-a\r\n" + 
				"different part-a\r\n" + 
				"different part-a\r\n" + 
				"same part7\r\n" + 
				"same part8");

		right.setFileContent("same part1\r\n" + 
				"same part2\r\n" + 
				"different but same line.3\r\n" + 
				"different but same line.4\r\n" + 
				"different part-b\r\n" + 
				"different part-b\r\n" + 
				"different part-b\r\n" + 
				"different part-b\r\n" + 
				"same part3\r\n" + 
				"same part4\r\n" + 
				"same part5\r\n" + 
				"different part-b\r\n" + 
				"different part-b\r\n" + 
				"same part6\r\n" + 
				"same part7\r\n" + 
				"same part8");

		MergeController mc = new MergeController(left, right);

		boolean iterate = true;
		while (iterate) {
			System.out.println("\n1. print");
			System.out.println("2. print arranged");
			System.out.println("3. traverse previous");
			System.out.println("4. traverse next");
			System.out.println("5. copy to left");
			System.out.println("6. copy to right");
			System.out.println("7. exit");
			System.out.print("Select menu: ");
			int menu = s.nextInt();
			switch (menu) {

			case 1:
				mc.printAll();
				break;
			case 2:
				mc.printArranged();
				break;
			case 3:
				index = mc.traversePrevious();
				if (index[0] != -1 && index[1] != -1) {
					System.out.println("Traverse line " + index[0] + " to " + index[1]);
					for (int i = index[0]; i <= index[1]; i++)
						System.out.println(mc.leftFileContents.get(i));
				}
				break;
			case 4:
				index = mc.traverseNext();
				if (index[0] != -1 && index[1] != -1) {
					System.out.println("Traverse line " + index[0] + " to " + index[1]);
					for (int i = index[0]; i <= index[1]; i++)
						System.out.println(mc.leftFileContents.get(i));
				}
				break;
			case 5:
				mc.copyToLeft();
				break;
			case 6:
				mc.copyToRight();
				break;
			case 7:
				iterate = false;
				break;
			default:
				System.out.println("wrong choice");
				continue;
			}

		}
		System.out.println("Exit Program.");
		s.close();
	}

}