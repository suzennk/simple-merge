package view;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FilenameFilter;

import javax.swing.*;
import javax.swing.border.MatteBorder;

import controller.MergeController;
import model.Mode;


public class MainView extends JFrame{
	private MergeController MGcontroller;
	
	private JPanel toolPanel;	// tool panel

	private JButton compareBtn;
	private JButton upBtn;
	private JButton downBtn;
	private JButton copyToLeftBtn;
	private JButton copyToRightBtn;

	private JPanel holderPanel;
	private PanelView leftPV; // specific panel (left)
	private PanelView rightPV; // specific panel (right)

	// image icon for ImageBtn
	private ImageIcon compare_icon;
	private ImageIcon up_icon;
	private ImageIcon down_icon;
	private ImageIcon left_icon;
	private ImageIcon right_icon;
	private ImageIcon view_icon;

	private int comparePressed;
	
	private FileDialog fd;

	public MainView() throws Exception {
		super("Simple Merge");

		toolPanel = new JPanel();
		holderPanel = new JPanel();
		leftPV = new PanelView();
		rightPV = new PanelView();

		comparePressed = 0; // comparePressed: even=NOT pressed, odd= pressed

		fd = new FileDialog(this, "Open File", FileDialog.LOAD);
		fd.setFilenameFilter(new FilenameFilter() {

			public boolean accept(File dir, String name) {
				return name.endsWith(".txt") || name.endsWith(".c") || name.endsWith(".cpp") || name.endsWith(".java")
						|| name.endsWith(".md") || name.endsWith(".py");
			}

		});
		
		// set image icon
		compare_icon = new ImageIcon("res/compare.png");
		up_icon = new ImageIcon("res/up.png");
		down_icon = new ImageIcon("res/down.png");
		left_icon = new ImageIcon("res/left.png");
		right_icon = new ImageIcon("res/right.png");
		view_icon = new ImageIcon("res/not_compare.png");

		// set size of image button
		Image compare_img = compare_icon.getImage();
		compare_img = compare_img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
		Image up_img = up_icon.getImage();
		up_img = up_img.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		Image down_img = down_icon.getImage();
		down_img = down_img.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		Image left_img = left_icon.getImage();
		left_img = left_img.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		Image right_img = right_icon.getImage();
		right_img = right_img.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		Image view_img = view_icon.getImage();
		view_img = view_img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);

		compare_icon = new ImageIcon(compare_img);
		up_icon = new ImageIcon(up_img);
		down_icon = new ImageIcon(down_img);
		left_icon = new ImageIcon(left_img);
		right_icon = new ImageIcon(right_img);
		view_icon = new ImageIcon(view_img);

		// set image button
		compareBtn = new JButton(compare_icon);
		compareBtn.setContentAreaFilled(false);
		upBtn = new JButton(up_icon);
		upBtn.setContentAreaFilled(false);
		downBtn = new JButton(down_icon);
		downBtn.setContentAreaFilled(false);
		copyToLeftBtn = new JButton(left_icon);
		copyToLeftBtn.setContentAreaFilled(false);
		copyToRightBtn = new JButton(right_icon);
		copyToRightBtn.setContentAreaFilled(false);

		// make Image Button's border invisible
		compareBtn.setBorderPainted(false);
		compareBtn.setFocusPainted(false);
		upBtn.setBorderPainted(false);
		upBtn.setFocusPainted(false);
		downBtn.setBorderPainted(false);
		downBtn.setFocusPainted(false);
		copyToLeftBtn.setBorderPainted(false);
		copyToLeftBtn.setFocusPainted(false);
		copyToRightBtn.setBorderPainted(false);
		copyToRightBtn.setFocusPainted(false);

		// set color of panel
		leftPV.setHighlightColor(255, 232, 232);
		leftPV.setFocusColor(255, 142, 142);
		leftPV.setBorder(new MatteBorder(0, 0, 0, 1, Color.GRAY));
		rightPV.setHighlightColor(218, 255, 216);
		rightPV.setFocusColor(126, 255, 119);

