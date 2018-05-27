import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class PanelController {
	private PanelInfo panelInfo;

	private FileReader fr = null;
	private FileWriter fw = null;
	private BufferedReader br = null;
	private BufferedWriter bw = null;

	private String fileContent;
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

			fileContent = new String();
			String s = null;

			while ((s = br.readLine()) != null) {
				fileContent += s;
				fileContent += "\r\n";
			}

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

		System.out.println(this.fileContent);
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

			bw.write(fileContent);
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
		int startIndex = this.fileContent.toLowerCase().indexOf(findPattern.toLowerCase(), this.currentIndex);
		
		if (startIndex == -1 && this.currentIndex != 0)
			startIndex = this.fileContent.toLowerCase().indexOf(findPattern.toLowerCase(), 0);
		
		this.currentIndex = startIndex + 1;
				
		if (startIndex == -1)
			System.out.println("No match found");
		else if (startIndex == this.fileContent.toLowerCase().lastIndexOf(findPattern.toLowerCase())) {
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
		StringBuilder replacedFC = new StringBuilder(this.fileContent);
		
		replacedFC.delete(startIndex, startIndex+length);
		replacedFC.insert(startIndex, replacePattern);
		fileContent = replacedFC.toString();
		
		return;
	}
	
	/**
	 * Replace all matches of findPattern to replacePattern
	 * @param findPattern
	 * @param replacePattern
	 */
	public void replaceAll(String findPattern, String replacePattern) {
		this.fileContent = this.fileContent.replaceAll(findPattern, replacePattern);
		
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

	public void setMode(Mode mode) {
		this.panelInfo.setMode(mode);
	}

	/**
	 * @return current mode of text editor
	 */
	public Mode getMode() {
		return this.panelInfo.getMode();
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

	public String getFileContent() {
		return this.fileContent;
	}
	
	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}
	
	public int getCurrentIndex() {
		return currentIndex;
	}

	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}


	/**
	 * Set the file of panelInfo
	 * @param file
	 */
	private void setFile(File file) {
		// TODO error handling
		this.panelInfo.setFile(file);
	}

	/**
	 * @return currently open file, null if no file open
	 */
	public File getFile() {
		return this.panelInfo.getFile();
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
			System.out.println("8. Exit");

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
				pc.fileContent += modifiedString;
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
