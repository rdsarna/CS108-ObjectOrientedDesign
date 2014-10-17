package database;

import java.sql.*;

public class ConnectDB {
	
	static String account = MyDBInfo.MYSQL_USERNAME;
	static String password = MyDBInfo.MYSQL_PASSWORD;
	static String server = MyDBInfo.MYSQL_DATABASE_SERVER;
	static String database = MyDBInfo.MYSQL_DATABASE_NAME;

	Connection connection;
	
	public ConnectDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Cannot connect to MySQL.");
		}
	}
	
	public void connectToDB() {
		try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://" + server, account, password);
			System.out.println("Connected to Database.");
		} catch (SQLException e) {
			System.out.println("Cannot connect to Database.");
		}
	}
	
	public void execSQL() {
		try {
			Statement stmt = connection.createStatement();
			stmt.executeQuery("USE " + database);
			ResultSet rs = stmt.executeQuery("SELECT * FROM metropolises");
			
			while (rs.next()) {
				String name = rs.getString("metropolis");
				long pop = rs.getLong("population");
				System.out.println(name + "\t" + pop);
			}
			connection.close();
		} catch (SQLException e) {
			System.out.println("Error executing SQL");
		}
	}
	
	public static void main(String[] args) {
		ConnectDB conn = new ConnectDB();
		conn.connectToDB();
		conn.execSQL();
	}

}
