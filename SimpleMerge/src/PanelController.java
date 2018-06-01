import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class PanelController {
	private PanelInfo panelInfo;

	
	public PanelController() {
		panelInfo = new PanelInfo();
	}
		
	public boolean load(String filePath) {
		return panelInfo.load(filePath);
	}
	
	public boolean save() {
		return panelInfo.save();
	}
	
	public boolean saveAs(String newFilePath) {
		return panelInfo.saveAs(newFilePath);
	}
	
	public void closeFile() {
		panelInfo.closeFile();
	}
	
	
	/**
	 * Checks if a file is open in the panel in order to save it before opening
	 * another file
	 * @return true if file is open, else return false
	 */
	public boolean fileIsOpen() {
		return panelInfo.fileIsOpen();
	}

	
	/* Getter & Setter */
	public String getFileName() {
		return panelInfo.getFileName();
	}

	public String getFilePath() {
		return panelInfo.getFilePath();
	}
	
	/**
	 * @return current mode of text editor
	 */
	public Mode getMode() {
		return this.panelInfo.getMode();
	}
	
	public void setMode(Mode mode) {
		this.panelInfo.setMode(mode);
	}
	
	public String getOriginalFileContent() {
		return panelInfo.getOriginalFileContent();
	}
	
	public void setFileContent(String fileContent) {
		panelInfo.setFileContentBuffer(fileContent);
	}
	
	/**
	 * @return true is the file has been updated
	 */
	public boolean isUpdated() {
		return panelInfo.isUpdated();
	}

	public void setUpdated(boolean flag) {
		panelInfo.setUpdated(flag);
	}
	
	public ArrayList<String> getFileContentList() {
		return panelInfo.getFileContentBufferList();
	}
	
	@SuppressWarnings("unchecked")
	public void setFileContentList(ArrayList<String> fromMerge) {
		panelInfo.setFileContentList(fromMerge);
	}
	
	
	/* Private Functions */

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Test PanelController.");
		Scanner s = new Scanner(System.in);
		Scanner s2 = new Scanner(System.in);

		PanelController pc = new PanelController();

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
				System.out.println(pc.getFileContentList());
				break;
			case 9:
				ArrayList<String> edited = pc.getFileContentList();
				edited.add("Is this added????");
				pc.setFileContentList(edited);
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
