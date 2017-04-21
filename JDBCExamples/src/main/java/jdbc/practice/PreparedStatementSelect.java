package jdbc.practice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PreparedStatementSelect {
	
	private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/mylocaldb";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "12345";
	
	
	
	public static void main(String[] argv) {

		try {

			selectRecords();

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}

	}

	private static void selectRecords() throws SQLException {

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String sql = "SELECT * FROM DBUSER WHERE USERNAME = ? ";
		

		try {
			dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement(sql);

			
			preparedStatement.setString(1, "A");
			
			// execute insert SQL stetement
			ResultSet rs = preparedStatement.executeQuery();

			while(rs.next()) {
				System.out.println(rs.getString("username"));
			}

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}

	}

	private static Connection getDBConnection() {

		Connection dbConnection = null;

		try {

			Class.forName(DB_DRIVER);

		} catch (ClassNotFoundException e) {

			System.out.println(e.getMessage());

		}

		try {

			dbConnection = DriverManager.getConnection(
                            DB_URL, DB_USER,DB_PASSWORD);
			return dbConnection;

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}

		return dbConnection;

	}

	private static java.sql.Timestamp getCurrentTimeStamp() {

		java.util.Date today = new java.util.Date();
		return new java.sql.Timestamp(today.getTime());

	}

}
