package assign3;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import java.awt.*;
import java.awt.event.*;


public class SudokuFrame extends JFrame {

	JCheckBox checkBox;
	String puzzleString;
	Sudoku puzzle;
	JTextArea textAreaPuzzle;
	JTextArea textAreaSolution;
	
	public SudokuFrame() {
		super("Sudoku Solver");

		textAreaPuzzle = new JTextArea(15, 25);
		textAreaSolution = new JTextArea(15, 30);
		
		textAreaPuzzle.setBorder(new TitledBorder("Puzzle"));
		textAreaSolution.setBorder(new TitledBorder("Solution"));
		
		textAreaPuzzle.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent docEvent) {
				updatePuzzleAndSolution(docEvent);
			}
			
			@Override
			public void insertUpdate(DocumentEvent docEvent) {
				updatePuzzleAndSolution(docEvent);
			}
			
			@Override
			public void changedUpdate(DocumentEvent docEvent) {	}
		});
		
		JPanel bottomPanel = new JPanel();
		JButton checkBttn = new JButton("Check");
		checkBox = new JCheckBox("Auto Check");
		checkBox.setSelected(true);
		
		checkBttn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				puzzle = null;
				puzzleString = textAreaPuzzle.getText();
				createNewSudokuPuzzle();
				solveInSolutionArea();
			}
		});
		
//		textAreaPuzzle.setText(
//				"\"3 7 0 0 0 0 0 8 0\"\n"
//				+ "\"0 0 1 0 9 3 0 0 0\",\n"
//				+ "\"0 4 0 7 8 0 0 0 3\",\n"
//				+ "\"0 9 3 8 0 0 0 1 2\",\n"
//				+ "\"0 0 0 0 4 0 0 0 0\",\n"
//				+ "\"5 2 0 0 0 6 7 9 0\",\n"
//				+ "\"6 0 0 0 2 1 0 4 0\",\n"
//				+ "\"0 0 0 5 3 0 9 0 0\",\n"
//				+ "\"0 3 0 0 0 0 0 5 1\"");
		
		setLayout(new BorderLayout(4, 4));
		
		add(textAreaPuzzle, BorderLayout.WEST);
		add(textAreaSolution, BorderLayout.EAST);
		
		bottomPanel.add(checkBttn);
		bottomPanel.add(checkBox);
		
		add(bottomPanel, BorderLayout.SOUTH);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationByPlatform(true);
		setVisible(true);
	}
	
	private void createNewSudokuPuzzle() {
		try {
			int[][] ints = Sudoku.textToGrid(puzzleString);
			puzzle = new Sudoku(ints);
		} catch (Exception e) {
			textAreaSolution.setText("Parsing Error");
		}
	}

	private void solveInSolutionArea() {
		textAreaSolution.removeAll();
		if (puzzle == null) {
			textAreaSolution.setText("Invalid Puzzle");
			return;
		}
		
		int count = puzzle.solve();
		textAreaSolution.setText(puzzle.getSolutionText() +
				"\nNumber of Solutions: " + count + 
				"\nElapsed time: " + puzzle.getElapsed() + "ms");
	}
	
	private void updatePuzzleAndSolution(DocumentEvent docEvent) {
		puzzle = null;
		if (checkBox.isSelected()) {
			try {
				Document doc = docEvent.getDocument();
				puzzleString = doc.getText(0, doc.getLength());
			} catch (BadLocationException e) { }
			
			createNewSudokuPuzzle();
			solveInSolutionArea();
		}
	}

	public static void main(String[] args) {
		// GUI Look And Feel
		// Do this incantation at the start of main() to tell Swing
		// to use the GUI LookAndFeel of the native platform. It's ok
		// to ignore the exception.
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) { }

		SudokuFrame frame = new SudokuFrame();
	}

}
