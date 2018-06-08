package view;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;

import controller.TextEditorController;
import model.Mode;
import model.TextEditorModel;

public class PanelView extends JPanel {
	protected	TextEditorController tec;

	private 	JPanel menuPanel;	
	protected 	JButton loadBtn;
	private 	JButton editBtn;
	private 	JButton saveBtn;
	protected 	JButton saveAsBtn;
	
	private 	JPanel editorPanel; // xPanel + text field
	
	private 	JPanel titlePanel; // filename, xbutton
	public 		JLabel fileNameLabel;
	protected 	JButton xBtn;
	
	private     JScrollPane scrollPane;
	private		TextLineNumber tln;
	protected   JEditorPane textArea;
	protected   CompareTable textTable;
	private     DefaultTableModel model;
	
	private 	JLabel statusLabel;
	
	private 	ImageIcon load_icon;
	private 	ImageIcon edit_icon;
	private 	ImageIcon save_icon;
	private 	ImageIcon saveAs_icon;
	private 	ImageIcon x_icon;

	private Color highlightColor;
	private Color focusColor;
	
	private FileDialog fd;
	
	public PanelView() throws Exception{
		tec				= new TextEditorController();
		
		menuPanel 		= new JPanel();
		editorPanel 	= new JPanel();
		titlePanel 		= new JPanel();
		
		highlightColor 	= new Color(0,0,0); // set color default as WHITE
		focusColor 		= new Color(0,0,0);		
		
		fd = new FileDialog(new JFrame(), "Open File", FileDialog.SAVE);
				 
		// set image icon
		load_icon 		= new ImageIcon("res/load.png");
		edit_icon 		= new ImageIcon("res/edit.png");
		save_icon 		= new ImageIcon("res/save.png");
		saveAs_icon		= new ImageIcon("res/save_as.png");
		x_icon 			= new ImageIcon("res/reject.png");
			
		// set size of image button
		Image load_img 		= load_icon.getImage();		load_img = load_img.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
		Image edit_img 		= edit_icon.getImage();		edit_img = edit_img.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
		Image save_img 		= save_icon.getImage();		save_img = save_img.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
		Image saveAs_img	= saveAs_icon.getImage();	saveAs_img = saveAs_img.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
		Image x_img 		= x_icon.getImage();		x_img = x_img.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
		
		load_icon 		= new ImageIcon(load_img);
		edit_icon 		= new ImageIcon(edit_img);
		save_icon 		= new ImageIcon(save_img);
		saveAs_icon 	= new ImageIcon(saveAs_img);
		x_icon 			= new ImageIcon(x_img);
			
		// set image button
		loadBtn			= new JButton(load_icon);		loadBtn.setContentAreaFilled(false);
		editBtn 		= new JButton(edit_icon); 		editBtn.setContentAreaFilled(false);
		saveBtn 		= new JButton(save_icon);		saveBtn.setContentAreaFilled(false);
		saveAsBtn 		= new JButton(saveAs_icon); 	saveAsBtn.setContentAreaFilled(false);
		xBtn 			= new JButton(x_icon);			xBtn.setContentAreaFilled(false); 
			
		// make Image Button's border invisible
		loadBtn.setBorderPainted(false); 				loadBtn.setFocusPainted(false);
		editBtn.setBorderPainted(false); 				editBtn.setFocusPainted(false);
		saveBtn.setBorderPainted(false); 				saveBtn.setFocusPainted(false);
		saveAsBtn.setBorderPainted(false); 				saveAsBtn.setFocusPainted(false);
		xBtn.setFocusPainted(false); 					xBtn.setBorderPainted(false);
	
		
		fileNameLabel 	= new JLabel("");
		fileNameLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		fileNameLabel.setFont(new Font(fileNameLabel.getFont().getName(), Font.BOLD, 20));

		// Text Area
		textArea	 	= new JEditorPane();
		textArea.setText("Click the Load Button.");
		textArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		textArea.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
		
		tln		 		= new TextLineNumber(textArea);
		model			= new DefaultTableModel();
	    textTable		= new CompareTable();

		scrollPane		= new JScrollPane(textArea);
		scrollPane.setRowHeaderView(tln);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		 
		statusLabel		= new JLabel("");
		statusLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 2, 0));
		
		menuPanel.setLayout(new GridLayout(1, 4));   
		menuPanel.add(loadBtn);
		menuPanel.add(editBtn);
		menuPanel.add(saveBtn);
		menuPanel.add(saveAsBtn);
		menuPanel.setBorder(new MatteBorder(0,0,1,0, Color.GRAY));
		
		titlePanel.setLayout(new BorderLayout());
		titlePanel.add(fileNameLabel, BorderLayout.CENTER);
		titlePanel.add(xBtn, BorderLayout.EAST);
		
		editorPanel.setLayout(new BorderLayout());
		editorPanel.add(titlePanel, BorderLayout.NORTH);
		editorPanel.add(scrollPane, BorderLayout.CENTER);
		editorPanel.add(statusLabel, BorderLayout.SOUTH);
		
		this.setLayout(new BorderLayout());
		this.add(menuPanel, BorderLayout.NORTH);
		this.add(editorPanel, BorderLayout.CENTER);
		this.setVisible(true);
		
		this.setMode(Mode.VIEW);
		editBtn.setEnabled(false); // at the start, only loadBtn enable!
		
		// keyboard input
		textArea.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				// DIRTY FLAG SET
				if (tec.fileIsOpen()) {
					tec.setUpdated(true);
				}
				tec.setFileContentBuffer(textArea.getText());
				updateView();
			}
		});
		   
		editBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// Set Mode
				setMode(Mode.EDIT);
			}
		});
		
		saveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

		  		if (tec.getMode() == Mode.COMPARE) {
		  			tec.fileContentBufferToString();
		  			save();
		  		}
		  		else {
					tec.setFileContentBuffer(textArea.getText());
			  		save();
		  		}
		  	}
				
		});
		   
		saveAsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (tec.getMode() == Mode.COMPARE) {
					tec.fileContentBufferToString();
					saveAs();
				}
				else {
					tec.setFileContentBuffer(textArea.getText());
					saveAs();
				}
			}
		});
	}

	
	
	public void save() {
  		if (tec.save()) {
  			if(tec.getMode() != Mode.COMPARE)
  				setMode(Mode.VIEW);
  		}
	}
	
	public void saveAs() {
		fd.setVisible(true);
		
		if (fd.getFile() != null) {
			String filePath = fd.getDirectory() + fd.getFile();
			if (tec.saveAs(filePath)) {
				if(tec.getMode()!=Mode.COMPARE)
					setMode(Mode.VIEW);	
				else 
					updateView();
			}
		}
	}
	
	public void enterCompareMode() {
		// make model arguments
		// get file content as Arraylist
		ArrayList<String> fileContentList = tec.getAlignedFileContentBufferList();

		// Initialize model and textTable, make textTable non-Editable
		textTable = new CompareTable(fileContentList, tec.getBlocks(), tec.getDiffIndices(), highlightColor, focusColor);
		
		// JTable
		textArea.setVisible(false);
		textTable.setVisible(true);

		// set textTable
		editorPanel.remove(scrollPane);
		scrollPane = new JScrollPane(textTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		editorPanel.add(scrollPane);
	}
	
	public void exitCompareMode() {
		// JEditorPane
		textTable.setVisible(false);
		textArea.setVisible(true);

		textArea.setText(tec.getFileContentBuffer());
		
		editorPanel.remove(scrollPane);
		scrollPane = new JScrollPane(textArea);
		scrollPane.setRowHeaderView(tln);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		editorPanel.add(scrollPane);
	}

	
	public void updateView() {
	
		if (!tec.fileIsOpen()) {
			textArea.setText("Click the Load Button.");
			editBtn.setEnabled(false);
		} 
		if (!tec.isUpdated()) {
			fileNameLabel.setText(tec.getFileName());
		} else {
			fileNameLabel.setText("*" + tec.getFileName());
		}

	}
	
	public void updateTable(boolean up) {
		textTable.highlightBlocks(tec.getBlocks(), tec.getDiffIndices(), tec.getTraverseIndex());
		if (up) {
			textTable.scrollUpToCurrentIndex(tec.getBlocks(), tec.getCurrentBlock());
		}
		else {
			textTable.scrollDownToCurrentIndex(tec.getBlocks(), tec.getCurrentBlock());
		}
		
	}
	
	public void updateTableModel() {
		textTable.updateModel(tec.getAlignedFileContentBufferList(), tec.getBlocks(), tec.getDiffIndices());
	}
	
	/**
	 * @return -1 if not updated
	 * @return 0 if save
	 * @return 1 if don't save
	 * @return 2 if cancel
	 */
	public int showSaveDialog() {
		Object[] options = { "Save", "Don't Save", "Cancel" };
		int n = -1;

		if (tec.isUpdated()) {
			n = JOptionPane.showOptionDialog(this,
					tec.getFileName() +" has been edited.\nDo you want to save the file and continue?", "Simple Merge",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
		}
		return n;
	}

	public int showSaveDialogWithCompletion() {
		int n = showSaveDialog();
		
		if (n == 0) {										// YES: save and switch to view mode
			this.save();	
		} 
		else if(n == 1) { 									// NO: not save, switch to view mode
			this.resetToOriginal();
		} 
		else {
			// Do nothing
		}
		
		return n;
	}
	
	public void setMode(Mode mode) {
		switch (mode) {
		case VIEW:
			tec.setMode(Mode.VIEW);
			statusLabel.setText("View Mode");
			textArea.setEditable(false);
			loadBtn.setEnabled(true);
			editBtn.setEnabled(true);
			saveBtn.setEnabled(false);
			saveAsBtn.setEnabled(false);
			xBtn.setEnabled(true);
			break;
		case EDIT:
			tec.setMode(Mode.EDIT);
			statusLabel.setText("Edit Mode");
			textArea.setEditable(true);
			loadBtn.setEnabled(true);
			editBtn.setEnabled(false);
			saveBtn.setEnabled(true);
			saveAsBtn.setEnabled(true);
			xBtn.setEnabled(true);
			break;
		case COMPARE:
			tec.setMode(Mode.COMPARE);
			statusLabel.setText("Compare Mode");
			textArea.setEditable(false);
			loadBtn.setEnabled(false);
			editBtn.setEnabled(false);
			saveBtn.setEnabled(true);
			saveAsBtn.setEnabled(true);
			xBtn.setEnabled(false);
			break;
		default:
			break;
		}
		updateView();

	}
	
	public void resetToOriginal() {
		tec.resetToOriginal();
		this.textArea.setText(tec.getFileContentBuffer());
	}

	public void setHighlightColor(int x, int y, int z){
		highlightColor = new Color(x, y, z);
	}

	public void setFocusColor(int x, int y, int z) {
		focusColor = new Color(x, y, z);
	}

	public TextEditorModel getTEM() {
		return tec.getTEM();
	}
}
