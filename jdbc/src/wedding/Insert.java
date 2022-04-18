package wedding;

import java.sql.*;

// Insert one employee
public class Insert {
	private static final String DB_URL = 
			"jdbc:sqlserver://localhost:1433;databaseName=Wedding";
	private static final String USER = "sa";
	private static final String PASSWORD = "passw0rd";
	
	private static final String SQL1 =
			"INSERT INTO AREA (AREA_CODE, AREA_NAME) VALUES (?, ?)";
	private static final String SQL2 =
			"INSERT INTO MARRY_CNT (AREA_CODE, YEAR, JAN_CNT, FEB_CNT ,MAR_CNT, APR_CNT, MAY_CNT, JUN_CNT, JUL_CNT, AUG_CNT, SEP_CNT, OCT_CNT, NOV_CNT, DEC_CNT, MARRY_FLAG, OID ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String SQL3 =
			"INSERT INTO MARRY_CNT (AREA_CODE, YEAR, JAN_CNT, FEB_CNT ,MAR_CNT, APR_CNT, MAY_CNT, JUN_CNT, JUL_CNT, AUG_CNT, SEP_CNT, OCT_CNT, NOV_CNT, DEC_CNT, MARRY_FLAG, OID ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	public static void main(String[] args) {
		Connection conn = null;
		try {     
			conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			
			PreparedStatement pstmt = conn.prepareStatement(SQL1);
			pstmt.setString(1, "14");
			pstmt.setString(2, "紅燈區");
			
			int count = pstmt.executeUpdate();
			pstmt.clearParameters();
			
			pstmt = conn.prepareStatement(SQL2);
			pstmt.setString(1, "14");
			pstmt.setInt(2, 110);
			pstmt.setInt(3, 10);
			pstmt.setInt(4, 20);
			pstmt.setInt(5, 30);
			pstmt.setInt(6, 40);
			pstmt.setInt(7, 50);
			pstmt.setInt(8, 60);
			pstmt.setInt(9, 70);
			pstmt.setInt(10, 80);
			pstmt.setInt(11, 90);
			pstmt.setInt(12, 100);
			pstmt.setInt(13, 110);
			pstmt.setInt(14, 120);
			pstmt.setString(15, "Y");
			pstmt.setString(16, "11014Y");
			
			count = pstmt.executeUpdate();
			pstmt.clearParameters();
			
			pstmt = conn.prepareStatement(SQL3);
			pstmt.setString(1, "14");
			pstmt.setInt(2, 110);
			pstmt.setInt(3, 5);
			pstmt.setInt(4, 15);
			pstmt.setInt(5, 25);
			pstmt.setInt(6, 35);
			pstmt.setInt(7, 45);
			pstmt.setInt(8, 55);
			pstmt.setInt(9, 65);
			pstmt.setInt(10, 75);
			pstmt.setInt(11, 85);
			pstmt.setInt(12, 95);
			pstmt.setInt(13, 15);
			pstmt.setInt(14, 25);
			pstmt.setString(15, "N");
			pstmt.setString(16, "11014N");
			
		
			count = pstmt.executeUpdate();
			System.out.println("insert count = " + count);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch(SQLException e) { 
					e.printStackTrace();
				}
		}
	}
}// end of class InsertDemo
