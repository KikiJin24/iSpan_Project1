package wedding;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InsertToSQL {

	private static final String JDBC_DRIVER = 
			"com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static final String DB_URL = 
			"jdbc:sqlserver://localhost:1433;databaseName=Test_j";
	private static final String USER = "sa";
	private static final String PASSWORD = "passw0rd";
	
	public static final String SQL_AREA = "INSERT INTO AREA VALUES (?,?)";
	
	
	
	public static void insertIntoArea(Map<String, String> area, Connection conn) {
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL_AREA);
			for (String AREA_CODE : area.keySet()) {
				pstmt.setInt(1, Integer.parseInt(AREA_CODE));
				pstmt.setString(2, area.get(AREA_CODE));
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	

	public static void batchInsertData(Map<String, String> area) {
		Connection conn = null;

		try {
			Class.forName(JDBC_DRIVER); 
			conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);

			insertIntoArea(area, conn);
			

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void doInsert() {
		
		
		Map<String, String> area = new HashMap<>();
		
		
		
		ParseCSV.parseCsv(area);
		
		System.out.printf("=================");
		
		InsertToSQL.batchInsertData(area);
		System.out.printf("=====done=====%n");
		
	}

}