		// set tool panel
		toolPanel.setLayout(new GridLayout(1, 5));
		toolPanel.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
		toolPanel.add(compareBtn);
		toolPanel.add(upBtn);
		toolPanel.add(downBtn);
		toolPanel.add(copyToLeftBtn);
		toolPanel.add(copyToRightBtn);

		// set holder panel
		holderPanel.setLayout(new GridLayout(1, 2, 0, 0));
		holderPanel.add(leftPV);
		holderPanel.add(rightPV);

		// add all panels
		this.setLayout(new BorderLayout());
		this.add(toolPanel, BorderLayout.NORTH);
		this.add(holderPanel, BorderLayout.CENTER);

		this.pack();
		this.setSize(1200, 900);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setMVbutton(false);

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

		leftPV.xBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				closeFile(leftPV);
			}
		});

		rightPV.xBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				closeFile(rightPV);
			}
		});

		compareBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				comparePressed++;

				if (comparePressed % 2 == 1) {
					int a = leftPV.showSaveDialog();
					if (a == 2) {
						// keep compare mode
						comparePressed++;
						return;
					}

					int b = rightPV.showSaveDialog();

					if (b == 2) {
						// keep compare mode
						comparePressed++;
						return;
					}

					// if (a != 2 && b != 2)
					// save files if needed
					if (a == 0)
						leftPV.save();
					else if (a == 1) {
						leftPV.resetToOriginal();
					}

					if (b == 0)
						rightPV.save();
					else if (b == 1) {
						rightPV.resetToOriginal();
					}
					// compareBtn pressed once->do compare
					enterCompareMode();
					showMessageIfIdentical();

				} else {
					// compareBtn pressed twice->try to escape compare mode

					int a = leftPV.showSaveDialog();
					if (a == 2) {
						// keep compare mode
						comparePressed++;
						return;
					}

					int b = rightPV.showSaveDialog();

					if (b == 2) {
						// keep compare mode
						comparePressed++;
						return;
					}

					// if (a != 2 && b != 2)
					// save files if needed
					if (a == 0) {
						leftPV.tec.fileContentBufferToString();
						leftPV.save();
					} else if (a == 1) {
						leftPV.tec.setUpdated(false);
						leftPV.textArea.setText(leftPV.tec.getOriginalFileContent());
					}

					if (b == 0) {
						rightPV.tec.fileContentBufferToString();
						rightPV.save();
					} else if (b == 1) {
						rightPV.tec.setUpdated(false);
						rightPV.textArea.setText(rightPV.tec.getOriginalFileContent());
					}

					exitCompareMode();

				}
			}

		});

		upBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (MGcontroller.getFlagPrevious()) {
					// at least one previous block
					MGcontroller.traversePrevious();
					leftPV.updateTable(true);
					rightPV.updateTable(true);
				}
				updateView();
			}

		});

		downBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (MGcontroller.getFlagNext()) {
					// at least one next block
					MGcontroller.traverseNext();
					leftPV.updateTable(false);
					rightPV.updateTable(false);
				}

				updateView();
			}

		});

		copyToLeftBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				// do merge: copy to left
				MGcontroller.callCopyToLeft();

				leftPV.updateTableModel();
				rightPV.updateTableModel();
				leftPV.updateTable(false);
				rightPV.updateTable(false);

				leftPV.tec.setUpdated(true);

				updateView();
			}

		});

		copyToRightBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				// if there's no block to be merged, disable mergeBtn

				// do merge: copy to right
				MGcontroller.callCopyToRight();

				leftPV.updateTableModel();
				rightPV.updateTableModel();
				leftPV.updateTable(false);
				rightPV.updateTable(false);

				rightPV.tec.setUpdated(true);

				updateView();
			}

		});

	}

	private void load(PanelView mine, PanelView yours) {

		int dirtyCheck = mine.showSaveDialogWithCompletion();

		if (dirtyCheck == 2) {
			// file load canceled.
			return;
		}

		// Load file via fileDialog
		fd.setVisible(true);

		if (fd.getFile() != null) { // Pressed "Open" in FileDialog
			String filePath = fd.getDirectory() + fd.getFile();

			if (yours.tec.fileIsOpen() == false) { // if the other panel does not have a file open, LOAD
				if (mine.tec.load(filePath)) { // if load succeeds, set to View mode and update View
					mine.setMode(Mode.VIEW);
					mine.textArea.setText(mine.tec.getFileContentBuffer());
				} else {
					JOptionPane.showMessageDialog(null, "Failed to open file.", "ERROR!", JOptionPane.ERROR_MESSAGE);
				}
			} else if (!filePath.equals(yours.tec.getFilePath())) { // check if files in both panels are equal
				if (mine.tec.load(filePath)) {
					mine.setMode(Mode.VIEW);
					mine.textArea.setText(mine.tec.getFileContentBuffer());
				} else {
					JOptionPane.showMessageDialog(null, "Failed to open file.", "ERROR!", JOptionPane.ERROR_MESSAGE);
				}
			} else { // if the files are equal, cancel everything
				JOptionPane.showMessageDialog(null, "File is already open in another panel.", "ERROR!",
						JOptionPane.ERROR_MESSAGE);
			}
		}

		else {
			// Pressed "Cancel" in FileDialog
		}

		updateView();
	}

	private void closeFile(PanelView pv) {

		pv.tec.closeFile();

		// Set Mode
		setMode(Mode.VIEW);
	}

	public void setMVbutton(boolean tf) {
		compareBtn.setEnabled(tf);
		upBtn.setEnabled(tf);
		downBtn.setEnabled(tf);
		copyToLeftBtn.setEnabled(tf);
		copyToRightBtn.setEnabled(tf);
	}

	private void updateView() {

		if (leftPV.tec.getMode() == Mode.COMPARE) { // if compare mode
			if (!MGcontroller.getFlagPrevious())
				upBtn.setEnabled(false);
			else
				upBtn.setEnabled(true);

			if (!MGcontroller.getFlagNext())
				downBtn.setEnabled(false);
			else
				downBtn.setEnabled(true);

			// if there's no block to be merged, disable mergeBtn
			if (leftPV.tec.getBlocks().size() == 0) {
				copyToLeftBtn.setEnabled(false);
				copyToRightBtn.setEnabled(false);
			} else {
				copyToLeftBtn.setEnabled(true);
				copyToRightBtn.setEnabled(true);
			}
		} else { // if not compare mode
					// set enable [compare/merge/traverse] button only when two panel loaded
			if (leftPV.tec.fileIsOpen() && rightPV.tec.fileIsOpen()) {
				compareBtn.setEnabled(true);
			} else {
				compareBtn.setEnabled(false);
			}
		}

		leftPV.updateView();
		rightPV.updateView();
	}

	private void setMode(Mode mode) {
		switch (mode) {
		case VIEW:
			leftPV.setMode(Mode.VIEW);
			rightPV.setMode(Mode.VIEW);
			compareBtn.setEnabled(true);
			upBtn.setEnabled(false);
			downBtn.setEnabled(false);
			copyToLeftBtn.setEnabled(false);
			copyToRightBtn.setEnabled(false);
			break;

		case COMPARE:
			leftPV.setMode(Mode.COMPARE);
			rightPV.setMode(Mode.COMPARE);
			compareBtn.setEnabled(true);
			upBtn.setEnabled(true);
			downBtn.setEnabled(true);
			copyToLeftBtn.setEnabled(true);
			copyToRightBtn.setEnabled(true);

			break;
		default:
			break;
		}

		updateView();
	}

	private void enterCompareMode() {
		compareBtn.setIcon(view_icon);

		// create merge model
		MGcontroller = new MergeController(leftPV.getTEM(), rightPV.getTEM());

		// enter compare mode
		leftPV.enterCompareMode();
		rightPV.enterCompareMode();
		setMode(Mode.COMPARE);
	}

	private void exitCompareMode() {
		// convert to view mode
		compareBtn.setIcon(compare_icon);
		this.setMode(Mode.VIEW);
		leftPV.exitCompareMode();
		rightPV.exitCompareMode();
		setMode(Mode.VIEW);
	}

	private void showMessageIfIdentical() {
		if (leftPV.getTEM().getBlocks().size() == 0)
			JOptionPane.showMessageDialog(this, "The files are identical.", "Simple Merge", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void main(String[] args) throws Exception {

		MainView mv = new MainView();

	}
}