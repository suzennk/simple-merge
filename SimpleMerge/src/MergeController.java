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
	 * 각 Panel의 difference index를 저장하는 배열
	 * 같은 내용을 가진 줄은 서로의 index를 저장하고
	 * 다른 내용을 가진 줄에는 -1을 저장한다.
	 * ex)
	 * 		<Left>			<Right>	
	 * 0	abcdef			abc		
	 * 1	aaa				bbb		
	 * 2	same part		same part
	 * 3	diff part for a	hihihi
	 * 4	hihihi
	 * 
	 * <leftDiffIndex>	<rightDiffIndex>
	 * [0] abcdef		[0] abcdef
	 * [-1] aaa			[-1] bbb
	 * [2] same part	[2] same part
	 * [-1] diff 생략 a	[4] hihihi
	 * [3] hihihi
	 */
	private ArrayList<Integer> leftDiffIndex;
	private ArrayList<Integer> rightDiffIndex;
	
	
	MergeController(){
		System.out.println("No input panels");
	}
	
	
	MergeController(PanelController leftPanel, PanelController rightPanel) {
		this.leftPanel = leftPanel;
		this.rightPanel = rightPanel;
		
		/* panel contents 받아와서 parsing 후 arraylist에  저장 */
		String[] array = leftPanel.getFileContent().split("\r\n");
		leftFileController = new ArrayList<String>(Arrays.asList(array));

		array = rightPanel.getFileContent().split("\r\n");
		rightFileController = new ArrayList<String>(Arrays.asList(array));
	
		/* FileComparator를 이용하여 compare 후 difference를 저장한 index 돌려받기*/
		FileComparator fc = new FileComparator(leftFileController, rightFileController);
		leftDiffIndex = fc.getDiffLeft();
		rightDiffIndex = fc.getDIffRight();
	}
	
	/**
	 * @param currentIndex: current index value
	 * @return 현재 index 이전의 index중 값이 -1인 index를 찾아 return함
	 * 		index가 0이 될 때까지 다른 부분을 찾지 못하면 currentIndex값을 다시 return
	 */
	public int traversePrevious(int currentIndex) {
		int index = currentIndex;
		
		while(index >= 0) {
			index--;
			if(index == -1)
				return index;
		}
		
		return currentIndex;
	}
	
	/**
	 * @param currentIndex: current index value
	 * @return 현재 index 이후의 index중 값이 -1인 index를 찾아 return함
	 * 		index가 마지막에 다다를 때까지 다른 부분을 찾지 못하면 currentIndex값을 다시 return
	 */
	public int traverseNext(int currentIndex) {
		int index = currentIndex;
		int maxIndex = (this.leftFileController.size() > this.rightFileController.size()) ?
				this.leftFileController.size() : this.rightFileController.size();
		
		while(index < maxIndex) {
			index++;
			if(index == -1)
				return index;
		}
		
		return currentIndex;
	}
	
	
	/**
	 *	clear all the string and copy from right to left 
	 */
	public void copyToLeft() {
		this.leftFileController.clear();
		this.leftFileController.addAll(this.rightFileController);
	}
	

	/**
	 *	clear all the string and copy from left to right
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
}
