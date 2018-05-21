import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class MainView extends JFrame{
	private JPanel mainPanel;

	private JPanel toolPanel;
	private JButton compareBtn;
	private JButton upBtn;
	private JButton downBtn;
	private JButton copyToLeftBtn;
	private JButton copyToRightBtn;
	
	private JPanel holderPanel;
	private PanelView leftPV;
	private PanelView rightPV;
	
	public MainView() {
		super("Simple Merge");
		
		mainPanel = new JPanel();
		
		toolPanel = new JPanel();
		holderPanel = new JPanel();
		
		compareBtn = new JButton("compare");
		upBtn = new JButton("up");
		downBtn = new JButton("down");
		copyToLeftBtn = new JButton("copy to left");
		copyToRightBtn = new JButton("copy to right");
		
		leftPV = new PanelView();
		rightPV = new PanelView();
		
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
		toolPanel.setBackground(Color.CYAN);
		
		toolPanel.add(compareBtn);
		toolPanel.add(upBtn);
		toolPanel.add(downBtn);
		toolPanel.add(copyToLeftBtn);
		toolPanel.add(copyToRightBtn);
		
		leftPV.setBackground(Color.RED);
		rightPV.setBackground(Color.GREEN);
		
		holderPanel.setLayout(new GridLayout());
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
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainView mv = new MainView();
		
	}

}
