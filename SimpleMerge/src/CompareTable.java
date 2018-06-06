import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

public class CompareTable extends JTable {
	private DefaultTableModel model;
	private Color highlightColor;
	private Color focusColor;

	private CompareTableRenderer renderer;

	public CompareTable() {

	}

	public CompareTable(ArrayList<String> fileContentList, ArrayList<int[]> blocks, ArrayList<Integer> diffIndices, Color highlightColor, Color focusColor) {
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
			if (diffIndices.get(i) == 0) {
				contents.addElement("-");
			}
			else {
			contents.addElement(String.valueOf(++ctr));
			}
			contents.addElement(fileContentList.get(i));
			model.addRow(contents);
		}

		// Highlight Lines
		highlightBlocks(blocks, diffIndices);

		this.setModel(model);

		// Set Color or Grid and Header
		this.setShowHorizontalLines(false);
		this.setGridColor(Color.LIGHT_GRAY);
		JTableHeader header = this.getTableHeader();
		header.setBackground(Color.WHITE);

		// Set row and column size
		this.setRowHeight(20);
		TableColumnModel col = this.getColumnModel();
		col.getColumn(0).setMaxWidth(40);
	}

	public void highlightBlocks(ArrayList<int[]> blocks, ArrayList<Integer> greyIndices, int traverseIndex) {

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
		renderer = new CompareTableRenderer(highlightIndices, greyIndices, highlightColor, focusColor);
		try {
			this.setDefaultRenderer(Class.forName("java.lang.Object"), renderer);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		repaint();

	}
	
	public void updateModel(ArrayList<String> fileContentList, ArrayList<int[]> blocks, ArrayList<Integer> diffIndices) {
		Vector<String> head = new Vector<String>();
		head.addElement("line");
		head.addElement("Content");
		DefaultTableModel model = new DefaultTableModel(head, 0);

		// Set contents
		int ctr = 0;
		for (int i = 1; i < fileContentList.size(); i++) {
			Vector<String> contents = new Vector<String>();
			if (diffIndices.get(i) == 0) {
				contents.addElement("-");
			}
			else {
			contents.addElement(String.valueOf(++ctr));
			}
			contents.addElement(fileContentList.get(i));
			model.addRow(contents);
		}

		// Highlight Lines
		highlightBlocks(blocks, diffIndices);

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
	}
	
	public void highlightBlocks(ArrayList<int[]> blocks, ArrayList<Integer> greyIndices) {

		highlightBlocks(blocks, greyIndices, 0);

	}
	
	public void scrollDownToCurrentIndex(ArrayList<int[]> blocks, int[] currentBlock) {
		if (blocks.size() != 0) {
			Rectangle cellRect = this.getCellRect(currentBlock[1] - 1, 0, true);
			this.scrollRectToVisible(cellRect);
		}
	}

	public void scrollUpToCurrentIndex(ArrayList<int[]> blocks, int[] currentBlock) {
		if (blocks.size() != 0) {
			Rectangle cellRect = this.getCellRect(currentBlock[0] - 1, 0, true);
			this.scrollRectToVisible(cellRect);
		}
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		return false;
	}

}