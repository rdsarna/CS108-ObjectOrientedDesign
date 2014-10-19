package assign3;

import java.sql.*;
import java.util.*;

/**
 * A class to retrieve data from a back end SQL database.
 * Used JDBC as the connector and a MySQL database as the
 * source of the data.
 * This class doesn't store the data itself. Instead, it only
 * retrieves the data and passes it to any class that calls
 * the appropriate methods.
 * 
 * @author Ratul
 */
public class DataClass {

	private static String account = MyDBInfo.MYSQL_USERNAME;
	private static String password = MyDBInfo.MYSQL_PASSWORD;
	private static String server = MyDBInfo.MYSQL_DATABASE_SERVER;
	private static String database = MyDBInfo.MYSQL_DATABASE_NAME;
	private static String table = "metropolises";
	
	private static final String DEFAULT_STATEMENT = "SELECT * FROM " + table;

	private Connection connection;
	private Statement stmt;
	private ResultSet rs;
	private ResultSetMetaData metadata;
	private List<String> columnNames;
	
	
	/**
	 * Constructs a DataClass object and connects to the back end 
	 * database.
	 */
	public DataClass() {
		columnNames = new ArrayList<>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(
					"jdbc:mysql://" + server, account, password);
			stmt = connection.createStatement();
			stmt.executeQuery("USE " + database);
			
			rs = stmt.executeQuery(DEFAULT_STATEMENT);
			metadata = rs.getMetaData();
			int count = metadata.getColumnCount();
			for (int i = 1; i <= count; i++) {
				columnNames.add(metadata.getColumnLabel(i));
			}
			
		} catch (ClassNotFoundException e) {
			System.out.println("Class Exception");
			e.getStackTrace();
		} catch (SQLException se) {
			System.out.println("SQL Exception");
			se.getStackTrace();
		}
	}
	
	
	/**
	 * Retrieves data from the database based on the given Entry object.
	 * Uses the properties of Entry object to determine the conditions for
	 * the retrieval query. The data retrieved is stored in the given List
	 * "data". The given entry should not be null.
	 * @param entry The object that holds information about conditions to be used to 
	 * query the database
	 */
	public void retrieveData(Entry entry, List<List<String>> data) {
		/* Clear the existing data to get new data based on the current query */
		data.clear();
		
		try {
			rs = stmt.executeQuery(entry.getQueryStatement(table));
			
			convertResultSetToList(rs, data);
		} catch (SQLException se) {
			System.out.println("SQL Exception");
			se.getStackTrace();
		}
	}
	
	
	/**
	 * Inserts a row into the table in the database based on the given Entry
	 * object. The data inserted is based on the properties of the Entry object.
	 * The given List of rows "data" is cleared and the newly inserted row is stored
	 * in it. 
	 * a List of Strings.
	 * @param entry The object based on which a new row is inserted
	 * @return returns the row index where the new row is added in the table.
	 * Or -1 if row is not inserted due to invalid information given
	 */
	public int insertData(Entry entry, List<List<String>> data) {
		int rowIndex = -1;
		
		/* Clear the existing data to get new data based on the current query */
		data.clear();
		/* List to store the single data in the row added */
		List<String> rowList = new ArrayList<>();
		
		try {
			String insertStatement = entry.getInsertStatement(table);
			
			if (insertStatement == null) return rowIndex;
			
			stmt.executeUpdate(insertStatement);
			rs = stmt.executeQuery(DEFAULT_STATEMENT);
			rs.last();
			
			rowIndex = rs.getRow();
			
			for (int i = 1; i <= metadata.getColumnCount(); i++)
				rowList.add(rs.getString(i));
			
			data.add(rowList);
			
			rs.first();
		} catch (SQLException se) {
			System.out.println("Insert Error");
			se.getStackTrace();
		}
		
		return rowIndex;
	}
	
	
	/**
	 * Returns the column names from the database as a List of Strings 
	 * @return names of the column
	 */
	public List<String> retrieveColumnNames() {
		return columnNames;
	}

	/* Converts the given ResultSet to a List of rows and the given list points to it.
	 * The given list should ideally be empty before this method is called. */
	private void convertResultSetToList(ResultSet rs, List<List<String>> list) 
			throws SQLException {
		while (rs.next()) {
			List<String> row = new ArrayList<>();
			for (int i = 1; i <= metadata.getColumnCount(); i++)
				row.add(rs.getString(i));
			list.add(row);
		}
	}
	
}
