package database;

import java.sql.*;

public class TestConnection {

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Connected to MySQL.");
		} catch (ClassNotFoundException e) {
			System.out.println("Cannot connec to MySQL.");
		}
	}

}
