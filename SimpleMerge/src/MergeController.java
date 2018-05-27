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
	private FileComparator fc;
	private int[] diffIndex;
	
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
		
		/* left, right Panel의 속성에서 String을 받아와서
		 * ArrayList로 만들어야함
		 * 그리고 diffIndex를 생성
		 * FileComparator 생성
		 */
	}
	
	public int[] getDiffIndex() {
		return this.diffIndex;
	}
	
	public int traversePrevious() {
		
		int index = -1;
		// TODO
		
		return index;
	}
	
	public int traverseNext() {
		
		int index = -1;
		// TODO
		
		return index;
	}
	
	public void copyToLeft() {
		// TODO
	}
	
	public void copyToRight() {
		// TODO
	}	
}
