import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.MatteBorder;

public class PanelView extends JPanel {
	protected	PanelController pc;

	private 	JPanel menuPanel;	
	protected 	JButton loadBtn;
	private 	JButton editBtn;
	private 	JButton saveBtn;
	protected 	JButton saveAsBtn;
	

	private 	JPanel editorPanel; // xPanel + text field
	
	private 	JPanel titlePanel; // filename, xbutton
	public 		JLabel fileNameLabel;
	protected 	JButton xBtn;
	
	private		JScrollPane scrollPane;
	protected	JEditorPane textArea;
	
	private 	JLabel statusLabel;
	
	private 	ImageIcon load_icon;
	private 	ImageIcon edit_icon;
	private 	ImageIcon save_icon;
	private 	ImageIcon saveAs_icon;
	private 	ImageIcon x_icon;

	
	private Color panelColor;
	   
	
	public PanelView() throws Exception{
		pc 				= new PanelController();
		
		menuPanel 		= new JPanel();
		editorPanel 	= new JPanel();
		titlePanel 		= new JPanel();
		
		panelColor 		= new Color(0,0,0); //set color default as WHITE 
		
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
		
		// keyboard input
		textArea.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				// DIRTY FLAG SET
				pc.setUpdated(true); 
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

		  		save();
		  		
		  		System.out.println("Save Completed.");
		  	}
				
		});
		   
		saveAsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Save As button pressed.");
				
				FileDialog fd = new FileDialog(new JFrame(), "Open File", FileDialog.SAVE);
				fd.setVisible(true);
				
				String editedContent = textArea.getText();
				pc.setFileContent(editedContent);
				
				if (fd.getFile() != null) {
					String filePath = fd.getDirectory() + fd.getFile();
					if (pc.saveAs(filePath)) {
						setMode(Mode.VIEW);	
					}
				}
			}
		});
	}
	
	public void setXEnabled(boolean tf){
		xBtn.setEnabled(tf);
	}

	public void setMode(Mode mode) {
		switch(mode) {
		case VIEW:
			pc.setMode(Mode.VIEW);
			statusLabel.setText("View Mode");
			textArea.setEditable(false);
			loadBtn.setEnabled(true);
			editBtn.setEnabled(true);
			saveBtn.setEnabled(false);
			saveAsBtn.setEnabled(false);
			xBtn.setEnabled(true);
			break;
		case EDIT:
			pc.setMode(Mode.EDIT);
			statusLabel.setText("Edit Mode");
			textArea.setEditable(true);
			loadBtn.setEnabled(true);
			editBtn.setEnabled(false);
			saveBtn.setEnabled(true);
			saveAsBtn.setEnabled(true);
			xBtn.setEnabled(true);
			break;
		case COMPARE:
			pc.setMode(Mode.COMPARE);
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
	

	public int checkUpdated() {
		Object[] options = {"Save", "Don't Save", "Cancel"};
		int n=0;
		
		if (pc.isUpdated()) {
			n = JOptionPane.showOptionDialog(this, "The file has been edited. Do you want to save the file and continue?", "Question", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			if (n == 0) {				 //YES: save and convert to view mode
				this.save();	
			} 
			else if(n == 1) { 			//NO: not save, convert to view mode
				pc.setUpdated(false);
				textArea.setText(pc.getOriginalFileContent()); //show panel state before escapeBtn pressed	
			} 
			else {
				// Do nothing
			}
		}
		return n;
	}
	
	public void updateView() {
		if (pc.getFile() == null) {
			textArea.setText("Click the Load Button.");
		}
		
		if (pc.isUpdated()) {
			fileNameLabel.setText("*" + pc.getFileName());
		} else {
			fileNameLabel.setText(pc.getFileName());
		}
	}

	
	public void save() {
		String editedContent = textArea.getText();
		pc.setFileContent(editedContent);
		
  		if (pc.save()) {
  			setMode(Mode.VIEW);
  		}
	}
	
	public PanelController getPC() {
		return pc;
	}
	
	public void setPanelColor(int x, int y, int z){
		panelColor=new Color(x,y,z);
	}
	
}
