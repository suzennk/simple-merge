package test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import simplemerge.Mode;
import simplemerge.TextEditorModel;

public class TextEditorModelTests {

	TextEditorModel tem;
	static String inputFile;
	static String content;
	static String inputFile_no;
	static String filePath;
	static String fileName;
	static String filePath_NO;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		filePath = new String("number.txt");
		fileName = new String("number.txt");

		filePath_NO = new String("non-existing-file-path");

		tem = new TextEditorModel();
		tem.load(filePath);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void loadTest() throws IOException {
		
		assertEquals(true, tem.load(filePath));
		assertEquals(fileName, tem.getFile().getName());
		assertEquals(false, tem.isUpdated());
		assertEquals(readFile(filePath), tem.getFileContentBuffer());
		assertEquals(tem.getFileContentBuffer(), tem.getOriginalFileContent());

	}
	
	@Test
	public void failingLoadTest() {
		assertEquals(false, tem.load(filePath_NO));
		assertEquals(null, tem.getFile());
		assertEquals(false, tem.isUpdated());
		assertEquals(tem.getFileContentBuffer(), tem.getOriginalFileContent());
	}
	
	@Test
	public void saveTest() {
		// when no file is loaded, saving should fail.
		tem.closeFile();
		assertEquals(false, tem.save());
		assertEquals(tem.getFileContentBuffer(), tem.getOriginalFileContent());
		
		
		// when a file is loaded, successfully save.
		tem.load(filePath);
		assertEquals(true, tem.save());
		assertEquals(tem.getFileContentBuffer(), tem.getOriginalFileContent());
		
	}
	
	@Test
	public void saveAsTest() {
		tem.load(filePath);
		assertEquals(true, tem.saveAs("saveAs1.txt"));
		assertEquals("saveAs1.txt", tem.getFileName());
		assertEquals(tem.getFileContentBuffer(), tem.getOriginalFileContent());
		
		assertEquals(true, tem.saveAs("saveAs2"));
		assertEquals("saveAs2.txt", tem.getFileName());
		assertEquals(tem.getFileContentBuffer(), tem.getOriginalFileContent());
	}

	@Test
	public void closeFileTest() {
		tem.closeFile();
		assertEquals(null, tem.getFile());
		assertEquals(Mode.VIEW, tem.getMode());
		assertEquals(null, tem.getOriginalFileContent());
		assertEquals(null, tem.getFileContentBuffer());
		assertEquals(false, tem.isUpdated());
		assertEquals(null, tem.getFileContentBufferList());
	}
	
	@Test
	public void resetToOriginal() {
		tem.resetToOriginal();
		assertEquals(tem.getFileContentBuffer(), tem.getOriginalFileContent());
	}

	@Test
	public void getFilePathTest() {
		assertEquals(filePath, tem.getFilePath());
		
		tem.closeFile();
		assertEquals("", tem.getFilePath());
	}
	
	@Test 
	public void getFileNameTest() {
		assertEquals(fileName, tem.getFileName());
		
		tem.closeFile();
		assertEquals("", tem.getFileName());
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
