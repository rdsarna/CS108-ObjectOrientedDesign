package assign3;

import javax.swing.table.AbstractTableModel;

import java.sql.*;
import java.util.*;

/**
 * A Table Model implementation to be used in a JTable context.
 * Uses ArrayList of rows, where each row is itself an ArrayList
 * of the data in that row.
 * 
 * The data stored in this class is retrieved from the DataClass
 * which in turn gets it from a back end SQL database.
 */
public class DBTableModel extends AbstractTableModel {
	
	private List<String> colNames;	// defines the number of cols
	private List<List<String>> data;	// one List for each row
	private DataBaseConnection dataFromDB;
	
	public DBTableModel() {
		dataFromDB = new DataBaseConnection();
		colNames = dataFromDB.retrieveColumnNames();
		data = new ArrayList<>();
	}


	/*
	 Basic getXXX methods required by a class implementing TableModel
	*/
	
	/**
	 * Returns the name of the column at columnIndex. This is used to 
	 * initialize the table's column header name. Note: this name does 
	 * not need to be unique; two columns in a table can have the same name.
	 * @param the column being queried
	 * @return a string containing the default name of column
	 */
	public String getColumnName(int col) {
		return colNames.get(col);
	}
	
	/**
	 * Returns the number of columns in the model. A JTable uses 
	 * this method to determine how many columns it should create 
	 * and display by default.
	 * @return the number of columns in the model
	 */
	public int getColumnCount() {
		return colNames.size();
	}
	
	/**
	 * Returns the number of rows in the model. A JTable uses this 
	 * method to determine how many rows it should display.
	 * @return the number of rows in the model
	 */
	public int getRowCount() {
		return data.size();
	}
	
	/**
	 * Returns the value for the cell at col and row.
	 * @param row the row whose value is to be queried
	 * @param col the column whose value is to be queried
	 * @return the value Object at the specified cell
	 */
	public Object getValueAt(int row, int col) {
		List<String> rowList = data.get(row);
		Object result = null;
		if (col<rowList.size()) {
			result = rowList.get(col);
		}
		return result;
	}
	
	
	/**
	 * Returns true if a cell should be editable in the table otherwise false.
	 * @param row the row whose value to be queried
	 * @param col the column whose value to be queried
	 * @return true if the cell is editable
	 */
	public boolean isCellEditable(int row, int col) {
		return false;
	}
	
	/**
	 * Retrieves new data from the back end database by calling on the 
	 * DataClass and updates the data in "this" object. The data retrieved
	 * is based on the properties of the given Entry object.
	 * @param entry The properties of this entry object relay what data needs
	 * to be retrieved
	 */
	public void getNewDataFromDB(Entry entry) {
		dataFromDB.retrieveData(entry, data);
		fireTableDataChanged();
	}

	
	/**
	 * Adds a new row to the back end database by calling on the DataClass.
	 * The data added in the new row is based on the properties of the given
	 * Entry object.
	 * @param entry The properties of this entry object relay what data needs
	 * to be stored in the added row.
	 * @return The row index of the table in the back end DB where the new row
	 * is added.
	 */
	public int addRow(Entry entry) {
		int rowIndex = dataFromDB.insertData(entry, data);
		fireTableDataChanged();
		return rowIndex;
	}
	
}
