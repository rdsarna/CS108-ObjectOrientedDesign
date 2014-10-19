package assign3;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import assign3.Entry.Match;
import assign3.Entry.Population;


/**
 * Builds and displays a GUI through which users can 
 * search for and add data to the metropolis table in a back end
 * database. The data from the database is displayed as in a
 * JTable.
 * 
 * @author Ratul Sarna
 */
public class TableFrame extends JFrame {
	
	private DBTableModel model;
	private JTable table;
	
	private JButton addBttn;
	private JButton searchBttn;
	private JLabel metropolisLabel;
	private JTextField metropolisField;
	private JLabel continentLabel;
	private JTextField continentField;
	private JLabel populationLabel;
	private JTextField populationField;
	private JComboBox populationCriteria;
	private JComboBox matchCriteria;
	
	final static String POPULATION_LARGER = "Population Larger Than";
	final static String POPULATION_SMALLER = "Population Smaller Than";
	final static String POPULATION_EQUAL = "Population Equal To";
	final static String MATCH_EXACT = "Exact Match";
	final static String MATCH_PARTIAL = "Partial Match";
	
	public TableFrame() {
		super("Metropolis Viewer");
		
		model = new DBTableModel();
		
		table = new JTable(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		JScrollPane scrollpane = new JScrollPane(table);
		scrollpane.setPreferredSize(new Dimension(300, 400));
		add(scrollpane, BorderLayout.CENTER);
		
		/* Fields and labels to be added to the top part of the window */
		JPanel topPanel = new JPanel();
		add(topPanel, BorderLayout.NORTH);
		
		metropolisLabel = new JLabel("Metropolis:  ");
		metropolisField = new JTextField(17);
		continentLabel = new JLabel("Continent:  ");
		continentField = new JTextField(17);
		populationLabel = new JLabel("Population:  ");
		populationField = new JTextField(17);
		
		topPanel.add(metropolisLabel);
		topPanel.add(metropolisField);
		topPanel.add(continentLabel);
		topPanel.add(continentField);
		topPanel.add(populationLabel);
		topPanel.add(populationField);
		
		/* Buttons and ComboBoxes on the east side of the window */
		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
		add(eastPanel, BorderLayout.EAST);
		
		addBttn = new JButton("Add");
		searchBttn = new JButton("Search");
		eastPanel.add(addBttn);
		eastPanel.add(new JLabel(" "));
		eastPanel.add(searchBttn);
		eastPanel.add(new JLabel(" "));
		
		/* A panel for the ComboBoxes */
		JPanel comboBoxPanel = new JPanel();
		String[] populationOptions = new String[] {POPULATION_LARGER, POPULATION_SMALLER, POPULATION_EQUAL};
		populationCriteria = new JComboBox<>(populationOptions);
		populationCriteria.setPreferredSize(new Dimension(190, 28));
		
		String[] matchOptions = new String[] {MATCH_EXACT, MATCH_PARTIAL};
		matchCriteria = new JComboBox<>(matchOptions);
		matchCriteria.setPreferredSize(new Dimension(190, 28));
		
		comboBoxPanel.setBorder(new TitledBorder("Search Options"));
		comboBoxPanel.add(populationCriteria);
		comboBoxPanel.add(matchCriteria);
		comboBoxPanel.setPreferredSize(new Dimension(200, 80));
		eastPanel.add(comboBoxPanel);
		
		addBttn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Entry entry = getNewEntry();
				model.addRow(entry);
			}
		});
		
		searchBttn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Entry entry = getNewEntry();
				model.getNewDataFromDB(entry);
			}
		});
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	protected Entry getNewEntry() {
		String metropolis = metropolisField.getText();
		String continent = continentField.getText();
		String population = populationField.getText();
		Entry.Population populationCrit = getPopulationCriteria(populationCriteria.getSelectedItem());
		Entry.Match matchCrit = matchCriteria.getSelectedItem().equals(MATCH_EXACT) ? Match.EXACT : Match.PARTIAL;
		return new Entry(metropolis, continent, population, populationCrit, matchCrit);
	}

	private Population getPopulationCriteria(Object selectedItem) {
		switch ((String)selectedItem) {
		case POPULATION_LARGER: return Population.LARGER;
		case POPULATION_SMALLER: return Population.SMALLER;
		case POPULATION_EQUAL: return Population.EQUAL;
		default: return null;
		}
	}

	
	public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		new TableFrame();
	}

}
