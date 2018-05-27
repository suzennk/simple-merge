import java.io.*;

public class PanelInfo {
	private File file;
	private String fileContent;
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
	
	public String getFileContents() {
		return this.fileContent;
	}
	
	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
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
