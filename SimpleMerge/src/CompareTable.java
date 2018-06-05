import java.awt.Color;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

public class CompareTable extends JTable {
	private DefaultTableModel model;
	private Color highlightColor;
	private Color focusColor;

	private CompareTableRenderer renderer;

	public CompareTable() {

	}

	public CompareTable(ArrayList<String> fileContentList, ArrayList<int[]> blocks, ArrayList<Integer> diffIndicies, Color highlightColor, Color focusColor) {
		super();

		// Set highlight color
		this.highlightColor = highlightColor;
		this.focusColor = focusColor;
		
		// Set header
		Vector<String> head = new Vector<String>();
		head.addElement("line");
		head.addElement("Content");
		model = new DefaultTableModel(head, 0);

		// Set contents
		int ctr = 0;
		for (int i = 1; i < fileContentList.size(); i++) {
			Vector<String> contents = new Vector<String>();
			if (diffIndicies.get(i) == 0) {
				contents.addElement("-");
			}
			else {
			contents.addElement(String.valueOf(++ctr));
			}
			contents.addElement(fileContentList.get(i));
			model.addRow(contents);
//			System.out.println(i + "\t\t" + fileContentList.get(i));
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
		col.getColumn(0).setPreferredWidth(40);
		col.getColumn(1).setPreferredWidth(550);
		this.setRowHeight(20);

		this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}

	public void highlightBlocks(ArrayList<int[]> blocks, int traverseIndex) {

		ArrayList<Integer> highlightIndices = new ArrayList<Integer>();
		
		for (int i = 0; i < blocks.size(); i++) {
			int[] block = blocks.get(i);
			int start = block[0];
			int end = block[1];

			for (int j = start; j <= end; j++) {
				if (i == traverseIndex)
					highlightIndices.add(-j);
				else 
					highlightIndices.add(j);
			}
			
		}
		renderer = new CompareTableRenderer(highlightIndices, highlightColor, focusColor);
		try {
			this.setDefaultRenderer(Class.forName("java.lang.Object"), renderer);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		repaint();

	}
	
	public void highlightBlocks(ArrayList<int[]> blocks) {

		highlightBlocks(blocks, 0);

	}


	@Override
	public boolean isCellEditable(int row, int col) {
		return false;
	}

}