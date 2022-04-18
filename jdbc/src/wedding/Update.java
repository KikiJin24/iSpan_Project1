package wedding;

import java.sql.*;

// Update employee data
public class Update {
	private static final String DB_URL = 
			"jdbc:sqlserver://localhost:1433;databaseName=Wedding";
	private static final String USER = "sa";
	private static final String PASSWORD = "passw0rd";
	
	private static final String SQL1 =
			"UPDATE AREA SET AREA_NAME=? WHERE AREA_CODE=?";
	
	private static final String SQL2 =
			"UPDATE MARRY_CNT SET OCT_CNT=? WHERE OID=?";
	
	public static void main(String[] args) {
		Connection conn = null;
		try {     
			conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			
			PreparedStatement pstmt = conn.prepareStatement(SQL1);
			pstmt.setString(1, "綠燈區");
			pstmt.setString(2, "14");
			int count = pstmt.executeUpdate();
			pstmt.clearParameters();
			
			pstmt = conn.prepareStatement(SQL2);
			pstmt.setInt(1, 120);
			pstmt.setString(2, "11014N");
			
			
			count = pstmt.executeUpdate();
			System.out.println("update count = " + count);
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
	}// end of main()
}// end of class UpdateDemo
