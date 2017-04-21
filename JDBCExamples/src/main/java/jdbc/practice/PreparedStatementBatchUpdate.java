package jdbc.practice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PreparedStatementBatchUpdate {
	
	private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/mylocaldb";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "12345";
	
	
	
	public static void main(String[] argv) {

		try {

			updateRecord();

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}

	}

	private static void updateRecord() throws SQLException {

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String sql = "UPDATE DBUSER SET USERNAME = ? WHERE USER_ID = ?";
		String sql2 = "UPDATE DBUSER SET USERNAME = ? WHERE USER_ID = ?";

		try {
			dbConnection = getDBConnection();
			
			dbConnection.setAutoCommit(false);
			
			preparedStatement = dbConnection.prepareStatement(sql);

			
			preparedStatement.setString(1, "sandeep chauhan");
			preparedStatement.setInt(2, 1);
			
			preparedStatement.addBatch();
			
			preparedStatement.setString(1, "rajan chauhan");
			preparedStatement.setInt(2, 2);
			
			preparedStatement.addBatch();
			
			preparedStatement.executeBatch();
			
			dbConnection.commit();
			
			System.out.println("batch upate successfully...");

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
