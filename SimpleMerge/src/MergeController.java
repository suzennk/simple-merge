public class MergeController {
	private Merge merge;
	
	MergeController (TextEditorModel left, TextEditorModel right) {
		merge = new Merge(left, right);
	}
	
	public boolean getFlagPrevious(){
	    return merge.getFlagPrevious();
	}
	   
	public boolean getFlagNext(){
	    return merge.getFlagNext();
	}
	
	public void setTraverseCursor(int move) {
		
		merge.setTraverseCursor(move);
		
	}
	
	public void traverseNext() {
		merge.traverseNext();
	}
	
	public void traversePrevious() {
		merge.traversePrevious();
	}
	
	public void callCopyToLeft(){
		merge.copyToLeft();
		
	}
	
	public void callCopyToRight(){
		merge.copyToRight();
	}

   

}
