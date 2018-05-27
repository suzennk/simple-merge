import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class MainView extends JFrame{
	private JPanel mainPanel;

	// 도구 패널
	private JPanel toolPanel;
	private JButton compareBtn;
	private JButton upBtn;
	private JButton downBtn;
	private JButton copyToLeftBtn;
	private JButton copyToRightBtn;
	
	// 세부 패널 (왼쪽, 오른쪽)
	private JPanel holderPanel;
	private PanelView leftPV;
	private PanelView rightPV;
	
	public MainView() throws Exception {
		super("Simple Merge");
		
		mainPanel = new JPanel();
		
		toolPanel = new JPanel();
		
		compareBtn = new JButton("Compare");
		upBtn = new JButton("Up");
		downBtn = new JButton("Down");
		copyToLeftBtn = new JButton("Copy to left");
		copyToRightBtn = new JButton("Copy to right");
		
		holderPanel = new JPanel();
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
		
		holderPanel.setLayout(new GridLayout(1, 2, 10, 0));
		holderPanel.add(leftPV);
		holderPanel.add(rightPV);
		
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(toolPanel, BorderLayout.NORTH);
		mainPanel.add(holderPanel, BorderLayout.CENTER);
		
		this.add(mainPanel);
		this.pack();
		this.setSize(1200, 900);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Start!");
		
		MainView mv = new MainView();
		
		System.out.println("End!");
	}
}