import java.io.*;

public class PanelController {
	private PanelInfo panelInfo;
	private String fileContent;		// 일종의 Buffer 같이 작
	
	public PanelController() {
		
	}
	
	/** file을 열고 PanelInfo의 file을 설정. **/
	public void load(String filePath) {
		// TODO error handling
		panelInfo.setFile(new File(filePath));
	}
	

	/** fileContent의 내용을 저장 **/
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
	
	/** panelInfo의 파일 내용 복사 **/
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
	
	/** panelInfo의 변경사항을 PanelVew에게 알린다. **/
	private void updatePanelView() {
		// TODO
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Test PanelController.");
	}

}
