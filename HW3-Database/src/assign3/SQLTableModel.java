/**
 *
 * @author Ratul Sarna
 */
package assign3;

import javax.swing.table.AbstractTableModel;
import java.sql.*;

/**
 * 
 *
 */
public class SQLTableModel extends AbstractTableModel {
	
	static String account = MyDBInfo.MYSQL_USERNAME;
	static String password = MyDBInfo.MYSQL_PASSWORD;
	static String server = MyDBInfo.MYSQL_DATABASE_SERVER;
	static String database = MyDBInfo.MYSQL_DATABASE_NAME;
	static String table = "metropolises";
	
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	private ResultSetMetaData metadata;
	
	public SQLTableModel() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			conn = DriverManager.getConnection(
					"jdbc:mysql://" + server, account, password);
			stmt = conn.createStatement();
			stmt.executeQuery("USE " + database);
		} catch (SQLException se) {
			se.getStackTrace();
		} catch (ClassNotFoundException ce) {
			ce.getStackTrace();
		}
	}
	
	@Override
	public String getColumnName(int col) {
		
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		int rowCount = 0;
		try {
			rs.last();
			rowCount = rs.getRow();
			rs.first();
		} catch (SQLException se) {
			se.getStackTrace();
		}
		return rowCount;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		int colCount = 0;
		try {
			colCount = metadata.getColumnCount();
		} catch (SQLException se) {
			se.getStackTrace();
		}
		return colCount;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object result = null;
		try {
			rs.absolute(rowIndex);
			result = rs.getString(columnIndex);
			rs.first();
		} catch (SQLException se) {
			se.getStackTrace();
		}
		return result;
	}

}
