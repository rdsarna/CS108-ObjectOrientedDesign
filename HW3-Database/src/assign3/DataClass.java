package assign3;

import java.sql.*;
import java.util.*;

public class DataClass {

	static String account = MyDBInfo.MYSQL_USERNAME;
	static String password = MyDBInfo.MYSQL_PASSWORD;
	static String server = MyDBInfo.MYSQL_DATABASE_SERVER;
	static String database = MyDBInfo.MYSQL_DATABASE_NAME;
	static String table = "metropolises";

	private Connection connection;
	private Statement stmt;
	private ResultSet rs;
	private ResultSetMetaData metadata;
	
	public DataClass() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(
					"jdbc:mysql://" + server, account, password);
			stmt = connection.createStatement();
			stmt.executeQuery("USE " + database);
		} catch (ClassNotFoundException e) {
			e.getStackTrace();
		} catch (SQLException se) {
			se.getStackTrace();
		}
	}
	
	public List<String> retrieveDataAndColumns(Entry entry, List<String> columnNames) {
		List<String> result = new ArrayList<>();
		try {
			rs = stmt.executeQuery(entry.getQueryStatement(table));
			metadata = rs.getMetaData();
		} catch (SQLException se) {
			se.getStackTrace();
		}
		result = convertResultSetToList(rs);
		
		
	}

	private static List<String> convertResultSetToList(ResultSet rs) {
		
		return null;
	}
	
}
