import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

public class MainView extends JFrame{
	private JPanel mainPanel;

	// tool panel
	private JPanel toolPanel;
	private JButton compareBtn;
	private JButton upBtn;
	private JButton downBtn;
	private JButton copyToLeftBtn;
	private JButton copyToRightBtn;
	
	// image icon for ImageBtn
	private ImageIcon compare_icon;
	private ImageIcon up_icon;
	private ImageIcon down_icon;
	private ImageIcon left_icon;
	private ImageIcon right_icon;
	
	
	// specific panel (right, left)
	private JPanel holderPanel;
	private PanelView leftPV;
	private PanelView rightPV;
	
	private JFileChooser fileChooser;
	
	public MainView() throws Exception {
		super("Simple Merge");
		
		mainPanel = new JPanel();
		
		toolPanel = new JPanel();
		
		// set image icon
		compare_icon=new ImageIcon("res/compare.png");
		up_icon=new ImageIcon("res/up.png");
		down_icon=new ImageIcon("res/down.png");
		left_icon=new ImageIcon("res/left.png");
		right_icon=new ImageIcon("res/right.png");
		
		// set size of image button
		Image compare_img=compare_icon.getImage(); compare_img=compare_img.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		Image up_img=up_icon.getImage(); up_img=up_img.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		Image down_img=down_icon.getImage(); down_img=down_img.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		Image left_img=left_icon.getImage(); left_img=left_img.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		Image right_img=right_icon.getImage(); right_img=right_img.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		
		compare_icon=new ImageIcon(compare_img);
		up_icon=new ImageIcon(up_img);
		down_icon=new ImageIcon(down_img);
		left_icon=new ImageIcon(left_img);
		right_icon=new ImageIcon(right_img);
		
		// set image button
		compareBtn = new JButton(compare_icon);
		upBtn = new JButton(up_icon);
		downBtn = new JButton(down_icon);
		copyToLeftBtn = new JButton(left_icon);
		copyToRightBtn = new JButton(right_icon);
		
		
		// make Image Button's border invisible
		compareBtn.setBorderPainted(false); compareBtn.setFocusPainted(false);
		upBtn.setBorderPainted(false); upBtn.setFocusPainted(false);
		downBtn.setBorderPainted(false); downBtn.setFocusPainted(false);
		copyToLeftBtn.setBorderPainted(false); copyToLeftBtn.setFocusPainted(false);
		copyToRightBtn.setBorderPainted(false); copyToRightBtn.setFocusPainted(false);
		
		add(compareBtn);
		add(upBtn);
		add(downBtn);
		add(copyToLeftBtn);
		add(copyToRightBtn);
		
		fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory()); // �뵒�젆�넗由� �꽕�젙 
        fileChooser.setCurrentDirectory(new File("/")); // �쁽�옱 �궗�슜 �뵒�젆�넗由щ�� 吏��젙 
        fileChooser.setAcceptAllFileFilterUsed(true);   // Filter 紐⑤뱺 �뙆�씪 �쟻�슜  
        fileChooser.setDialogTitle("Choose File to Open"); // 李쎌쓽 �젣紐� 
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); // �뙆�씪 �꽑�깮 紐⑤뱶 
        
//        FileNameExtensionFilter filter = new FileNameExtensionFilter("Binary File", "cd11"); //  filter �솗�옣�옄 異붽� 
//        fileChooser.setFileFilter(filter); // �뙆�씪 �븘�꽣瑜� 異붽�
        
		
		holderPanel = new JPanel();
		leftPV = new PanelView();
		rightPV = new PanelView();
		
		leftPV.loadBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Load button pressed.");
				
				// Load file via fileChooser
				int returnVal = fileChooser.showOpenDialog(null);
	            if( returnVal == JFileChooser.APPROVE_OPTION) {
	            	String filePath = fileChooser.getSelectedFile().toString();
	            	String rightFilePath = null;
	            	if (rightPV.pc.getFile() != null )
	            		rightFilePath = rightPV.pc.getFile().toString();
	            	if (filePath != rightFilePath)
	            		leftPV.pc.load(filePath);
	            	else 
	            		System.out.println("File is already open in another panel.");
	            } else {
	                System.out.println("File load canceled.");
				}
	            
	            // Set the text in view
	            leftPV.myTextArea.setText(leftPV.pc.getFileContent());
			}
		});
		
		rightPV.loadBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Load button pressed.");
				
				// Load file via fileChooser
				int returnVal = fileChooser.showOpenDialog(null);
	            if( returnVal == JFileChooser.APPROVE_OPTION) {
	            	String filePath = fileChooser.getSelectedFile().toString();
	            	String leftFilePath = null;
	            	if (leftPV.pc.getFile() != null )
	            		leftFilePath = leftPV.pc.getFile().toString();
	            	if (filePath != leftFilePath) {
	            		rightPV.pc.load(filePath);
	    	            // Set the text in view
	    	            rightPV.myTextArea.setText(rightPV.pc.getFileContent());
	            	} else 
	            		System.out.println("File is already open in another panel.");
	            } else {
	                System.out.println("File load canceled.");
				}
	            
			}
		});
		compareBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("compare button pressed.");
			}
			
		});
		
		upBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("up button pressed.");
			}
			
		});
		
		downBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("down button pressed.");
			}
			
		});
		
		copyToLeftBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("copy to left button pressed.");
			}
			
		});
		
		copyToRightBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("copy to right button pressed.");
			}
			
		});
		
		toolPanel.setLayout(new GridLayout(1, 5));
		
		toolPanel.add(compareBtn);
		toolPanel.add(upBtn);
		toolPanel.add(downBtn);
		toolPanel.add(copyToLeftBtn);
		toolPanel.add(copyToRightBtn);
		
		holderPanel.setLayout(new GridLayout(1, 2, 10, 0));
		holderPanel.add(leftPV);
		holderPanel.add(rightPV);
		
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(toolPanel, BorderLayout.NORTH);
		mainPanel.add(holderPanel, BorderLayout.CENTER);
		
		this.add(mainPanel);
		this.pack();
		this.setSize(1200, 900);
		this.setVisible(true);
	
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Start!");
		
		MainView mv = new MainView();
		
		System.out.println("End!");
	}
}