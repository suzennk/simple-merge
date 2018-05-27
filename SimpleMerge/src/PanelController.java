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
	 * and sets the file of PanelInfo. **/
	public void load(String filePath) {
		// TODO error handling
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
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) try {br.close();} catch (IOException e) {}
			if (fr != null) try {fr.close();} catch (IOException e) {}
		}
	}
	

	/** saves the file content **/
	public void save() {
		try {
			String filePath = panelInfo.getFilePath();
			fw = new FileWriter(filePath);
			bw = new BufferedWriter(fw);
			
			bw.write(fileContent);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bw != null) try {bw.close();} catch (IOException e) {}
			if (fw != null) try {fw.close();} catch (IOException e) {}
		}
	}
	
	public void saveAs(/* TODO */) {
		// TODO
	}
	
	public boolean fileIsOpen() {
		/* TODO */
		return true;	
	}
	
	/** panelInfo의 파일 내용을 리턴 **/
	public void fetchFileContent() {
		this.fileContent = new String(panelInfo.getFileContents());
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
