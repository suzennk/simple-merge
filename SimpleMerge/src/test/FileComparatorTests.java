package test;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import simplemerge.FileComparator;

public class FileComparatorTests {
	
	FileComparator fc;
	
    static int[][] C;
    static ArrayList<Integer> leftDiffIndex;
    static ArrayList<Integer> rightDiffIndex;
    static ArrayList<int[]> blocks;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() {
	      ArrayList<String> s1 = new ArrayList<String>();
	      ArrayList<String> s2 = new ArrayList<String>();
	      
	      s1.add("additional part1");
	      s1.add("same part1");
	      s1.add("same part2");
	      s1.add("different but same line.1");
	      s1.add("differenet but same line.2");
	      s1.add("same part3");
	      s1.add("same part4");
	      s1.add("same part5");
	      s1.add("same part6");
	      s1.add("different part-a");
	      s1.add("different part-a");
	      s1.add("different part-a");
	      s1.add("same part7");
	      s1.add("same part8");
	      
	      s2.add("same part1");
	      s2.add("same part2");
	      s2.add("different but same line.3");
	      s2.add("different but same line.4");
	      s2.add("different part-b");
	      s2.add("different part-b");
	      s2.add("different part-b");
	      s2.add("different part-b");
	      s2.add("same part3");
	      s2.add("same part4");
	      s2.add("same part5");
	      s2.add("different part-b");
	      s2.add("different part-b");
	      s2.add("same part6");
	      s2.add("same part7");
	      s2.add("same part8");
	      s2.add("additional part2");
	      
	      fc = new FileComparator(s1,s2);
	}
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void checkDiffIndex() {
		
		ArrayList<Integer> left = new ArrayList<Integer>();
		int[] t1 = {0,-1,-2,-3,-4,-5,0,0,0,0,-6,-7,-8,0,0,-9,-10,-11,-12,-13,-14,0};
		for(int i =0; i<t1.length;i++){
			left.add(t1[i]);
		}
		
		ArrayList<Integer> right = new ArrayList<Integer>();
		int[] t2 = {0,0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,0,0,0,15,16,17};
		for(int i=0; i<t2.length;i++){
			right.add(t2[i]);
		}
		
		assertEquals(fc.getDiffLeft(), left);
		assertEquals(fc.getDiffRight(), right);
	}
	
	@Test
	public void checkBlock() {
		
		ArrayList<int[]> t = new ArrayList<int[]>();
		t.add(new int[]{1,1});
		t.add(new int[]{4,5});
		t.add(new int[]{6,9});
		t.add(new int[]{13,14});
		t.add(new int[]{16,18});
		t.add(new int[]{21,21});
		
		for(int i =0; i<t.size();i++){
			assertEquals(fc.getBlocks().get(i)[0], t.get(i)[0]);
			assertEquals(fc.getBlocks().get(i)[1], t.get(i)[1]);
		}

	}
	
}
