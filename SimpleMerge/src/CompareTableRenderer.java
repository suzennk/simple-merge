import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class CompareTableRenderer extends DefaultTableCellRenderer {

	private ArrayList<Integer> arri = new ArrayList<Integer>();
	private Color lineColor;
	private Color focusColor;

	@SuppressWarnings("unchecked")
	public CompareTableRenderer(ArrayList<Integer> arr, Color highlightColor, Color focusColor) {
		this.arri = (ArrayList<Integer>) arr.clone();
		this.lineColor = highlightColor;
		this.focusColor = focusColor;
	}

	public Component getTableCellRendererComponent(JTable table, Object object, boolean isSelected, boolean hasFocus,
			int row, int col) {
		Component cell = super.getTableCellRendererComponent(table, object, isSelected, hasFocus, row, col);

		this.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
		
		if (!isSelected) {
			if (arri.contains(row + 1))
				cell.setBackground(lineColor);

			else if (arri.contains(-row - 1))
				cell.setBackground(focusColor);

			else
				cell.setBackground(Color.white);
		}
		return cell;
	}
}
