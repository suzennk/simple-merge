import java.io.*;
import java.util.Scanner;

public class PanelController {
	private PanelInfo panelInfo;

	private FileReader fr = null;
	private FileWriter fw = null;
	private BufferedReader br = null;
	private BufferedWriter bw = null;

	private String fileContent;
	private boolean dirty = false;

	public PanelController() {
		panelInfo = new PanelInfo();

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
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
				}
			if (fr != null)
				try {
					fr.close();
				} catch (IOException e) {
				}
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
				try {
					bw.close();
				} catch (IOException e) {
				}
			if (fw != null)
				try {
					fw.close();
				} catch (IOException e) {
				}
		}
		this.setUpdated(false);
		return true;
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

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
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
	private File getFile() {
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
			System.out.println("5. Exit");

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
				iterate = false;
				break;
			default:
				System.out.println("wrong choice");
				continue;
			}

			System.out.println("Exit Program.");

		}

	}

}
