package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
	private static final String DB_USERNAME = "postgres";
	private static final String DB_PASSWORD = "38698";
	private static final String DB_URL = "jdbc:postgresql://localhost:5433/java_lab";

	public static Connection connectToDataBase() throws SQLException {
		return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
	}

}
