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
	   
//	   private JTextArea myTextArea;
	   private JEditorPane myTextArea;
	   private JScrollPane scrollPane;
	   private JLabel statusBar;
	   
	   public PanelView() throws Exception{
		   myPanel = new JPanel();
		   
		   menuPanel = new JPanel();
		   loadBtn = new JButton("Load");
		   editBtn = new JButton("Edit");
		   saveBtn = new JButton("Save");
		   saveAsBtn = new JButton("Save As");
		   
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