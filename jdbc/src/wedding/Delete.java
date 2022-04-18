package wedding;

import java.sql.*;

// delete a employee data
public class Delete {
	private static final String DB_URL = 
			"jdbc:sqlserver://localhost:1433;databaseName=Wedding";
	private static final String USER = "sa";
	private static final String PASSWORD = "passw0rd";
	
	private static final String SQL1 =
			"DELETE MARRY_CNT WHERE AREA_CODE=?";
	private static final String SQL2 =
			"DELETE AREA WHERE AREA_CODE=?";
	
		
	public static void main(String[] args) {
		Connection conn = null;
		try {     
			conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			
			PreparedStatement pstmt = conn.prepareStatement(SQL1);
			pstmt.setString(1, "14");
			int count = pstmt.executeUpdate();
			pstmt.clearParameters();
			
			pstmt = conn.prepareStatement(SQL2);
			pstmt.setString(1, "14");
			count = pstmt.executeUpdate();
			
			
			
			
			System.out.println("delete count = " + count);
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
}// end of class DeleteDemo
