/**
 * 
 */
package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import simplemerge.Mode;
import simplemerge.TextEditorModel;

/**
 * @author Susan
 *
 */
class TextEditorModelTests {

	TextEditorModel tem;
	static String inputFile;
	static String content;
	static String inputFile_no;
	static String filePath;
	static String fileName;
	static String filePath_NO;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {

		filePath = new String("data/s1 left.txt");
		fileName = new String("s1 left.txt");

		filePath_NO = new String("non-existing-file-path");
		
//		inputFile = new String("input.txt");
//		inputFile_no = new String("input_no.txt");
//		
//		// Generate a valid input file
//		File input = new File(inputFile);
//		FileWriter fw= new FileWriter(inputFile);
//		BufferedWriter bw = new BufferedWriter(fw);
//		content = new String();
//		int i = 0;
//		content += String.valueOf(i++);
//		while (i++ < 100) {
//			content += "\r\n";
//			content += String.valueOf(i);
//		}
//		bw.write(content);
//		
//		if (bw != null)
//			bw.close();
//		if (fw != null)
//			fw.close();
//		
//		// Generate an invalid input file by creating a file, and then deleting it.
//		File input_no = new File(inputFile_no);
//		input_no.delete();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		tem = new TextEditorModel();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void loadTest() throws IOException {
		
		assertEquals(true, tem.load(filePath));
		assertEquals(fileName, tem.getFile().getName());
		assertEquals(false, tem.isUpdated());
		assertEquals(readFile(filePath), tem.getFileContentBuffer());
		assertEquals(tem.getFileContentBuffer(), tem.getOriginalFileContent());

	}
	
	@Test
	void failingLoadTest() {
		assertEquals(false, tem.load(filePath_NO));
		assertEquals(null, tem.getFile());
		assertEquals(false, tem.isUpdated());
		assertEquals(tem.getFileContentBuffer(), tem.getOriginalFileContent());
	}
	
	@Test
	void closeFileTest() {
		tem.closeFile();
		assertEquals(null, tem.getFile());
		assertEquals(Mode.VIEW, tem.getMode());
		assertEquals(null, tem.getOriginalFileContent());
		assertEquals(null, tem.getFileContentBuffer());
		assertEquals(false, tem.isUpdated());
		assertEquals(null, tem.getFileContentBufferList());
	}
	
	@Test
	void saveTest() {
		// when no file is loaded, saving should fail.
		tem.closeFile();
		assertEquals(false, tem.save());
		
		// when a file is loaded, successfully save.
		tem.load(filePath);
		assertEquals(true, tem.save());
	}
	
	@Test
	void saveAsTest() {
		tem.load(filePath);
		assertEquals(true, tem.saveAs("saveAs1.txt"));
		assertEquals("saveAs1.txt", tem.getFileName());
		assertEquals(tem.getFileContentBuffer(), tem.getOriginalFileContent());
		
		assertEquals(true, tem.saveAs("saveAs2"));
		assertEquals("saveAs2.txt", tem.getFileName());
		assertEquals(tem.getFileContentBuffer(), tem.getOriginalFileContent());
	}


	String readFile(String filepath) throws IOException {
		File f = new File(filepath);
		FileReader fr = new FileReader(filepath);
		BufferedReader br = new BufferedReader(fr);

		String originalFileContent = new String();
		String s = br.readLine();

		if (s != null)
			originalFileContent += s;
		while ((s = br.readLine()) != null) {
			originalFileContent += "\r\n";
			originalFileContent += s;
		}
		return originalFileContent;
	}
}
