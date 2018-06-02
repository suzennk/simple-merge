import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;

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
	protected   JEditorPane textArea;
	protected   JTable textTable;
	private     DefaultTableModel model;
	
	private 	JLabel statusLabel;
	
	private 	ImageIcon load_icon;
	private 	ImageIcon edit_icon;
	private 	ImageIcon save_icon;
	private 	ImageIcon saveAs_icon;
	private 	ImageIcon x_icon;

	
	private Color panelColor;
	
	private ArrayList<Integer> diffIndex;
	private int[] block; // block to be colored: {begin_idx, end_idx}
	   
	
	public PanelView() throws Exception{
		tec				= new TextEditorController();
		
		menuPanel 		= new JPanel();
		editorPanel 	= new JPanel();
		titlePanel 		= new JPanel();
		
		panelColor 		= new Color(0,0,0); // set color default as WHITE 
		diffIndex		= new ArrayList<Integer>(); // highlighted when compare & traverse
		block 			= new int[] {0, 0}; // default value : nothing to be colored

		
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
		fileNameLabel.setFont(new Font("Arial",Font.BOLD,20));		

		// Text Area
		textArea	 	= new JEditorPane();
		textArea.setText("Click the Load Button.");
		textArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		model			= new DefaultTableModel();
	    textTable		= new JTable();

		scrollPane = new JScrollPane(textArea);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		 
		statusLabel = new JLabel("");
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
				tec.panel.setUpdated(true);
				updateView();
			}
		});
		   
		editBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				System.out.println("Edit button pressed.");
				
				// Set Mode
				setMode(Mode.EDIT);
			}
		});
		
		saveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		  		System.out.println("Save button pressed.");

		  		// Save
		  		save();
		  	}
				
		});
		   
		saveAsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Save As button pressed.");
				
				// SaveAs
				saveAs();
			}
		});
	}
	public void setMode(Mode mode) {
        switch(mode) {
        case VIEW:
           tec.panel.setMode(Mode.VIEW);
           statusLabel.setText("View Mode");
           textArea.setEditable(false);
           loadBtn.setEnabled(true);
           editBtn.setEnabled(true);
           saveBtn.setEnabled(false);
           saveAsBtn.setEnabled(false);
           xBtn.setEnabled(true);
           break;
        case EDIT:
           tec.panel.setMode(Mode.EDIT);
           statusLabel.setText("Edit Mode");
           textArea.setEditable(true);
           loadBtn.setEnabled(true);
           editBtn.setEnabled(false);
           saveBtn.setEnabled(true);
           saveAsBtn.setEnabled(true);
           xBtn.setEnabled(true);
           break;
        case COMPARE:
           tec.panel.setMode(Mode.COMPARE);
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
//	public void setMode(Mode mode) {
//        switch(mode) {
//        case VIEW:
//           pc.setMode(Mode.VIEW);
//           statusLabel.setText("View Mode");
//           textArea.setEditable(false);
//           loadBtn.setEnabled(true);
//           editBtn.setEnabled(true);
//           saveBtn.setEnabled(false);
//           saveAsBtn.setEnabled(false);
//           xBtn.setEnabled(true);
//           
//           changeToEditor();
//           break;
//        case EDIT:
//           pc.setMode(Mode.EDIT);
//           statusLabel.setText("Edit Mode");
//           textArea.setEditable(true);
//           loadBtn.setEnabled(true);
//           editBtn.setEnabled(false);
//           saveBtn.setEnabled(true);
//           saveAsBtn.setEnabled(true);
//           xBtn.setEnabled(true);
//           break;
//        case COMPARE:
//           pc.setMode(Mode.COMPARE);
//           statusLabel.setText("Compare Mode");
//           textArea.setEditable(false);
//           loadBtn.setEnabled(false);
//           editBtn.setEnabled(false);
//           saveBtn.setEnabled(true);
//           saveAsBtn.setEnabled(true);
//           xBtn.setEnabled(false);
//           
//           changeToTable();
//           break;
//        default:
//           break;
//        }
//        updateView();
//     }
//  
//  private void changeToEditor() {
//     // JEditorPane
//       textTable.setVisible(false);
//       textArea.setVisible(true);
//       
//       editorPanel.remove(scrollPane);
//       scrollPane = new JScrollPane(textArea);
//       editorPanel.add(scrollPane);
//       System.out.println("change to editor");
//  }
//  
//  private void changeToTable() {
//     // make model arguments        
//       // set header
//       Vector<String> head = new Vector<String>();
//       head.addElement("line");
//       head.addElement("Content");
//       model = new DefaultTableModel(head, 0);
//       
//       
//       // set contents
//       for (int i = 0; i < pc.getFileContentList().size(); i++) {
//          Vector<String> contents = new Vector<String>();
//          contents.addElement(String.valueOf(i+1));
//          contents.addElement(pc.getFileContentList().get(i));
//          model.addRow(contents);
//          System.out.println(pc.getFileContentList().get(i));
//       }
//       
//    // TODO column width 수정(가로로 다 안보이는 겨우 있을 수 있음!)
//       
//       // Initialize model and textTable, make textTable non-Editable
//       textTable = new JTable(model) {
//          @Override
//          public boolean isCellEditable(int row, int col) {
//             return false;
//          }        
//       };
//       
//       // Set Color or Grid and Header
//       textTable.setGridColor(Color.LIGHT_GRAY);
//       JTableHeader header = textTable.getTableHeader();
//       header.setBackground(Color.WHITE);
//       
//       // Set column size
////       TableColumnModel col = textTable.getColumnModel();
////       col.getColumn(0).setPreferredWidth(30);
////       col.getColumn(1).setPreferredWidth(550);      
//       
////       textTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//       
//       // Color textTable
//       ArrayList<Integer> arri = new ArrayList<Integer>();
//       TableColumnColor renderer = new TableColumnColor(arri, panelColor);
//       try {
//        textTable.setDefaultRenderer(Class.forName("java.lang.Object"), renderer);
//     } catch (ClassNotFoundException e) {
//        // TODO Auto-generated catch block
//        e.printStackTrace();
//     }
//       
//       
//       // JTable
//       textArea.setVisible(false);
//       textTable.setVisible(true);
//       
//       // set textTable
//       editorPanel.remove(scrollPane);
//       scrollPane = new JScrollPane(textTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//       editorPanel.add(scrollPane);
//       System.out.println("change to text table");
//  }
	

	public int showSaveDialog() {
		Object[] options = {"Save", "Don't Save", "Cancel"};
		int n=0;
		
		if (tec.panel.isUpdated()) {
			n = JOptionPane.showOptionDialog(this,
											"The file has been edited. Do you want to save the file and continue?",
											"Question", 
											JOptionPane.YES_NO_CANCEL_OPTION,
											JOptionPane.QUESTION_MESSAGE,
											null, options, options[0]);
			
			if (n == 0) {										// YES: save and switch to view mode
				this.save();	
			} 
			else if(n == 1) { 									// NO: not save, switch to view mode
				tec.panel.setUpdated(false);
				textArea.setText(tec.panel.getOriginalFileContent()); 	// reset textArea to original file content	
			} 
			else {
				// Do nothing
			}
		}
		return n;
	}
	
	public void updateView() {
		if (tec.panel.getFile() == null) {
			textArea.setText("Click the Load Button.");
		}
		
		if (tec.panel.isUpdated()) {
			fileNameLabel.setText("*" + tec.panel.getFileName());
		} else {
			fileNameLabel.setText(tec.panel.getFileName());
		}
	}

	
	public void save() {
		String editedContent = textArea.getText();
		tec.panel.setFileContent(editedContent);
		
  		if (tec.panel.save()) {
  			if(tec.panel.getMode()!=Mode.COMPARE)
  				setMode(Mode.VIEW);
  		}
	}
	
	public void saveAs() {
		FileDialog fd = new FileDialog(new JFrame(), "Open File", FileDialog.SAVE);
		fd.setVisible(true);
		
		String editedContent = textArea.getText();
		tec.panel.setFileContent(editedContent);
		
		if (fd.getFile() != null) {
			String filePath = fd.getDirectory() + fd.getFile();
			if (tec.panel.saveAs(filePath)) {
				if(tec.panel.getMode()!=Mode.COMPARE)
					setMode(Mode.VIEW);	
			}
		}
	}
	
	public TextEditorModel getTextEditorModel() {
		return tec.panel;
	}
	
	public void setPanelColor(int x, int y, int z){
		panelColor=new Color(x,y,z);
	}
	
	public void setDiffIndex(ArrayList<Integer> diffIndex){
		this.diffIndex=diffIndex;
	}
	
	public void setBlock(int[] block){
		this.block=block;
	}
}
