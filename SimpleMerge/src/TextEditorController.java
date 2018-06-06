/**
* TextEditorController.java
*/


import java.util.ArrayList;
import java.util.Scanner;

public class TextEditorController {
	private TextEditorModel tem;
	
	public TextEditorController() {
		tem = new TextEditorModel();		
	}
		
	public boolean load(String filePath) {
		return tem.load(filePath);
	}
	
	public boolean save() {
		return tem.save();
	}
	
	public boolean saveAs(String newFilePath) {
		return tem.saveAs(newFilePath);
	}
	
	public void closeFile() {
		tem.closeFile();
	}
	
	public void resetToOriginal() {
		tem.resetToOriginal();
	}
	
	public void fileContentBufferToString() {
		tem.fileContentBufferToString();
	}
	
	/**
	 * Checks if a file is open in the panel in order to save it before opening
	 * another file
	 * @return true if file is open, else return false
	 */
	public boolean fileIsOpen() {
		return tem.fileIsOpen();
	}

	
	/* Getter & Setter */
	public String getFileName() {
		return tem.getFileName();
	}

	public String getFilePath() {
		return tem.getFilePath();
	}
	
	/**
	 * @return current mode of text editor
	 */
	public Mode getMode() {
		return this.tem.getMode();
	}
	
	public void setMode(Mode mode) {
		this.tem.setMode(mode);
	}
	
	/**
	 * @return the original file content, which is the content before editing
	 */
	public String getOriginalFileContent() {
		return tem.getOriginalFileContent();
	}
	
	/**
	 * @return current file content
	 */
	public String getFileContentBuffer() {
		return tem.getFileContentBuffer();
	}
	
	
	public void setFileContentBuffer(String fileContent) {
		tem.setFileContentBuffer(fileContent);
	}
	

	/**
	 * @return true is the file has been updated
	 */
	public boolean isUpdated() {
		return tem.isUpdated();
	}

	public void setUpdated(boolean flag) {
		tem.setUpdated(flag);
	}
	
	public ArrayList<String> getFileContentBufferList() {
		return tem.getFileContentBufferList();
	}
	
	public ArrayList<String> getAlignedFileContentBufferList() {
		return tem.getAlignedFileContentBufferList();
	}
	
	@SuppressWarnings("unchecked")
	public void setFileContentBufferList(ArrayList<String> fromMerge) {
		tem.setFileContentBufferList(fromMerge);
	}
	
	public void setAlignedFileContentBufferList(ArrayList<String> fromMerge) {
		tem.setAlignedFileContentBufferList(fromMerge);
	}
	public ArrayList<int[]> getBlocks() {
		return tem.getBlocks();
	}
	
	public TextEditorModel getTEM() {
		return this.tem;
	}
	
	public ArrayList<Integer> getDiffIndices() {
		return tem.getDiffIndices();
	}
	
	public int getTraverseIndex() {
		return tem.getTraverseIndex();
	}
	
	public int[] getCurrentBlock() {
		return tem.getCurrentBlock();
	}

}
