import java.io.*;

public class PanelInfo {
	private File file;
	private String fileContent;
	private boolean dirty;
	private Mode mode;
	
	public PanelInfo() {
		// TODO
	}
	
	File getFile() {
		return this.file;
	}
	
	void setFile(File file) {
		this.file = file;
	}
	
	String getFileContents() {
		return this.fileContent;
	}
	
	void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}
	
	boolean isUpdated() {
		return dirty;
	}
	
	void setUpdated(boolean flag) {
		this.dirty = flag;
	}
	
	Mode getMode() {
		return this.mode;
	}
	
	void setMode(Mode mode) {
		this.mode = mode;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Test PanelInfo.java");
	}

}
