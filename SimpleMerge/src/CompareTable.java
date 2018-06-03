import java.awt.Color;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

public class CompareTable extends JTable {
   DefaultTableModel model;
   ArrayList<Integer> highlightIndices = new ArrayList<Integer>();
   Color highlightColor;

   public CompareTable() {

   }

   public CompareTable(ArrayList<String> fileContentList, ArrayList<int[]> blocks, Color highlightColor) {
      super();
      
      // Set highlight color
      this.highlightColor = highlightColor;
      
      // Set header
      Vector<String> head = new Vector<String>();
      head.addElement("line");
      head.addElement("Content");
      model = new DefaultTableModel(head, 0);

      // Set contents
      for (int i = 0; i < fileContentList.size(); i++) {
         Vector<String> contents = new Vector<String>();
         contents.addElement(String.valueOf(i + 1));
         contents.addElement(fileContentList.get(i));
         model.addRow(contents);
         System.out.println(fileContentList.get(i));
      }
      
      // Highlight Lines
      highlightBlocks(blocks);

      this.setModel(model);

      // Set Color or Grid and Header
      this.setShowHorizontalLines(false);
      this.setGridColor(Color.LIGHT_GRAY);
      JTableHeader header = this.getTableHeader();
      header.setBackground(Color.WHITE);

      // TODO column width
      // Set column size
      TableColumnModel col = this.getColumnModel();
      col.getColumn(0).setPreferredWidth(30);
      col.getColumn(1).setPreferredWidth(550);

      this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

      ///// for testing
//      ArrayList<int[]> blocks = new ArrayList<int[]>();
//      blocks.add(new int[]{1,5});
//      blocks.add(new int[]{8,9});
//      System.out.println(blocks);
//      highlightBlocks(blocks);
   }

   public void highlightBlocks(ArrayList<int[]> blocks) {
      for (int[] block: blocks) {
         int start = block[0];
         int end = block[1];

         for (int i = start; i <= end; i++) {
            highlightIndices.add(i);
            System.out.println(i);
         }   
      }
      
      TableColumnColor renderer = new TableColumnColor(highlightIndices, highlightColor);
      try {
         this.setDefaultRenderer(Class.forName("java.lang.Object"), renderer);
      } catch (ClassNotFoundException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

   }

   public void highlightCurrentBlock() {

   }

   @Override
   public boolean isCellEditable(int row, int col) {
      return false;
   }

}