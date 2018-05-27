import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/*패널간의 메소드를 관리하는 컨트롤러(interpanel controller),
 * compare, traverse, merge 메소드 포함 */
public class MergeControllerSP {
	
	private String leftPanelContent;
	private String rightPanelContent;
	private FileComparatorSP fc;
	private ArrayList<Integer> diffLeft;
	private ArrayList<Integer> diffRight;
	private ArrayList<int[]> leftTraversePoint;
	private ArrayList<int[]> rightTraversePoint;
	
	MergeControllerSP(){
		System.out.println("No input panels");
	}
	
	MergeControllerSP(PanelController leftPanel, PanelController rightPanel) {
		
		leftPanelContent = leftPanel.getFileContent();
		rightPanelContent = rightPanel.getFileContent();
		
		fc = new FileComparatorSP(leftPanelContent, rightPanelContent);
		
		diffLeft = fc.getDiffLeft();
		diffRight = fc.getDiffRight();
		
		leftTraversePoint = new ArrayList<int[]>();
		rightTraversePoint = new ArrayList<int[]>();
		leftTraversePoint = arrangeTraverseSamePoint(leftTraversePoint, diffLeft);
		rightTraversePoint = arrangeTraverseSamePoint(rightTraversePoint, diffRight);
		arrangeTraverseAdditionalPoint();
	}
	
	/*
	  두 개의 텍스트 사이 공통된 부분이 연속되어있는 index들을 ArrayList에서 제거한다. 
	  
	  example:
	  
	  "AGGTAB" "GXXTAYYB" -> {1,3,4,5}, {0,3,4,7}
	  모든 원소는 char index.
	  
	  3과 4 부분은 연속된 부분이다. 
	  두 텍스트의 틀린부분을 begin index와 end index 이용해 배열화 시킴. {begin, end}
	  따라서 AL는 { {1,3} , {4,5} }, { {0,3}, {4,7} }
	 */
	ArrayList<int[]> arrangeTraverseSamePoint(ArrayList<int[]> traversePoints, ArrayList<Integer> diffAL){			
		
		for(int i = 1; i<diffAL.size(); i++){
			
			int begin = diffAL.get(i-1);
			int end = diffAL.get(i);
			
			if(end - begin != 1)
				traversePoints.add(new int[] {begin, end});
		}
		
		return traversePoints;
	}
	
	/*
	 두 개의 텍스트에서 한 쪽에만 제일 앞 또는 뒤에 내용이 추가되어 있는경우를 정렳한다.  
	 
	 
	 */
	void arrangeTraverseAdditionalPoint(){
		
		if(diffLeft.get(0) != 0 || diffRight.get(0) != 0){
			
			leftTraversePoint.add(0, new int[]{0, diffLeft.get(0)});
			rightTraversePoint.add(0, new int[]{0, diffRight.get(0)});
		}
		
		int leftLastIndex = diffLeft.size()-1;
		int rightLastIndex = diffRight.size()-1;
		
		if(diffLeft.get(leftLastIndex) != leftPanelContent.length()-1 || diffRight.get(rightLastIndex) != rightPanelContent.length()-1 ){
			
			leftTraversePoint.add(new int[]{ diffLeft.get(leftLastIndex), leftPanelContent.length()-1});
			rightTraversePoint.add(new int[]{ diffRight.get(rightLastIndex) , rightPanelContent.length()-1 });
		}

	}
	
	
	ArrayList<int[]> getLeftTraversePoint() {
		
		return this.leftTraversePoint;
	}
	
	ArrayList<int[]> getRightTraversePoint() {
		
		return this.rightTraversePoint;
	}
	
	public void copyToLeft() {
		// TODO
	}
	
	public void copyToRight() {
		// TODO
	}	
	
	public static void main(String[] args) {
		/* This main is a test for this class.
		 * You can delete this if you don't need.
		 * */


	}
}
