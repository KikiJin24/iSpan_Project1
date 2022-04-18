package wedding;

import java.sql.*;

public class Query1 {
	private static final String DB_URL = 
			"jdbc:sqlserver://localhost:1433;databaseName=Wedding";
	private static final String USER = "sa";
	private static final String PASSWORD = "passw0rd";
	
	private static final String SQL =
			"SELECT C.AREA_NAME, C.AREA_CODE, C.TOTAL FROM (\r\n"
			+ "    SELECT\r\n"
			+ "        ROW_NUMBER() OVER(ORDER BY (JAN_CNT+FEB_CNT+MAR_CNT+APR_CNT+MAY_CNT+JUN_CNT+JUL_CNT+AUG_CNT+SEP_CNT+OCT_CNT+NOV_CNT+DEC_CNT) DESC) AS ROWID,\r\n"
			+ "        A.AREA_NAME, \r\n"
			+ "        B.AREA_CODE,\r\n"
			+ "        (JAN_CNT+FEB_CNT+MAR_CNT+APR_CNT+MAY_CNT+JUN_CNT+JUL_CNT+AUG_CNT+SEP_CNT+OCT_CNT+NOV_CNT+DEC_CNT)AS TOTAL\r\n"
			+ "    FROM AREA A \r\n"
			+ "    LEFT JOIN MARRY_CNT B ON A.AREA_CODE = B.AREA_CODE\r\n"
			+ "    WHERE B.MARRY_FLAG = ?\r\n"
			+ ") C\r\n"
			+ "WHERE C.ROWID = 1";
	
	public static void main(String[] args) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);			
				
			String MARRY_FLAG = "N";
			PreparedStatement stmt = conn.prepareStatement(SQL);
			stmt.setString(1, MARRY_FLAG);
			ResultSet rs = stmt.executeQuery();

			while(rs.next()) {
				System.out.print("AREA_NAME = " + rs.getString("AREA_NAME"));
				System.out.print(", AREA_CODE = " + rs.getString("AREA_CODE"));
				System.out.println(", TOTAL = " + rs.getString("TOTAL"));
			}
			rs.close();
			stmt.close();
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
}
