import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import javax.swing.*;

// 파일 선택창은 JFileChooser로 구현
// https://blog.naver.com/cracker542/40119977325

public class PanelView extends JPanel {
	   private JPanel myPanel;
	   
	   private JPanel menuPanel;
	   private JButton loadBtn;
	   private JButton editBtn;
	   private JButton saveBtn;
	   private JButton saveAsBtn;
	   
//	   image icon for imageBtn
		private ImageIcon load_icon;
		private ImageIcon edit_icon;
		private ImageIcon save_icon;
		private ImageIcon saveAs_icon;
	   
//	   private JTextArea myTextArea;
	   private JEditorPane myTextArea;
	   private JScrollPane scrollPane;
	   private JLabel statusBar;
	   
	   public PanelView() throws Exception{
		   myPanel = new JPanel();
		   
		   menuPanel = new JPanel();
		   
//	   set image icon
		   load_icon=new ImageIcon("res/load.png");
		   edit_icon=new ImageIcon("res/edit.png");
		   save_icon=new ImageIcon("res/save.png");
		   saveAs_icon=new ImageIcon("res/save_as.png");
			
//     set size of image button
		   Image load_img=load_icon.getImage(); load_img=load_img.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
		   Image edit_img=edit_icon.getImage(); edit_img=edit_img.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
		   Image save_img=save_icon.getImage(); save_img=save_img.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
		   Image saveAs_img=saveAs_icon.getImage(); saveAs_img=saveAs_img.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
			
			load_icon=new ImageIcon(load_img);
			edit_icon=new ImageIcon(edit_img);
			save_icon=new ImageIcon(save_img);
			saveAs_icon=new ImageIcon(saveAs_img);
			
//     set image button
			loadBtn = new JButton(load_icon);
			editBtn = new JButton(edit_icon);
			saveBtn = new JButton(save_icon);
			saveAsBtn = new JButton(saveAs_icon);
			
			
//     make Image Button's border invisible
			loadBtn.setBorderPainted(false); loadBtn.setFocusPainted(false);
			editBtn.setBorderPainted(false); editBtn.setFocusPainted(false);
			saveBtn.setBorderPainted(false); saveBtn.setFocusPainted(false);
			saveAsBtn.setBorderPainted(false); saveAsBtn.setFocusPainted(false);
			
			add(loadBtn);
			add(editBtn);
			add(saveBtn);
			add(saveAsBtn);
		   
//		   myTextArea = new JTextArea();
		   myTextArea = new JEditorPane();
		   
		   scrollPane = new JScrollPane(myTextArea);
		   statusBar = new JLabel("View Mode");
		   
		   loadBtn.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					System.out.println("Load button pressed.");
				}
				
			});
		   
		   editBtn.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					System.out.println("Edit button pressed.");
				}
				
			});
		   
		   saveBtn.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					System.out.println("Save button pressed.");
				}
				
			});
		   
		   saveAsBtn.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					System.out.println("Save As button pressed.");
				}
				
			});
		   
		   menuPanel.setLayout(new GridLayout(1, 3));
		   menuPanel.setBackground(Color.CYAN);
		   
		   menuPanel.add(loadBtn);
		   menuPanel.add(editBtn);
		   menuPanel.add(saveBtn);
		   menuPanel.add(saveAsBtn);
		   
		   myPanel.setLayout(new BorderLayout());
		   myPanel.add(menuPanel, BorderLayout.NORTH);
		   myPanel.add(myTextArea, BorderLayout.CENTER);
		   myPanel.add(statusBar, BorderLayout.SOUTH);
		   
		   this.setLayout(new BorderLayout());
		   this.add(myPanel);
		   this.setVisible(true);
	   }
}