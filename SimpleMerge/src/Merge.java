import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/*패널간의 메소드를 관리하는 컨트롤러(interpanel controller),
 * compare, traverse, merge 메소드 포함 */
public class Merge {

   private TextEditorModel leftPanel;
   private TextEditorModel rightPanel;
   private ArrayList<String> leftFileContents;
   private ArrayList<String> rightFileContents;
   /**
    * 각 Panel의 difference index를 저장하는 배열
    * index는 0이 아닌 1부터 시작!
    * 같은 내용을 가진 줄에는 서로의 index, 다른 내용을 가진 줄에는 (-1)*index,
    * 공백줄은 0을 저장
    */
   private ArrayList<Integer> leftDiffIndex;
   private ArrayList<Integer> rightDiffIndex;
   private ArrayList<int[]> blocks;
   
   /*
    * 현재 커서의 위치 int[2] => [begin index, end index]
    */
   private int[] traverseCursor;
   /*
    * flagPrevious 는  더이상 앞으로 이동할 곳이없다를 표시하는 flag. true이면 아직 있다. false면 더이상없다.
    * flagNext 마찬가지.
    * MainView 는 이 Flag 들을 이용해 traverse 버튼을 비활성화/활성화 시킨다.
    */
   private boolean flagPrevious;
   private boolean flagNext;
   /*
    * diffFirstIndex는  가장 처음 block(SRS glossory)의 begin 인자.
    * diffLastIndex는 가장 마지막 block(SRS glossory)의 end 인자.
    */
   private int diffFirstIndex;
   private int diffLastIndex;
   
   Merge() {
      System.out.println("No input panels");
   }

   Merge(TextEditorModel leftPanel, TextEditorModel rightPanel) {
      this.leftPanel = leftPanel;
      this.rightPanel = rightPanel;
      this.traverseCursor = new int[] {0, 0};

      /* panel contents 받아와서 parsing 후 arraylist에 저장 */
      this.leftFileContents = new ArrayList<String>(leftPanel.getFileContentList());
      this.leftFileContents.add(0, "");

      this.rightFileContents = new ArrayList<String>(rightPanel.getFileContentList());
      this.rightFileContents.add(0, "");
      
      /* FileComparator를 이용하여 compare 후 difference를 저장한 index 돌려받기 */
      FileComparator fc = new FileComparator(leftFileContents, rightFileContents);
      this.leftDiffIndex = fc.getDiffLeft();
      this.rightDiffIndex = fc.getDiffRight();
      this.blocks = fc.getBlocks();

      setDiffFirstIndex();
      setDiffLastIndex();
      flagPrevious = setFlagPrevious();
      flagNext = setFlagNext();
      
   }
/*
 * diffFirstIndex를 계산해준다.
 */
   private void setDiffFirstIndex(){
      for(int i = 1; i < leftDiffIndex.size(); i++){
         if(leftDiffIndex.get(i) <= 0 || rightDiffIndex.get(i) <= 0){
            diffFirstIndex = i;
            break;
         }
      }
   }
   
   /*
    * diffLastIndex를 계산해준다.
    */
   private void setDiffLastIndex(){
      for(int i = leftDiffIndex.size()-1; i > 0; i--){
         if(leftDiffIndex.get(i) <= 0 || rightDiffIndex.get(i) <= 0){
            diffLastIndex = i;
            break;
         }
      }
   }
   
   /*
    * flagPrevious 설정. 
    */
   private boolean setFlagPrevious(){
      if(traverseCursor[0] != diffFirstIndex)
         return true;
      
      else
         return false;
   }
   
   /*
    * flagNext 설정
    */
   private boolean setFlagNext(){
      if(traverseCursor[1] != diffLastIndex)
         return true;
      
      else
         return false;
   }
   
   /*
    * 이 메소드를 실행하면 previous block 을 가리킨다.
    * MainView는 getTraverseCursor을 통해 현재 가리키는 [begin index, end index] 를 얻는다.
    * 알고리즘은 바로 아래의 메소드를 참고 바람.
    */
   void traversePrevious() {
     int i = traverseCursor[0] - 1;
     
     while(leftDiffIndex.get(i) > 0 && rightDiffIndex.get(i) > 0)
        i--;
     
      traverseCursor[1] = i;

      if(leftDiffIndex.get(i) == 0 || rightDiffIndex.get(i) == 0){
         while(i >= diffFirstIndex && (leftDiffIndex.get(i) == 0 || rightDiffIndex.get(i) == 0))
            i--;
      }
      
      else{
         while(i >= diffFirstIndex && (leftDiffIndex.get(i) < 0 && rightDiffIndex.get(i) < 0))
            i--;
      }
      
      traverseCursor[0] = ++i;
   }   

   /*
    * 알고리즘 : 
    * [0,0]은 그 어떤 traverse 버튼도 누르기 전의 초기 상태이다.
    * 이 상태에서 next 버튼을 누른다면
    * 1) leftDiffIndex의 1번 index부터 block을 찾기시작한다.
    * 2) block을 찾았다면 그 block의 begin index를 현재 커서의 begin index에 대입.
    * 3) block의 끝을 찾기 위해 검토를 한다. <이 때, 공백줄vs다른내용인 block case와 다른내용vs다른내용인 block case를 나눴다.>
    * 4) block의 끝을 찾았다면 커서의 end index에 대입.
    * 5) MainView는 getTraverseCursor을 통해 현재 가리키는 [begin index, end index] 를 얻는다.
    * # 위의 previous는 이와 반대로 이루어져있다.
    */
   void traverseNext() {
     int i = traverseCursor[1] + 1;
     
     while(leftDiffIndex.get(i) > 0 && rightDiffIndex.get(i) > 0)
        i++;
     
     traverseCursor[0] = i;
   
     if(leftDiffIndex.get(i) == 0 || rightDiffIndex.get(i) == 0){
        while(i <= diffLastIndex && (leftDiffIndex.get(i) == 0 || rightDiffIndex.get(i) == 0))
           i++;
     }
     
     else{
        while(i <= diffLastIndex && (leftDiffIndex.get(i) < 0 && rightDiffIndex.get(i) < 0))
           i++;
     }
     
     traverseCursor[1] = --i;
   }

