
/**
* TextEditorModel.java
*/

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class TextEditorModel {
	private File file;
	private Mode mode;

	private String originalFileContent;
	private String fileContentBuffer;
	private boolean dirty;

	private ArrayList<String> alignedFileContentBufferList;
	/** for viewing purpose */
	private ArrayList<String> fileContentBufferList;
	/** for saving purpose */
	private ArrayList<Integer> diffIndices;
	private ArrayList<int[]> blocks;
	private int traverseIndex;
	/** index of blocks */

	private FileReader fr;
	private FileWriter fw;
	private BufferedReader br;
	private BufferedWriter bw;

	public TextEditorModel() {
		file = null;
		mode = Mode.VIEW;

		originalFileContent = null;
		fileContentBuffer = null;
		dirty = false;

		fileContentBufferList = null;

		fr = null;
		fw = null;
		br = null;
		bw = null;
	}

	/**
	 * opens the file in the corresponding file path and sets the file of PanelInfo.
	 * 
	 * @return true if success, false if fail
	 */
	public boolean load(String filePath) {
		if (file != null) {
			// PanelView에서 얘가 return false하면, save 먼저 하고 다시 시도하게 해야 함
			if (dirty) {
				System.out.println("The file has been modified. Please save the file before opening another one.");
				return false;
			}

			closeFile();
		}

		try {
			fr = new FileReader(filePath);
			br = new BufferedReader(fr);

			originalFileContent = new String();
			String s = br.readLine();

			if (s != null)
				originalFileContent += s;
			while ((s = br.readLine()) != null) {
				originalFileContent += "\r\n";
				originalFileContent += s;
			}

			file = new File(filePath);

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

		fileContentBuffer = new String(originalFileContent);
		dirty = false;
		return true;
	}

	/**
	 * saves the file
	 * 
	 * @return true if success, false if failure.
	 */
	public boolean save() {
		if (file == null) {
			System.out.println("No file exists.");
			return false;
		}
		String filePath = getFilePath();

		return this.saveAs(filePath);
	}

	/**
	 * save the file w/ different file name
	 * 
	 * @param newFilePath
	 * @return true if success, false if failure.
	 */
	public boolean saveAs(String newFilePath) {
		try {
			file = new File(newFilePath);

			fw = new FileWriter(newFilePath);
			bw = new BufferedWriter(fw);

			bw.write(fileContentBuffer);

			System.out.println("Saved");

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

		dirty = false;
		originalFileContent = new String(fileContentBuffer);

		return true;
	}

	/**
	 * operation when user closes the file
	 */
	public void closeFile() {
		file = null;
		mode = Mode.VIEW;

		originalFileContent = null;
		fileContentBuffer = null;
		dirty = false;

		fileContentBufferList = null;
	}

	public void resetToOriginal() {
		this.fileContentBuffer = new String(originalFileContent);
		this.dirty = false;
	}

	public void fileContentBufferToString() {
		fileContentBuffer = new String();

		fileContentBuffer += fileContentBufferList.get(0);
		for (int i = 1; i < fileContentBufferList.size(); i++) {
			fileContentBuffer += "\r\n";
			fileContentBuffer += fileContentBufferList.get(i);
		}

	}

	/**
	 * Checks if a file is open in the panel in order to save it before opening
	 * another file
	 * 
	 * @return true if file is open, else return false
	 */
	public boolean fileIsOpen() {
		return file != null;
	}

	/* Getter & Setter */
	public String getFilePath() {
		if (file == null)
			return "";
		else
			return file.getPath();
	}

	public String getFileName() {
		if (file == null)
			return "";
		else
			return file.getName();
	}

	/**
	 * @return current mode of text editor
	 */
	public Mode getMode() {
		return this.mode;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
		switch (mode) {
		case VIEW:
			dirty = false;
			break;
		case EDIT:
			break;
		case COMPARE:
			dirty = false;
			break;
		default:
			break;
		}
	}

	public String getOriginalFileContent() {
		return this.originalFileContent;
	}

	public String getFileContentBuffer() {
		return this.fileContentBuffer;
	}

	public void setFileContentBuffer(String fileContent) {
		fileContentBuffer = new String(fileContent);
	}

	public boolean isUpdated() {
		return dirty;
	}

	public void setUpdated(boolean flag) {
		this.dirty = flag;
	}

	//////////////////////////////////////////////////////////////////////////////
	// Merge - Use these methods ! //
	//////////////////////////////////////////////////////////////////////////////

	public void setDiffIndices(ArrayList<Integer> diffIndices) {
		this.diffIndices = diffIndices;
	}

	public void setTraverseIndex(int traverseIndex) {
		this.traverseIndex = traverseIndex;
	}

	public ArrayList<String> getAlignedFileContentBufferList() {
		return alignedFileContentBufferList;
	}

	public ArrayList<String> getFileContentBufferList() {
		System.out.println(fileContentBuffer);
		System.out.println("-------------");
		// does not split well in empty space
		String[] fcArray = fileContentBuffer.split("\\r?\\n", -1);

		this.fileContentBufferList = new ArrayList<String>(Arrays.asList(fcArray));

		System.out.println(fileContentBufferList);
		System.out.println("-------------");
		return this.fileContentBufferList;
	}

	// setBlocks(), setFileContentBufferList(), and
	// setAlignedFileContentBufferList()
	// above methods are no longer needed, as references are copied by getter
	// methods
	public void setBlocks(ArrayList<int[]> blocks) {
		this.blocks = blocks;
	}

	public void setFileContentBufferList(ArrayList<String> fileContentBufferList) {
		this.fileContentBufferList = fileContentBufferList;
	}

	public void setAlignedFileContentBufferList(ArrayList<String> alignedFileContentBufferList) {
		this.alignedFileContentBufferList = alignedFileContentBufferList;
	}

	//////////////////////////////////////////////////////////////////////////////
	// Merge - Use these methods ! //
	//////////////////////////////////////////////////////////////////////////////

	public ArrayList<Integer> getDiffIndices() {
		return diffIndices;
	}

	public int getTraverseIndex() {
		return traverseIndex;
	}

	public ArrayList<int[]> getBlocks() {
		return blocks;
	}

	public int[] getCurrentBlock() {
		return this.blocks.get(traverseIndex);
	}

	/* Private Functions */
	private void exitCompareMode() {
		fileContentBuffer = new String();

		for (int i = 0; i < fileContentBufferList.size(); i++) {
			fileContentBuffer += this.fileContentBufferList.get(i);
			fileContentBuffer += "\r\n";
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Test PanelInfo.java");
	}
}