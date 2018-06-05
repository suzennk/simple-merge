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
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Test PanelController.");
		Scanner s = new Scanner(System.in);
		Scanner s2 = new Scanner(System.in);

		TextEditorController pc = new TextEditorController();

		String filePath = new String();

		
		
		boolean iterate = true;
		while (iterate) {
			System.out.println("1. Load File");
			
			System.out.println("2. Save File");
			System.out.println("3. Save File As ...");
			System.out.println("4. Append file content");
			
//			System.out.println("5. Find");
//			System.out.println("6. Find & Replace");
//			System.out.println("7. Find & ReplaceAll");
			
			System.out.println("8. String to List");
			System.out.println("9. List to String");
			System.out.println("10. Exit");

			System.out.print("Select menu: ");
			int menu = s.nextInt();
			switch (menu) {

			case 1:
				System.out.println("Enter File Path : ");
				filePath = s2.nextLine();
				pc.load(filePath);
				break;
			case 2:
				System.out.println("Saving File ...");
				pc.save();
				System.out.println("Saved File!");
				break;
			case 3:
				System.out.print("Enter File Name : ");
				filePath = s2.nextLine();
				pc.saveAs(filePath);
				System.out.println("Saved File!");
				break;
			case 4:
				System.out.println("Enter String to append : ");
				String fc = pc.getOriginalFileContent();
				String modifiedString = s2.nextLine();
				fc += modifiedString;
				pc.setUpdated(true);
				break;
			
//			case 5:
//				System.out.println("Enter String to find : ");
//				String pattern = s2.nextLine();
//				pc.find(pattern);
//				break;
//			case 6:
//				System.out.println("Enter String to find : ");
//				String find = s2.nextLine();
//				System.out.println("Enter String to replace : ");
//				String replace = s2.nextLine();
//				pc.replace(pc.find(find), find.length(), replace);
//				break;
//			case 7:
//				System.out.println("Enter String to find : ");
//				String find2 = s2.nextLine();
//				System.out.println("Enter String to replace : ");
//				String replace2 = s2.nextLine();
//				pc.replaceAll(find2, replace2);
//				break;
			
			case 8:
				System.out.println(pc.getFileContentBufferList());
				break;
			case 9:
				ArrayList<String> edited = pc.getFileContentBufferList();
				edited.add("Is this added????");
				pc.setFileContentBufferList(edited);
				System.out.println(pc.getOriginalFileContent());	// 이부분 테스트 안됨..!! b/c original != buffer
				break;
			case 10:
				iterate = false;
				break;
			default:
				System.out.println("wrong choice");
				continue;
			}

		}
		System.out.println("Exit Program.");
	}

	
}
