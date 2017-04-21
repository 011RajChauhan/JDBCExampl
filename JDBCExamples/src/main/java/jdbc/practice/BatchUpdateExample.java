package jdbc.practice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class BatchUpdateExample {

	private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/mylocaldb";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "12345";
	
	private static final DateFormat dateFormat = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm:ss");
	
	public static void main( String [] args) {
		
		try {
			executeBatchUpdate();
		}catch(SQLException se) {
			se.getMessage();
		}
	}

	private static void executeBatchUpdate() throws SQLException {
		
		Connection connection = null;
		Statement statement = null;
		
		String sql1 = "INSERT INTO DBUSER(USERNAME, CREATED_BY, CREATED_DATE) VALUES ('d','A', '" + getCurrentTimeStamp()+"')";
		String sql2 = "INSERT INTO DBUSER(USERNAME, CREATED_BY, CREATED_DATE) VALUES ('d','B', '" + getCurrentTimeStamp()+"')";
		String sql3 = "INSERT INTO DBUSER(USERNAME, CREATED_BY, CREATED_DATE) VALUES ('f','C', '" + getCurrentTimeStamp()+"')";
		
		try{
			connection = getConnection();
			statement = connection.createStatement();
			
			connection.setAutoCommit(false);
			
			statement.addBatch(sql1);
			statement.addBatch(sql2);
			statement.addBatch(sql3);
			
			statement.executeBatch();
			
			connection.commit();
			
			System.out.println("batch updated done...");
		}catch(SQLException se) {
			se.getMessage();
		}finally{
			if(statement!=null) {
				statement.close();
			}
			if(connection!=null) {
				connection.close();
			}
		}
	}
	
	private static String getCurrentTimeStamp() {
		java.util.Date today = new java.util.Date();
		return dateFormat.format(today.getTime());
	}

	private static Connection getConnection() {
		
		Connection connection = null;
		
		try{
			Class.forName(DB_DRIVER);
		}catch(ClassNotFoundException cnfe) {
			cnfe.getMessage();
		}
		
		try{
			connection = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
			return connection;
		}catch(SQLException se) {
			se.getMessage();
		}
		
		return connection;
	}
}
