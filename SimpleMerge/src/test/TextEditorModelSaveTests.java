package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import simplemerge.Mode;
import simplemerge.TextEditorModel;

class TextEditorModelSaveTests {

	TextEditorModel tem;
//	static String inputFile;
//	static String content;
//	static String inputFile_no;
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
	}
	
	@BeforeEach
	void setUp() throws Exception {
		tem = new TextEditorModel();
		tem.load(filePath);
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
	void fileExtensionTest() {
	}

}
