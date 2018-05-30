import java.io.*;

public class PanelInfo {
	private File file;
	private String originalFileContent;
	private Mode mode;
	
	public PanelInfo() {
		// TODO
	}
	
	public File getFile() {
		return this.file;
	}
	
	public void setFile(File file) {
		this.file = file;
	}
	
	public String getOriginalFileContent() {
		return this.originalFileContent;
	}
	
	public void setOriginalFileContent(String fileContent) {
		this.originalFileContent = fileContent;
	}
	
	public Mode getMode() {
		return this.mode;
	}
	
	public void setMode(Mode mode) {
		this.mode = mode;
	}
	
	public String getFilePath() {
		return file.toString();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Test PanelInfo.java");
	}

}
