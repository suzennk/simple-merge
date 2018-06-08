package test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.Merge;
import model.TextEditorModel;

public class MergeTests {
	
	Merge m;
	static TextEditorModel left;
	static TextEditorModel right;
	
	@Before
	public void setUp() {
		left = new TextEditorModel();
		right = new TextEditorModel();
		left.setFileContentBuffer("\r\n" + "\r\n" + "\r\n" + "AAA\r\n" + "\r\n" + "\r\n" + "");
		right.setFileContentBuffer("\r\n" + "\r\n" + "\r\n" + "AA\r\n" + "1\r\n" + "\r\n" + "");
		
		m = new Merge(left, right);
	}
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testTraverseNext() {
		
		m.traverseNext();
		assertEquals(1,m.getTraverseCursor());
	}
	
	@Test
	public void testTraversePrevious() {
		m.traverseNext();
		m.traversePrevious();
		assertEquals(0,m.getTraverseCursor());
	}
	
	@Test
	public void testTraversePreviousWhenItsBegin(){
		assertEquals(false, m.getFlagPrevious());
	}
	
	@Test
	public void testTraverseNextWhenItsEnd(){
		m.traverseNext();
		m.traverseNext();
		assertEquals(false, m.getFlagNext());
	}
	
	@Test
	public void testCopyToLeft(){
		m.copyToRight();
		assertEquals("AAA", right.getAlignedFileContentBufferList().get(4));
		assertEquals(2,left.getBlocks().size());
		assertEquals(2,right.getBlocks().size());
		assertEquals(0,m.getTraverseCursor());
	}
	
	@Test
	public void testCopyToRight(){
		m.copyToLeft();
		assertEquals("AA", right.getAlignedFileContentBufferList().get(4));
		assertEquals(2, left.getBlocks().size());
		assertEquals(2,right.getBlocks().size());
		assertEquals(0,m.getTraverseCursor());
	}

}
