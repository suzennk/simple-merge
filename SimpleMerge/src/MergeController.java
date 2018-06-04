
public class MergeController {
	Merge merge;
	
	MergeController (TextEditorModel left, TextEditorModel right) {
		merge = new Merge(left, right);
	}	

}
