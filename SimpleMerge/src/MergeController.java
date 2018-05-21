import java.io.*;
import java.util.ArrayList;

/*패널간의 메소드를 관리하는 컨트롤러(interpanel controller),
 * compare, traverse, merge 메소드 포함 */
public class MergeController {
	
	private PanelInfo leftPanel;
	private PanelInfo rightPanel;
	private ArrayList<String> leftFileController;
	private ArrayList<String> rightFileController;
	private FileComparator fc;
	private int[] diffIndex;
	
	MergeController(){
		System.out.println("No input panels");
	}
	
	MergeController(PanelInfo leftPanel, PanelInfo rightPanel) {
		this.leftPanel = leftPanel;
		this.rightPanel = rightPanel;
		/* left, right Panel의 속성에서 String을 받아와서
		 * ArrayList로 만들어야함
		 * 그리고 diffIndex를 생성
		 * FileComparator 생성
		 */
	}
	
	int[] getDiffIndex() {
		return this.diffIndex;
	}
	
	int traversePrevious() {
		
		int index = -1;
		// TODO
		
		return index;
	}
	
	int traverseNext() {
		
		int index = -1;
		// TODO
		
		return index;
	}
	
	void copyToLeft() {
		// TODO
	}
	
	void copyToRight() {
		// TODO
	}	
}
