package assign3;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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
public class DataBaseConnection {

	public static final String MYSQL_USERNAME = "root";
	public static final String MYSQL_PASSWORD = "";
	public static final String MYSQL_DATABASE_SERVER = "localhost";
	public static final String MYSQL_DATABASE_NAME = "c_cs108_ratulsarna";
	private static final String TABLE = "metropolises";
	
	private static final String DEFAULT_STATEMENT = "SELECT * FROM " + TABLE;

	private final Connection connection = getConnection();
	private final ResultSetMetaData metadata = getMetadata();
	private final List<String> columnNames = getColumnNames();
	
	
	private void logException(String message, Exception e) {
		Logger logger = Logger.getAnonymousLogger();
		logger.log(Level.SEVERE, message, e);
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
			PreparedStatement stmt = 
					connection.prepareStatement(entry.getQueryStatement(TABLE));
			ResultSet rs = stmt.executeQuery();
			
			convertResultSetToList(rs, data);
		} catch (SQLException e) {
			logException("Error retrieving data", e);
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
			String insertStatement = entry.getInsertStatement(TABLE);
			
			if (insertStatement == null) return rowIndex;
			
			PreparedStatement stmt = 
					connection.prepareStatement(entry.getQueryStatement(TABLE));
			stmt.executeUpdate();
			ResultSet rs = stmt.executeQuery(DEFAULT_STATEMENT);
			rs.last();
			
			rowIndex = rs.getRow();
			
			for (int i = 1; i <= metadata.getColumnCount(); i++)
				rowList.add(rs.getString(i));
			
			data.add(rowList);
			
			rs.first();
		} catch (SQLException e) {
			logException("Error adding data", e);
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
	

	private List<String> getColumnNames() {
		List<String> result = new ArrayList<>();
		try {
			int count = metadata.getColumnCount();
			for (int i = 1; i <= count; i++)
				result.add(metadata.getColumnLabel(i));
		} catch (SQLException e) {
			logException("Error retrieving data", e);
		}
		return result;
	}


	private ResultSetMetaData getMetadata() {
		try {
			PreparedStatement stmt = connection.prepareStatement("USE " + MYSQL_DATABASE_NAME);
			stmt.executeQuery();
			stmt = connection.prepareStatement(DEFAULT_STATEMENT);
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData metadata = rs.getMetaData();
			return metadata;
			
		} catch (SQLException e) {
			logException("Error retrieving data", e);
		}
		return null;
	}


	private Connection getConnection() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://" + MYSQL_DATABASE_SERVER, MYSQL_USERNAME, MYSQL_PASSWORD);
		} catch (ClassNotFoundException e) {
			logException("Could not connect to the Database", e);
		} catch (SQLException e) {
			logException("Could not connect to the Database", e);
		}
		return con;
	}
	
}
