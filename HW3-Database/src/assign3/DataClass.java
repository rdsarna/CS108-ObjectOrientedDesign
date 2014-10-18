package assign3;

import java.sql.*;
import java.util.*;

public class DataClass {

	static String account = MyDBInfo.MYSQL_USERNAME;
	static String password = MyDBInfo.MYSQL_PASSWORD;
	static String server = MyDBInfo.MYSQL_DATABASE_SERVER;
	static String database = MyDBInfo.MYSQL_DATABASE_NAME;
	static String table = "metropolises";
	
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
	 */
	public void insertData(Entry entry, List<List<String>> data) {
		data.clear();
		List<String> list = new ArrayList<>();
		try {
			String insertStatement = entry.getInsertStatement(table);
			if (insertStatement == null) return;
			stmt.executeUpdate(insertStatement);
			rs = stmt.executeQuery(DEFAULT_STATEMENT);
			rs.last();
			for (int i = 1; i <= metadata.getColumnCount(); i++)
				list.add(rs.getString(i));
			data.add(list);
			rs.first();
		} catch (SQLException se) {
			System.out.println("Insert Error");
			se.getStackTrace();
		}
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
