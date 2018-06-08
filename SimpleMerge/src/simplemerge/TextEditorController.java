package simplemerge;

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
	
	public void fileContentBufferToString() {
		tem.fileContentBufferToString();
	}
	
	/* Getter & Setter */
	/**
	 * Checks if a file is open in the panel in order to save it before opening
	 * another file
	 * @return true if file is open, else return false
	 */
	public boolean fileIsOpen() {
		return tem.fileIsOpen();
	}

	
	/* Getter & Setter */
	public String getFilePath() {
		return tem.getFilePath();
	}
	
	public String getFileName() {
		return tem.getFileName();
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
	
	public void resetToOriginal() {
		tem.resetToOriginal();
	}
	
	
	public ArrayList<String> getAlignedFileContentBufferList() {
		return tem.getAlignedFileContentBufferList();
	}
	
	public void setAlignedFileContentBufferList(ArrayList<String> fromMerge) {
		tem.setAlignedFileContentBufferList(fromMerge);
	}
	
	public ArrayList<String> getFileContentBufferList() {
		return tem.getFileContentBufferStringToList();
	}
	
	@SuppressWarnings("unchecked")
	public void setFileContentBufferList(ArrayList<String> fromMerge) {
		tem.setFileContentBufferList(fromMerge);
	}
	
	public ArrayList<Integer> getDiffIndices() {
		return tem.getDiffIndices();
	}
	
	public ArrayList<int[]> getBlocks() {
		return tem.getBlocks();
	}
	
	public int[] getCurrentBlock() {
		return tem.getCurrentBlock();
	}
	
	public int getTraverseIndex() {
		return tem.getTraverseIndex();
	}
	
	public TextEditorModel getTEM() {
		return this.tem;
	}

}
