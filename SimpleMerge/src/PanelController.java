import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class PanelController {
	private PanelInfo panelInfo;

	private FileReader fr = null;
	private FileWriter fw = null;
	private BufferedReader br = null;
	private BufferedWriter bw = null;

	private String fileContentBuffer;
	private ArrayList <String> fileContentList;
	private int currentIndex;		// start index for find() function
	private boolean dirty;
	
	
	public PanelController() {
		panelInfo = new PanelInfo();
		currentIndex = 0;
		dirty = false;
	}

	/**
	 * opens the file in the corresponding file path and sets the file of PanelInfo.
	 * @return true if success, false if fail
	 */
	public boolean load(String filePath) {
		// TODO error handling
		if (this.getFile() != null) {
			// TODO PanelView에서 얘가 return false하면, save 먼저 하게 해야함 ,,,,
			if (dirty) {
				System.out.println("The file has been modified. Please save the file before opening another one.");
				return false;
			}

			// close(?) the file
			panelInfo.setFile(null);
		}

		try {
			fr = new FileReader(filePath);
			br = new BufferedReader(fr);

			fileContentBuffer = new String();
			String s = null;

			while ((s = br.readLine()) != null) {
				fileContentBuffer += s;
				fileContentBuffer += "\r\n";
			}

			panelInfo.setOriginalFileContent(fileContentBuffer);
			this.setFile(new File(filePath));
			System.out.println(panelInfo.getFilePath());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (br != null)	try { br.close(); } catch (IOException e) {}
			if (fr != null)	try { fr.close(); } catch (IOException e) {}
		}

		System.out.println(this.fileContentBuffer);
		this.setUpdated(false);
		return true;
	}

	/**
	 * saves the file
	 * @return true if success, false if failure.
	 */
	public boolean save() {
		if (fileIsOpen() == false) {
			System.out.println("No file exists.");
			return false;
		}
		String filePath = panelInfo.getFilePath();
		return this.saveAs(filePath);
	}

	/**
	 * save the file  w/ different file name
	 * @param newFilePath
	 * @return true if success, false if failure.
	 */
	public boolean saveAs(String newFilePath) {
		try {
			panelInfo.setFile(new File(newFilePath));

			fw = new FileWriter(newFilePath);
			bw = new BufferedWriter(fw);

			bw.write(fileContentBuffer);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed to save file.");
			return false;
		} finally {
			if (bw != null)
				try { bw.close(); } catch (IOException e) {}
			if (fw != null)
				try { fw.close(); } catch (IOException e) {}
		}
		panelInfo.setOriginalFileContent(fileContentBuffer);
		this.setUpdated(false);
		return true;
	}
	
	
	// PanelView에서 find할 때 setCurrent index 해줘야 함.
	/**
	 * Find findPattern in the file
	 * @param findPattern
	 * @return start index of found location
	 */
	public int find(String findPattern) {
		int startIndex = this.fileContentBuffer.toLowerCase().indexOf(findPattern.toLowerCase(), this.currentIndex);
		
		if (startIndex == -1 && this.currentIndex != 0)
			startIndex = this.fileContentBuffer.toLowerCase().indexOf(findPattern.toLowerCase(), 0);
		
		this.currentIndex = startIndex + 1;
				
		if (startIndex == -1)
			System.out.println("No match found");
		else if (startIndex == this.fileContentBuffer.toLowerCase().lastIndexOf(findPattern.toLowerCase())) {
			System.out.println("Found Index : " + startIndex);
			System.out.println("This is the last match.");
			this.currentIndex = 0;
		} else
			System.out.println("Found Index : " + startIndex);
		
		return startIndex;
	}

	/**
	 * Replace substring of length at start index to replacePattern
	 * @param startIndex
	 * @param length
	 * @param replacePattern
	 */
	public void replace(int startIndex, int length, String replacePattern) {
		StringBuilder replacedFC = new StringBuilder(this.fileContentBuffer);
		
		replacedFC.delete(startIndex, startIndex+length);
		replacedFC.insert(startIndex, replacePattern);
		fileContentBuffer = replacedFC.toString();
		
		return;
	}
	
	/**
	 * Replace all matches of findPattern to replacePattern
	 * @param findPattern
	 * @param replacePattern
	 */
	public void replaceAll(String findPattern, String replacePattern) {
		this.fileContentBuffer = this.fileContentBuffer.replaceAll(findPattern, replacePattern);
		
		return;
	}
	
	/**
	 * Checks if a file is open in the panel in order to save it before opening
	 * another file
	 * @return true if file is open, else return false
	 */
	public boolean fileIsOpen() {
		return panelInfo.getFile() != null;
	}

	
	/* Getter & Setter */
	/**
	 * @return currently open file, null if no file open
	 */
	public File getFile() {
		return this.panelInfo.getFile();
	}
	
	/**
	 * Set the file of panelInfo
	 * @param file
	 */
	public void setFile(File file) {
		// TODO error handling
		this.panelInfo.setFile(file);
	}

	public String getFileContent() {
		return this.fileContentBuffer;
	}
	
	public void setFileContent(String fileContent) {
		this.fileContentBuffer = fileContent;
	}
	
	public String getOriginalFileContent() {
		return panelInfo.getOriginalFileContent();
	}
	
	public ArrayList<String> getFileContentList() {
		this.toArrayList();
		return this.fileContentList;
	}
	
	@SuppressWarnings("unchecked")
	public void setFileContentList(ArrayList<String> fromMerge) {
		this.fileContentList = (ArrayList<String>)fromMerge.clone();
		this.makeListToString();
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

	/**
	 * @return true is the file has been updated
	 */
	public boolean isUpdated() {
		return dirty;
	}

	public void setUpdated(boolean flag) {
		this.dirty = flag;
	}
	
	public int getCurrentIndex() {
		return currentIndex;
	}

	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}

	/* Private Functions */
	/**
	 * Make String(fileContent) to ArrayList<String>(fileContentList)
	 */
	private void toArrayList() {
		String[] fcArray = fileContentBuffer.split("\r\n");
		this.fileContentList = new ArrayList<String>(Arrays.asList(fcArray));

	}
	
	/**
	 * Make ArrayList<String>(fileContentList) to String(fileContent)
	 */
	private void makeListToString() {
		
		this.fileContentBuffer = new String();
		
		for (int i = 0; i < this.fileContentList.size(); i++) {
			fileContentBuffer += this.fileContentList.get(i);
			fileContentBuffer += "\r\n";
		}
	}
	
	
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
			System.out.println("5. Find");
			System.out.println("6. Find & Replace");
			System.out.println("7. Find & ReplaceAll");
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
				String modifiedString = s2.nextLine();
				pc.fileContentBuffer += modifiedString;
				pc.setUpdated(true);
				break;
			case 5:
				System.out.println("Enter String to find : ");
				String pattern = s2.nextLine();
				pc.find(pattern);
				break;
			case 6:
				System.out.println("Enter String to find : ");
				String find = s2.nextLine();
				System.out.println("Enter String to replace : ");
				String replace = s2.nextLine();
				pc.replace(pc.find(find), find.length(), replace);
				break;
			case 7:
				System.out.println("Enter String to find : ");
				String find2 = s2.nextLine();
				System.out.println("Enter String to replace : ");
				String replace2 = s2.nextLine();
				pc.replaceAll(find2, replace2);
				break;
			case 8:
				System.out.println(pc.getFileContentList());
				break;
			case 9:
				ArrayList<String> edited = pc.getFileContentList();
				edited.add("Is this added????");
				pc.setFileContentList(edited);
				System.out.println(pc.fileContentBuffer);
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
