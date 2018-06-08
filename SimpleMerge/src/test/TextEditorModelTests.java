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

import model.Mode;
import model.TextEditorModel;

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
		// loading a file should return true
		assertEquals(true, tem.load(filePath));
		
		// file name should be set
		assertEquals(fileName, tem.getFile().getName());
		
		// dirty flag must be false right after load
		assertEquals(false, tem.isUpdated());
		
		// file content buffer must be set equal to original file content
		assertEquals(tem.getFileContentBuffer(), tem.getOriginalFileContent());

	}
	
	@Test
	public void failingLoadTest() {
		// loading a non-existing file should return false
		assertEquals(false, tem.load(filePath_NO));
		
		// tem.file should be null
		assertEquals(null, tem.getFile());
		
		// dirty flag must be false b/c no file is open
		assertEquals(false, tem.isUpdated());
		
		// file content buffer should be null
		assertEquals(null, tem.getFileContentBuffer());

		// file content buffer must be set equal to original file content
		assertEquals(tem.getFileContentBuffer(), tem.getOriginalFileContent());
	}
	
	@Test
	public void saveTest() {
		// when no file is loaded, saving should fail.
		tem.closeFile();
		assertEquals(false, tem.save());		
		
		// when a file is loaded, successfully save.
		tem.load(filePath);
		assertEquals(true, tem.save());
		assertEquals(tem.getFileContentBuffer(), tem.getOriginalFileContent());
		
	}
	
	@Test
	public void saveAsTest() {
		tem.load(filePath);
		// save as "saveAs1.txt" should return true
		assertEquals(true, tem.saveAs("saveAs1.txt"));
		
		// file should be set to new file
		assertEquals("saveAs1.txt", tem.getFileName());

		// file content buffer must be set equal to original file content
		assertEquals(tem.getFileContentBuffer(), tem.getOriginalFileContent());
		
		// save as "saveAs" should return true
		assertEquals(true, tem.saveAs("saveAs2"));
		
		// file should be set to new file 
		// file extension is added automatically
		assertEquals("saveAs2.txt", tem.getFileName());

		// file content buffer must be set equal to original file content
		assertEquals(tem.getFileContentBuffer(), tem.getOriginalFileContent());
	}

	@Test
	public void closeFileTest() {
		// close the file
		tem.closeFile();
		
		// mode should be set to View Mode
		assertEquals(Mode.VIEW, tem.getMode());
		
		// everything else should be null
		assertEquals(null, tem.getFile());
		assertEquals(null, tem.getOriginalFileContent());
		assertEquals(null, tem.getFileContentBuffer());
		assertEquals(false, tem.isUpdated());
		assertEquals(null, tem.getFileContentBufferList());
	}
	
	@Test
	public void resetToOriginal() {
		// reset to original
		tem.resetToOriginal();

		// file content buffer must be set equal to original file content
		assertEquals(tem.getFileContentBuffer(), tem.getOriginalFileContent());
	}

	@Test
	public void getFilePathTest() {
		// getFilePath() should return the file path
		assertEquals(filePath, tem.getFilePath());
		
		tem.closeFile();
		// if no file is open, getFilePath() should return an empty string
		assertEquals("", tem.getFilePath());
	}
	
	@Test 
	public void getFileNameTest() {
		// getFileName() should return the file name
		assertEquals(fileName, tem.getFileName());
		
		tem.closeFile();
		// if no file is opne, getFileName() should return an empty string
		assertEquals("", tem.getFileName());
	}


}
