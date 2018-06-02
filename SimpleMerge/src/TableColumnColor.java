import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class TableColumnColor extends DefaultTableCellRenderer{
   
   private ArrayList<Integer> arri = new ArrayList<Integer>();
   private Color lineColor;
   private Color LetterColor;
   
   @SuppressWarnings("unchecked")
   public TableColumnColor (ArrayList<Integer> arr, Color color){
      //this.arri = (ArrayList<Integer>) arr.clone();
      arri.add(1);
      arri.add(4);
      arri.add(8);
      this.lineColor = color;
   }

   public Component getTableCellRendererComponent(JTable table, Object object, boolean isSelected, boolean hasFocus, int row, int col) {
      Component cell = super.getTableCellRendererComponent(table, object, isSelected, hasFocus, row, col);
      
      if (!isSelected) {
         if (arri.contains(row+1)) {
            cell.setBackground(lineColor);
         }
         else
            cell.setBackground(Color.white);
      }
      return cell;
   }
}
