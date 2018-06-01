import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/*鳶確娃税 五社球研 淫軒馬澗 珍闘継君(interpanel controller),
 * compare, traverse, merge 五社球 匂敗 */
public class Merge {

   private TextEditorModel leftPanel;
   private TextEditorModel rightPanel;
   private ArrayList<String> leftFileContents;
   private ArrayList<String> rightFileContents;
   /**
    * 唖 Panel税 difference index研 煽舌馬澗 壕伸
    * index澗 0戚 焼観 1採斗 獣拙!
    * 旭精 鎧遂聖 亜遭 匝拭澗 辞稽税 index, 陥献 鎧遂聖 亜遭 匝拭澗 (-1)*index,
    * 因拷匝精 0聖 煽舌
    */
   private ArrayList<Integer> leftDiffIndex;
   private ArrayList<Integer> rightDiffIndex;
   private ArrayList<int[]> blocks;
   
   /*
    * 薄仙 朕辞税 是帖 int[2] => [begin index, end index]
    */
   private int[] traverseCursor;
   /*
    * flagPrevious 澗  希戚雌 蒋生稽 戚疑拝 員戚蒸陥研 妊獣馬澗 flag. true戚檎 焼送 赤陥. false檎 希戚雌蒸陥.
    * flagNext 原濁亜走.
    * MainView 澗 戚 Flag 級聖 戚遂背 traverse 獄動聖 搾醗失鉢/醗失鉢 獣轍陥.
    */
   private boolean flagPrevious;
   private boolean flagNext;
   /*
    * diffFirstIndex澗  亜舌 坦製 block(SRS glossory)税 begin 昔切.
    * diffLastIndex澗 亜舌 原走厳 block(SRS glossory)税 end 昔切.
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

      /* panel contents 閤焼人辞 parsing 板 arraylist拭 煽舌 */
      this.leftFileContents = new ArrayList<String>(leftPanel.getFileContentList());
      this.leftFileContents.add(0, "");

      this.rightFileContents = new ArrayList<String>(rightPanel.getFileContentList());
      this.rightFileContents.add(0, "");
      
      /* FileComparator研 戚遂馬食 compare 板 difference研 煽舌廃 index 宜形閤奄 */
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
 * diffFirstIndex研 域至背層陥.
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
    * diffLastIndex研 域至背層陥.
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
    * flagPrevious 竺舛. 
    */
   private boolean setFlagPrevious(){
      if(traverseCursor[0] != diffFirstIndex)
         return true;
      
      else
         return false;
   }
   
   /*
    * flagNext 竺舛
    */
   private boolean setFlagNext(){
      if(traverseCursor[1] != diffLastIndex)
         return true;
      
      else
         return false;
   }
   
   /*
    * 戚 五社球研 叔楳馬檎 previous block 聖 亜軒轍陥.
    * MainView澗 getTraverseCursor聖 搭背 薄仙 亜軒徹澗 [begin index, end index] 研 条澗陥.
    * 硝壱軒葬精 郊稽 焼掘税 五社球研 凧壱 郊寓.
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
    * 硝壱軒葬 : 
    * [0,0]精 益 嬢恐 traverse 獄動亀 刊牽奄 穿税 段奄 雌殿戚陥.
    * 戚 雌殿拭辞 next 獄動聖 刊献陥檎
    * 1) leftDiffIndex税 1腰 index採斗 block聖 達奄獣拙廃陥.
    * 2) block聖 達紹陥檎 益 block税 begin index研 薄仙 朕辞税 begin index拭 企脊.
    * 3) block税 魁聖 達奄 是背 伊塘研 廃陥. <戚 凶, 因拷匝vs陥献鎧遂昔 block case人 陥献鎧遂vs陥献鎧遂昔 block case研 蟹干陥.>
    * 4) block税 魁聖 達紹陥檎 朕辞税 end index拭 企脊.
    * 5) MainView澗 getTraverseCursor聖 搭背 薄仙 亜軒徹澗 [begin index, end index] 研 条澗陥.
    * # 是税 previous澗 戚人 鋼企稽 戚欠嬢閃赤陥.
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

   ////////////////////////merge税 敗呪 CTL, CTR 耕刃失 ばばばばばばばばばばばばばばばばばばばばばばばばばばばばばばば
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