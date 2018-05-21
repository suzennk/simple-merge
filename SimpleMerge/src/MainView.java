import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.*;

public class MainView extends JFrame{
	private JPanel MainPanel;

	private JPanel toolPanel;
	private JButton compareBtn;
	private JButton upBtn;
	private JButton downBtn;
	private JButton copyToLBtn;
	private JButton copyToRBtn;
	
	private JPanel holderPanel;
	private PanelView leftPV;
	private PanelView rightPV;
	
	public MainView() {
		super();
		
		toolPanel = new JPanel();
		
		compareBtn = new JButton("compare");
		upBtn = new JButton("up");
		downBtn = new JButton("down");
		copyToLBtn = new JButton("copy to left");
		copyToRBtn = new JButton("copy to right");
		
		holderPanel = new JPanel();
		leftPV = new PanelView();
		rightPV = new PanelView();
		
		toolPanel.setLayout(new FlowLayout());
		toolPanel.setBackground(Color.CYAN);
		
		toolPanel.add(compareBtn);
		toolPanel.add(upBtn);
		toolPanel.add(downBtn);
		toolPanel.add(copyToLBtn);
		toolPanel.add(copyToRBtn);
		
		leftPV.setBackground(Color.RED);
		rightPV.setBackground(Color.GREEN);
		
		holderPanel.setLayout(new GridLayout());
		holderPanel.add(leftPV);
		holderPanel.add(rightPV);
		
		
		this.setLayout(new BorderLayout());
		this.add(toolPanel, BorderLayout.NORTH);
		this.add(holderPanel, BorderLayout.CENTER);
		this.pack();
		
		this.setSize(1200, 900);
		this.setVisible(true);
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainView mv = new MainView();
		
		
	}

}
