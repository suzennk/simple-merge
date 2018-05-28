import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/*패널간의 메소드를 관리하는 컨트롤러(interpanel controller),
 * compare, traverse, merge 메소드 포함 */
public class MergeController {

	private PanelController leftPanel;
	private PanelController rightPanel;
	private ArrayList<String> leftFileController;
	private ArrayList<String> rightFileController;
	/**
	 * currentIndex[2] = (start index, end index) 서로 다른 부분의 시작과 끝을 가리키는 index배열 (-1,
	 * -1)로 초기화됨
	 */
	private int[] currentIndex;
	/**
	 * 각 Panel의 difference index를 저장하는 배열 같은 내용을 가진 줄은 서로의 index를 저장하고 다른 내용을 가진 줄에는
	 * -1을 저장한다. ex) <Left> <Right> 0 abcdef abcdef 1 aaa bbb 2 same part same part
	 * 3 diff part for a hihihi 4 hihihi
	 * 
	 * <leftDiffIndex> <rightDiffIndex> [0] abcdef [0] abcdef [-1] aaa [-1] bbb [2]
	 * same part [2] same part [-1] diff 생략 a [4] hihihi [3] hihihi
	 */
	private ArrayList<Integer> leftDiffIndex;
	private ArrayList<Integer> rightDiffIndex;

	MergeController() {
		System.out.println("No input panels");
	}

	MergeController(PanelController leftPanel, PanelController rightPanel) {
		this.leftPanel = leftPanel;
		this.rightPanel = rightPanel;
		this.currentIndex = new int[] { -1, -1 };

		/* panel contents 받아와서 parsing 후 arraylist에 저장 */
		String[] array = leftPanel.getFileContent().split("\r\n");
		leftFileController = new ArrayList<String>(Arrays.asList(array));

		array = rightPanel.getFileContent().split("\r\n");
		rightFileController = new ArrayList<String>(Arrays.asList(array));

		/* FileComparator를 이용하여 compare 후 difference를 저장한 index 돌려받기 */
		FileComparator fc = new FileComparator(leftFileController, rightFileController);
		leftDiffIndex = fc.getDiffLeft();
		rightDiffIndex = fc.getDIffRight();
	}

	/**
	 * @return 현재 index 이전의 index중 값이 -1인 부분의 start, end index를 찾아서 return 
	 * 			찾지 못하거나 맨 처음에 다다르면  currentIndex값을 다시 return
	 */
	public int[] traversePrevious() {
		int index = this.currentIndex[0];
		int[] previous = new int[2];

		/* 가장 앞의 부분이면 그 부분을 다시 반환 */
		if (this.currentIndex[0] < 1 || this.currentIndex[1] < 1)
			return this.currentIndex;

		/* currentIndex 이전의 index중 -1값을 가진 가장 가까운 index를 end index에 저장 */
		while (index >= 0) {
			index--;
			if (this.leftDiffIndex.get(index) == -1)
				previous[1] = index;
		}

		/* end index 이전의 index 중 가장 처음으로 같은 부분이 나타나는 index의 다음 index를 start index에 저장 */
		while (index >= 0) {
			index--;
			if (this.leftDiffIndex.get(index) != -1)
				previous[0] = index + 1;
		}

		return previous;
	}

	
	/**
	 * @return 현재 index 이후의 index중 값이 -1인 부분의 start, end index를 찾아서 return 
	 * 			찾지 못하거나 마지막에 다다르면  currentIndex값을 다시 return
	 */
	public int[] traverseNext(int currentIndex) {
		int index = this.currentIndex[1];
		int[] next = new int[2];
		int maxIndex = (this.leftFileController.size() > this.rightFileController.size())
				? (this.leftFileController.size() - 1)
				: (this.rightFileController.size() - 1);

		/* 가장 앞의 부분이면 그 부분을 다시 반환 */
		if (this.currentIndex[0] == maxIndex || this.currentIndex[1] == maxIndex)
			return this.currentIndex;

		/* currentIndex 이후의 index중 -1값을 가진 가장 가까운 index를 start index에 저장 */
		while (index <= maxIndex) {
			index++;
			if (this.leftDiffIndex.get(index) == -1)
				next[0] = index;
		}

		/* end index 이후의 index 중 가장 처음으로 같은 부분이 나타나는 index의 이전 index를 end index에 저장 */
		while (index <= maxIndex) {
			index++;
			if (this.leftDiffIndex.get(index) != -1)
				next[0] = index - 1;
		}

		return next;
	}

	/**
	 * clear all the string and copy from right to left
	 */
	public void copyToLeft() {
		this.leftFileController.clear();
		this.leftFileController.addAll(this.rightFileController);
	}

	/**
	 * clear all the string and copy from left to right
	 */
	public void copyToRight() {
		this.rightFileController.clear();
		this.rightFileController.addAll(this.leftFileController);
	}

	public ArrayList<String> getLeftFileController() {
		return this.leftFileController;
	}

	public ArrayList<String> getRightFileController() {
		return this.rightFileController;
	}

	public ArrayList<Integer> getLeftDiffIndex() {
		return this.leftDiffIndex;
	}

	public ArrayList<Integer> getRightDiffIndex() {
		return this.leftDiffIndex;
	}

	public static void main(String[] args) {
		/*
		 * This main is a test for this class. You can delete this if you don't need.
		 */

	}
}
