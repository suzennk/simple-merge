import java.io.*;
import java.util.Scanner;

public class PanelController {
	private PanelInfo panelInfo;
	
	private FileReader fr = null;
	private FileWriter fw = null;
	private BufferedReader br = null;
	private BufferedWriter bw = null;
	
	private String fileContent;	
	
	private boolean dirty;
	
	public PanelController() {
		panelInfo = new PanelInfo();
		
	}
	
	/** opens the file of the corresponding file path 
	 * and sets the file of PanelInfo.
	 * @return true if success, false if fail 
	 */
	public boolean load(String filePath) {
		// TODO error handling
		if (panelInfo.getFile() == null) {
			return false;
		}
		try {
			
			fr = new FileReader(filePath);
			br = new BufferedReader(fr);
			
			fileContent = new String();
			String s = null;
			
			while ((s = br.readLine()) != null ) {
				fileContent += s;
				fileContent += "\r\n";
			}
			
			this.setFile(new File(filePath));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (br != null) try {br.close();} catch (IOException e) {}
			if (fr != null) try {fr.close();} catch (IOException e) {}
		}
		return true;
	}
	

	/** saves the file content
	 * @return true if success, false if failure. 
	 */
	public boolean save() {
		try {
			String filePath = panelInfo.getFilePath();
			fw = new FileWriter(filePath);
			bw = new BufferedWriter(fw);
			
			bw.write(fileContent);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (bw != null) try {bw.close();} catch (IOException e) {}
			if (fw != null) try {fw.close();} catch (IOException e) {}
		}
		return true;
	}
	
	/** save the file content w/ different file name
	 * @param newFilePath
	 * @return true if success, false if failure.
	 */
	public boolean saveAs(String newFilePath) {
		try {
			fw = new FileWriter(newFilePath);
			bw = new BufferedWriter(fw);
			
			bw.write(fileContent);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (bw != null) try {bw.close();} catch (IOException e) {}
			if (fw != null) try {fw.close();} catch (IOException e) {}
		}
		return true;
	}
	
	/**
	 * Checks if a file is open in the panel
	 * in order to save it before opening another file
	 */
	public boolean fileIsOpen() {
		return panelInfo.getFile() != null;	
	}
		
	public void setMode(Mode mode) {
		this.panelInfo.setMode(mode);
	}
	
	public Mode getMode() {
		return this.panelInfo.getMode();
	}
	
	private void setFile(File file) {
		// TODO error handling
		this.panelInfo.setFile(file);
	}
	
	private File getFile() {
		return this.panelInfo.getFile();
	}
	
	boolean isUpdated() {
		return dirty;
	}
	
	void setUpdated(boolean flag) {
		this.dirty = flag;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Test PanelController.");
		Scanner s = new Scanner(System.in);
		
		PanelController pc = new PanelController();
		
		System.out.println("Enter File Path : ");
		String filePath = s.nextLine();
		
		pc.load(filePath);
		
		String modifiedString = "abcdefghijklmnop";
		pc.fileContent += modifiedString;
		
		pc.save();
		
	}
	
	
}
