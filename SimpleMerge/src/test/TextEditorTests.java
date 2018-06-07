/**
 * 
 */
package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import simplemerge.TextEditorModel;

/**
 * @author Susan
 *
 */
class TextEditorTests {

	TextEditorModel tem;
	static String inputFile;
	static String content;
	static String inputFile_no;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		inputFile = new String("input.txt");
		inputFile_no = new String("input_no.txt");
		
		// Generate a valid input file
		File input = new File(inputFile);
		FileWriter fw= new FileWriter(inputFile);
		BufferedWriter bw = new BufferedWriter(fw);
		content = new String();
		int i = 0;
		content += String.valueOf(i++);
		while (i++ < 100) {
			content += "\r\n";
			content += String.valueOf(i);
		}
		bw.write(content);
		
		if (bw != null)
			bw.close();
		if (fw != null)
			fw.close();
		
		// Generate an invalid input file by creating a file, and then deleting it.
		File input_no = new File(inputFile_no);
		input_no.delete();
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
		
		assertEquals(true, tem.load("TestInputs/input.txt"));
		assertEquals(false, tem.isUpdated());
		assertEquals(readFile("TestInputs/input.txt"), tem.getFileContentBuffer());
		assertEquals(tem.getFileContentBuffer(), tem.getFileContentBuffer());
		
		assertEquals(true, tem.load("TestInputs/number.txt"));
		assertEquals(false, tem.isUpdated());
		assertEquals(readFile("TestInputs/number.txt"), tem.getFileContentBuffer());
		assertEquals(tem.getFileContentBuffer(), tem.getFileContentBuffer());
		
		assertEquals(true, tem.load("TestInputs/test.txt"));
		assertEquals(false, tem.isUpdated());
		assertEquals(readFile("TestInputs/test.txt"), tem.getFileContentBuffer());
		assertEquals(tem.getFileContentBuffer(), tem.getFileContentBuffer());
	}
	
	@Test
	void failingLoadTest() {
		
	}
	
	@Test 
	void saveTest(){
		
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
