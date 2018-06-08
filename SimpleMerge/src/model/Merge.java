package model;

import java.util.ArrayList;
import java.util.Scanner;

/** Merge Model
 *  Compare, traverse and merge functions
 */
public class Merge {

	private TextEditorModel leftPanel;
	private TextEditorModel rightPanel;
	private ArrayList<String> leftFileContents;
	private ArrayList<String> rightFileContents;
	private ArrayList<String> leftFileSourceContents;
	private ArrayList<String> rightFileSourceContents;
	private ArrayList<String> leftViewContents;
	private ArrayList<String> rightViewContents;
	/**
	 * arrayList which stores difference indices of each panel
	 * the index is started at 1, not 0
	 */
	private ArrayList<Integer> leftDiffIndex;
	private ArrayList<Integer> rightDiffIndex;
	private ArrayList<int[]> blocks;

	/**
	 * current location 
	 * int[2] => {start index, end index}
	 */
	private int traverseCursor;


	Merge() {
		// should not call this constructor
	}

	public Merge(TextEditorModel leftPanel, TextEditorModel rightPanel) {
		this.leftPanel = leftPanel;
		this.rightPanel = rightPanel;
		this.traverseCursor = 0;

		/* get panel contents, parse it and store to arrayList */
		this.leftFileContents = leftPanel.getFileContentBufferStringToList();
		this.leftFileSourceContents = new ArrayList<String>(leftFileContents);
		this.leftViewContents = new ArrayList<String>();
		this.leftViewContents.add("");

		this.rightFileContents = rightPanel.getFileContentBufferStringToList();
		this.rightFileSourceContents = new ArrayList<String>(rightFileContents);
		this.rightViewContents = new ArrayList<String>();
		this.rightViewContents.add("");

		/* compare using FileComparator object */
		FileComparator fc = new FileComparator(leftFileSourceContents, rightFileSourceContents);
		this.leftFileSourceContents.add(0, "");
		this.rightFileSourceContents.add(0, "");
		this.leftDiffIndex = fc.getDiffLeft();
		this.rightDiffIndex = fc.getDiffRight();
		this.blocks = fc.getBlocks();

		leftPanel.setDiffIndices(leftDiffIndex);
		rightPanel.setDiffIndices(rightDiffIndex);
		leftPanel.setBlocks(blocks);
		rightPanel.setBlocks(blocks);
		setContentsForView();
		leftPanel.setAlignedFileContentBufferList(leftViewContents);
		rightPanel.setAlignedFileContentBufferList(rightViewContents);

	}

	private void setContentsForView() {
		// making leftViewContents
		for (int i = 1; i < leftDiffIndex.size(); i++) {
			int num = leftDiffIndex.get(i);
			if (num < 0)
				leftViewContents.add(leftFileSourceContents.get(num * (-1)));
			else
				leftViewContents.add("");
		}
		// making rightViewContents
		for (int i = 1; i < rightDiffIndex.size(); i++) {
			int num = rightDiffIndex.get(i);
			if (num > 0)
				rightViewContents.add(rightFileSourceContents.get(num));
			else
				rightViewContents.add("");
		}
	}

	private void setFileContents() {
		leftFileContents.clear();
		for (int i = 1; i < leftDiffIndex.size(); i++) {
			int num = leftDiffIndex.get(i);
			if (num < 0)
				leftFileContents.add(leftFileSourceContents.get(num * (-1)));
			else if (num > 0)
				leftFileContents.add(rightFileSourceContents.get(num));
			else
				continue;
		}

		// making rightViewContents
		rightFileContents.clear();
		for (int i = 1; i < rightDiffIndex.size(); i++) {
			int num = rightDiffIndex.get(i);
			if (num > 0)
				rightFileContents.add(rightFileSourceContents.get(num));
			else if (num < 0)
				rightFileContents.add(leftFileSourceContents.get(num * (-1)));
			else
				continue;
		}
	}

	public void traversePrevious() {
		--traverseCursor;

		leftPanel.setTraverseIndex(traverseCursor);
		rightPanel.setTraverseIndex(traverseCursor);

	}

	public void traverseNext() {
		++traverseCursor;

		leftPanel.setTraverseIndex(traverseCursor);
		rightPanel.setTraverseIndex(traverseCursor);
	}

	private void setThingsAfterMerge() {
		setFileContents();
		leftPanel.setBlocks(blocks);
		rightPanel.setBlocks(blocks);
		leftPanel.setAlignedFileContentBufferList(leftViewContents);
		rightPanel.setAlignedFileContentBufferList(rightViewContents);
		leftPanel.setDiffIndices(leftDiffIndex);
		rightPanel.setDiffIndices(rightDiffIndex);

		if (!this.getFlagNext() && (traverseCursor != blocks.size() - 1))
			traverseCursor--;

		leftPanel.setTraverseIndex(traverseCursor);
		rightPanel.setTraverseIndex(traverseCursor);
	}

	public void copyToLeft() {
		for (int i = blocks.get(traverseCursor)[0]; i <= blocks.get(traverseCursor)[1]; i++) {
			leftDiffIndex.set(i, rightDiffIndex.get(i));
			leftViewContents.set(i, rightViewContents.get(i));
		}
		blocks.remove(traverseCursor);

		setThingsAfterMerge();
	}

	public void copyToRight() {
		for (int i = blocks.get(traverseCursor)[0]; i <= blocks.get(traverseCursor)[1]; i++) {
			rightDiffIndex.set(i, leftDiffIndex.get(i));
			rightViewContents.set(i, leftViewContents.get(i));
		}
		blocks.remove(traverseCursor);
		setThingsAfterMerge();

	}

	public boolean getFlagPrevious() {
		return this.traverseCursor > 0;
	}

	public boolean getFlagNext() {
		return this.traverseCursor < blocks.size() - 1;
	}

	public int getTraverseCursor() {
		return this.traverseCursor;
	}

	public void setTraverseCursor(int move) {
		if (move == -1) {
			traversePrevious();
		} else if (move == +1) {
			traverseNext();
		}
	}


}