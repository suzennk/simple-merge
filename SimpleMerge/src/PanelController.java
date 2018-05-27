import java.io.*;

public class PanelController {
	private PanelInfo panelInfo;
	private String fileContent;	
	
	public PanelController() {
		
	}
	
	/** opens the file of the corresponding file path 
	 * and sets the file of PanelInfo. **/
	public void load(String filePath) {
		// TODO error handling
		panelInfo.setFile(new File(filePath));
	}
	

	/** saves the file content **/
	public void save(/* TODO */) {
		// TODO
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
	
	/** panelInfo의 변경사항을 PanelView에게 알린다. **/
	private void updatePanelView() {
		// TODO
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Test PanelController.");
	}

}