   ////////////////////////merge의 함수 CTL, CTR 미완성 ㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠ
   /*
    * 
    * 
    *  
    */
   void copyToLeft() {
      for(int i = traverseCursor[0]; i< traverseCursor[1]; i++){
         
      }
   }

   /**
    * clear all the string and copy from left to right
    */
   void copyToRight() {
      int i = traverseCursor[0];
        if(leftDiffIndex.get(i) == 0 || rightDiffIndex.get(i) == 0){
           while(i <=traverseCursor[1] && (leftDiffIndex.get(i) == 0 || rightDiffIndex.get(i) == 0))
              i++;
        }
        
        else{
           while(i <= traverseCursor[1] && (leftDiffIndex.get(i) < 0 && rightDiffIndex.get(i) < 0))
              i++;
        }
   }
   /////////////////////////////////////

   public ArrayList<String> getLeftFileContents() {
      return this.leftFileContents;
   }

   public ArrayList<String> getRightFileContents() {
      return this.rightFileContents;
   }

   public ArrayList<Integer> getLeftDiffIndex() {
      return this.leftDiffIndex;
   }

   public ArrayList<Integer> getRightDiffIndex() {
      return this.leftDiffIndex;
   }
   
   public boolean getFlagPrevious(){
      return this.flagPrevious;
   }
   
   public boolean getFlagNext(){
      return this.flagNext;
   }
   
   public int[] getTraverseCursor(){
      return this.traverseCursor;
   }
   
   /**
    * Used for the test of main class of this class.
    * You can delete this method.
    * It prints the index which includes blanks.
    */
   void printArranged() {
      System.out.println("Left Panel=========");
      for (int i = 1; i < this.leftDiffIndex.size(); i++)
         System.out.println("["+i+"] "+this.leftDiffIndex.get(i));

      System.out.println("\nRight Panel=========");
      for (int i = 1; i < this.rightDiffIndex.size(); i++)
         System.out.println("["+i+"] "+this.rightDiffIndex.get(i));
   }
   
   /**
    * Used for the test of main class of this class.
    * You can delete this method.
    * It prints the index which does not include blanks.
    */
   void printAll() {
      System.out.println("Left Panel=========");
      for (int i = 1; i < this.leftFileContents.size(); i++)
         System.out.println(this.leftFileContents.get(i));

      System.out.println("\nRight Panel=========");
      for (int i = 1; i < this.rightFileContents.size(); i++)
         System.out.println(this.rightFileContents.get(i));
   }

   public static void main(String[] args) {
      /*
       * This main is a test for this class. You can delete this if you don't need.
       */
      Scanner s = new Scanner(System.in);
      int[] index = new int[2];
      TextEditorModel left = new TextEditorModel();
      TextEditorModel right = new TextEditorModel();

      left.setFileContent("same part1\r\n" + 
            "same part2\r\n" + 
            "different but same line.1\r\n" + 
            "different but same line.2\r\n" + 
            "same part3\r\n" + 
            "same part4\r\n" + 
            "same part5\r\n" + 
            "same part6\r\n" + 
            "different part-a\r\n" + 
            "different part-a\r\n" + 
            "different part-a\r\n" + 
            "same part7\r\n" + 
            "same part8");

      right.setFileContent("same part1\r\n" + 
            "same part2\r\n" + 
            "different but same line.3\r\n" + 
            "different but same line.4\r\n" + 
            "different part-b\r\n" + 
            "different part-b\r\n" + 
            "different part-b\r\n" + 
            "different part-b\r\n" + 
            "same part3\r\n" + 
            "same part4\r\n" + 
            "same part5\r\n" + 
            "different part-b\r\n" + 
            "different part-b\r\n" + 
            "same part6\r\n" + 
            "same part7\r\n" + 
            "same part8");

      Merge mc = new Merge(left, right);

      boolean iterate = true;
      while (iterate) {
         System.out.println("\n1. print");
         System.out.println("2. print arranged");
         System.out.println("3. traverse previous");
         System.out.println("4. traverse next");
         System.out.println("5. copy to left");
         System.out.println("6. copy to right");
         System.out.println("7. exit");
         System.out.print("Select menu: ");
         int menu = s.nextInt();
         switch (menu) {

         case 1:
            mc.printAll();
            break;
         case 2:
            mc.printArranged();
            break;
         case 3:
            mc.traversePrevious();
            index = mc.getTraverseCursor();
            if (index[0] != -1 && index[1] != -1) {
               System.out.println("Traverse line " + index[0] + " to " + index[1]);
               for (int i = index[0]; i <= index[1]; i++)
                  System.out.println(mc.leftFileContents.get(i));
            }
            break;
         case 4:
            mc.traverseNext();
            index = mc.getTraverseCursor();
            if (index[0] != -1 && index[1] != -1) {
               System.out.println("Traverse line " + index[0] + " to " + index[1]);
               for (int i = index[0]; i <= index[1]; i++)
                  System.out.println(mc.leftFileContents.get(i));
            }
            break;
         case 5:
            mc.copyToLeft();
            break;
         case 6:
            mc.copyToRight();
            break;
         case 7:
            iterate = false;
            break;
         default:
            System.out.println("wrong choice");
            continue;
         }

      }
      System.out.println("Exit Program.");
      s.close();
   }

}