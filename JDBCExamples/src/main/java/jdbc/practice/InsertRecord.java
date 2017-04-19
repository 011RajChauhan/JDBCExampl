package jdbc.practice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class InsertRecord {
	
	/*Statement interfaces method
	execute() used for all query type, boolean return type true means ResultSet and false means 0 or any positive integer
	executeQuery() used for select queries only return type ResultSet
	executeUpdate() used of DML like update, insert, delete and DML like create, alter etc, return type 0 or positive integer*/
	
	private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/mylocaldb";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "12345";
	
	private static final DateFormat dateFormat = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm:ss");

	public static void main(String[] argv) {

		try {

			insertRecordIntoDbUserTable();

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}

	}

	private static void insertRecordIntoDbUserTable() throws SQLException {

		Connection dbConnection = null;
		Statement statement = null;

		//insert query
		/*String sql = "INSERT INTO DBUSER"
				+ "(USERNAME, CREATED_BY, CREATED_DATE) " + "VALUES"
				+ "('sandeep','sandeep', '" + getCurrentTimeStamp()+"'),"
						+ "('rajan','rajan', '" + getCurrentTimeStamp()+"'),"
								+ "('bhagat','bhagat', '" + getCurrentTimeStamp()+"'),"
										+ "('ajay','ajay', '" + getCurrentTimeStamp()+"')";*/
		
		
		//update query
		//String sql = "UPDATE DBUSER SET USERNAME = 'RAJAN CHAUHAN' WHERE USER_ID = 1";

		//delete query
		//String sql = "DELETE FROM DBUSER WHERE USER_ID = 1";
		
		//select query
		String sql = "SELECT USERNAME FROM DBUSER";
		
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();

			System.out.println(sql);

			// execute INSERT,UPDATE,DELETE,CREATE AND ALTER  SQL stetement using execute() and executeUpdate() from statement interface
			//statement.executeUpdate(sql);

			//execute SELECT using executeQuery() from statement interface
			ResultSet resultSet = statement.executeQuery(sql);
			while(resultSet.next()){
				System.out.println(resultSet.getString("USERNAME"));
			}
			System.out.println("query successfully executed...");

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (statement != null) {
				statement.close();
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

	private static String getCurrentTimeStamp() {

		java.util.Date today = new java.util.Date();
		return dateFormat.format(today.getTime());

	}
}
