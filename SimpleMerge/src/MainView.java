import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainView extends JFrame{
	private JPanel mainPanel;
	private int comparePressed;

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
	private ImageIcon notCompare_icon;
	
	
	// specific panel (right, left)
	private JPanel holderPanel;
	private PanelView leftPV;
	private PanelView rightPV;
	
	public MainView() throws Exception {
		super("Simple Merge");
		
		mainPanel = new JPanel();
		toolPanel = new JPanel();
		
		comparePressed = 0; //comparePressed: even=NOT pressed, odd= pressed
		
		// set image icon
		compare_icon=new ImageIcon("res/compare.png");
		up_icon=new ImageIcon("res/up.png");
		down_icon=new ImageIcon("res/down.png");
		left_icon=new ImageIcon("res/left.png");
		right_icon=new ImageIcon("res/right.png");
		notCompare_icon=new ImageIcon("res/not_compare.png");
		
		// set size of image button
		Image compare_img=compare_icon.getImage(); compare_img=compare_img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		Image up_img=up_icon.getImage(); up_img=up_img.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		Image down_img=down_icon.getImage(); down_img=down_img.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		Image left_img=left_icon.getImage(); left_img=left_img.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		Image right_img=right_icon.getImage(); right_img=right_img.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		Image notCompare_img=notCompare_icon.getImage(); notCompare_img=notCompare_img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		
		compare_icon=new ImageIcon(compare_img);
		up_icon=new ImageIcon(up_img);
		down_icon=new ImageIcon(down_img);
		left_icon=new ImageIcon(left_img);
		right_icon=new ImageIcon(right_img);
		notCompare_icon=new ImageIcon(notCompare_img);
		
		
		// set image button
		compareBtn = new JButton(notCompare_icon);
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
		
		
		holderPanel = new JPanel();
		leftPV = new PanelView();
		rightPV = new PanelView();
		
		leftPV.loadBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				load(leftPV, rightPV);
			}
		});
		
		rightPV.loadBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				load(rightPV, leftPV);
			}
		});
		
		compareBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("compare button pressed.");
				
				checkUpdated(leftPV);
				checkUpdated(rightPV);
				
				comparePressed++;
				if(comparePressed%2==1){
					//compareBtn pressed once->do compare
					compareBtn.setIcon(notCompare_icon);
					setMode(Mode.VIEW);
				}
				else{
					//compareBtn pressed twice->escape compare mode
					compareBtn.setIcon(compare_icon);
					setMode(Mode.COMPARE);
				}
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
	
	private void load(PanelView mine, PanelView yours) {
		System.out.println("Load button pressed.");
		// Check if dirty!!!!!!!!!!!!!!
		checkUpdated(mine);
		// Load file via fileDialog
		FileDialog fd = new FileDialog(this, "Open File", FileDialog.LOAD);
		fd.setVisible(true);
		
		if (fd.getFile() != null) {
			String filePath = fd.getDirectory() + fd.getFile();
        	if (yours.pc.getFile() == null) {
        		if (mine.pc.load(filePath)) {
        			mine.setMode(Mode.VIEW);
        			mine.myTextArea.setText(mine.pc.getFileContent());
        		}
        	} else if (!filePath.equals(yours.pc.getFile().toString())) {
        		if(mine.pc.load(filePath)) {
        			mine.setMode(Mode.VIEW);
        			mine.myTextArea.setText(mine.pc.getFileContent());
        		}
        	} else 
        		System.out.println("File is already open in another panel.");
        } else {
            System.out.println("File load canceled.");
		}
        
	}

	private void setMode(Mode mode) {
		switch(mode) {
		case VIEW:
			leftPV.setMode(Mode.VIEW);
			rightPV.setMode(Mode.VIEW);
			break;
			
		case COMPARE:
			leftPV.setMode(Mode.COMPARE);
			rightPV.setMode(Mode.COMPARE);
			break;
		default:
			break;
		}
	}
	
	private void checkUpdated(PanelView pv) {
		Object[] options = {"Save", "Don't Save", "Cancel"};
				
		if (pv.pc.isUpdated()) {
			int n = JOptionPane.showOptionDialog(this, "The file has been edited. Do you want to save the file and continue?", "Question", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
			if (n == 0 ) { // save
				pv.save();
			} else {
				pv.pc.setUpdated(false);
				pv.pc.load(pv.pc.getFile().toString());
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Start!");
		
		MainView mv = new MainView();
		
		System.out.println("End!");
	}
}