package model;

import java.util.ArrayList;

public class FileComparator {
   /* matrix which stores the LCS length */
   private int[][] C;
   /* arraylist which stores the difference indices of each panel */
   private ArrayList<Integer> leftDiffIndex;
   private ArrayList<Integer> rightDiffIndex;
   /* integer array type arraylist
    * which stores start and end indices of blocks */
   private ArrayList<int[]> blocks;

   public FileComparator(ArrayList<String> left, ArrayList<String> right) {
      leftDiffIndex = new ArrayList<Integer>();
      rightDiffIndex = new ArrayList<Integer>();
      blocks = new ArrayList<int[]>();

      LCSLength(left, right);
      compare();
      arrange();
      computeBlocks();
   }

   /**
    * Compute the length of the LCS
    * same algorithm with sequence alignment
    * input: text1(length: m), text2(length: n)
    * for all 1<= i <= m, 1 <= j <= n,
    * calculate LCS of text1[1..i] and text2[1..j]
    * and store it to C[i, j]
    * C[m, n] = LCS value of text1 and text2
    */
   int LCSLength(ArrayList<String> left, ArrayList<String> right) {
      int m = left.size();
      int n = right.size();
      C = new int[m + 1][n + 1];

      /* initialize elements whose row/column index is zero to zero */
      for (int i = 0; i <= m; i++)
         C[i][0] = 0;
      for (int j = 0; j <= n; j++)
         C[0][j] = 0;

      for (int i = 1; i <= m; i++) {
         for (int j = 1; j <= n; j++) {
            if (left.get(i - 1).equals(right.get(j - 1)))
               C[i][j] = C[i - 1][j - 1] + 1;
            else
               C[i][j] = (C[i][j - 1] > C[i - 1][j]) ? C[i][j - 1] : C[i - 1][j];
         }
      }
      return C[m][n];
   }

   /**
    * Compare two panels
    * and store the information to arraylist
    */
   private void compare() {
      /* if two strings have same line, store mutual index */
	  boolean flag =  C.length < C[0].length;
	   
      if( flag ){
    	int[][] B = new int[C[0].length][C.length];
    	for(int a = 0; a < C[0].length; a++){
    		for(int b =0; b < C.length; b++){
    			B[a][b] = C[b][a];
    		}
    	}
    	C=B;
      }
      
      int i = C.length - 1;
      int j = C[0].length - 1;
      
      while (C[i][j] != 0) {
         if (C[i][j] == C[i - 1][j]) {			//decrease row
             leftDiffIndex.add(0, -1);
             i--;
          }

         else if (C[i][j] == C[i][j - 1]) {		//decrease column
            rightDiffIndex.add(0, -1);
            j--;
         }

         else {
            leftDiffIndex.add(0, i);
            rightDiffIndex.add(0, j);
            i--;
            j--;

         }
      }

      while (i != 0) {

         leftDiffIndex.add(0, -1);
         i--;
      }

      while (j != 0) {

         rightDiffIndex.add(0, -1);
         j--;
      }

      leftDiffIndex.add(0, 0);
      rightDiffIndex.add(0, 0);
      
      if(flag){
    	  ArrayList<Integer> temp;
    	  temp = leftDiffIndex;
    	  leftDiffIndex = rightDiffIndex;
    	  rightDiffIndex = temp;
      }
   }

   
   /**
    * After comparing, arrange the strings in each panel
    * ex) add blank lines
    */
   private void arrange() {

      ArrayList<Integer> leftViewIndex = new ArrayList<Integer>();
      ArrayList<Integer> rightViewIndex = new ArrayList<Integer>();

      int L = 0, R = 0;
      int L_Max = leftDiffIndex.size();
      int R_Max = rightDiffIndex.size();
      while (L < L_Max && R < R_Max) {
         /* if same strings, store the index of string */
         if (this.leftDiffIndex.get(L) != -1 && this.rightDiffIndex.get(R) != -1) {
            leftViewIndex.add(L++);
            rightViewIndex.add(R++);
         }
         /* if different strings but in same line,
          * store the index * (-1) */
         else if (this.leftDiffIndex.get(L) == -1 && this.rightDiffIndex.get(R) == -1) {
            leftViewIndex.add(L * (-1));
            L++;
            rightViewIndex.add(R * (-1));
            R++;
         }
         /* if the string is in only one panel,
          * store it in the panel and blank value(0) to another panel */
         else {
            while (this.rightDiffIndex.get(R) == -1) {
               leftViewIndex.add(0);
               rightViewIndex.add(R++);
            }
            while (this.leftDiffIndex.get(L) == -1) {
               leftViewIndex.add(L++);
               rightViewIndex.add(0);
            }
         }
      }
      
      /* When the line numbers are not same,
       * add some blank line until they become same */
      while(L< L_Max){
          leftViewIndex.add(L++);
          rightViewIndex.add(0);
      }
      
      while(R< R_Max){
          leftViewIndex.add(0);
          rightViewIndex.add(R++);
      }
      
      leftDiffIndex = leftViewIndex;
      rightDiffIndex = rightViewIndex;

   }

   
   /**
    * Find start and end index of block
    * and store it to blocks arraylist
    */
   private void computeBlocks() {
      int start = 0;
      int end = 0;
      int i = 1;
      int size = leftDiffIndex.size();

      while (i < size) {
         /* if same string */
         while ((i < size && leftDiffIndex.get(i) > 0) && (rightDiffIndex.get(i) > 0)) {
            i++;
         }

         /* if left panel is blank */
         if (i < size && leftDiffIndex.get(i) == 0) {
            start = i++;
            while (i < size && leftDiffIndex.get(i) == 0) {
               i++;
            }
            end = i - 1;

            blocks.add(new int[] { start, end });
         }
         /* if right panel is blank */
         else if (i < size && rightDiffIndex.get(i) == 0) {
            start = i++;
            while (i < size && rightDiffIndex.get(i) == 0) {
               i++;
            }
            end = i - 1;

            blocks.add(new int[] { start, end });
         }
         /* if different string but in same line */
         else if (i < size && leftDiffIndex.get(i) < 0 && rightDiffIndex.get(i) < 0) {
            start = i++;
            while (i < size && leftDiffIndex.get(i) < 0 && rightDiffIndex.get(i) < 0) {
               i++;
            }
            end = i - 1;

            blocks.add(new int[] { start, end });
         }
      }
   }

   /**
    * Make all elements in leftDiffIndex to negative integer and return it
    * @return diffLeft
    */
   public ArrayList<Integer> getDiffLeft() {
      for (int i = 1; i < leftDiffIndex.size(); i++) {
         int num = leftDiffIndex.get(i);
         if (num > 0) {
            leftDiffIndex.set(i, num*(-1));
         }
      }      
      return this.leftDiffIndex;
   }

   /**
    * Make all elements in rightDiffIndex to positive integer and return it
    * @return diffRight
    */
   public ArrayList<Integer> getDiffRight() {
      for (int i = 1; i < rightDiffIndex.size(); i++) {
         int num = rightDiffIndex.get(i);
         if (num < 0) {
            rightDiffIndex.set(i, num * (-1));
         }
      }
      
      return this.rightDiffIndex;
   }

   public ArrayList<int[]> getBlocks() {
      return this.blocks;
   }
}