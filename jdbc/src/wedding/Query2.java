package wedding;

import java.sql.*;

public class Query2 {
	private static final String DB_URL = 
			"jdbc:sqlserver://localhost:1433;databaseName=Wedding";
	private static final String USER = "sa";
	private static final String PASSWORD = "passw0rd";
	
	private static final String SQL =
			"WITH temp (CNT_MONTH, CNT_SUM, MARRY_FLAG) AS (\r\n"
			+ "    SELECT '一月' AS CNT_MONTH, SUM(JAN_CNT) AS CNT_SUM, MARRY_FLAG FROM MARRY_CNT GROUP BY MARRY_FLAG\r\n"
			+ "    UNION\r\n"
			+ "    SELECT '二月' AS CNT_MONTH, SUM(FEB_CNT) AS CNT_SUM, MARRY_FLAG FROM MARRY_CNT GROUP BY MARRY_FLAG\r\n"
			+ "    UNION\r\n"
			+ "    SELECT '三月' AS CNT_MONTH, SUM(MAR_CNT) AS CNT_SUM, MARRY_FLAG FROM MARRY_CNT GROUP BY MARRY_FLAG\r\n"
			+ "    UNION\r\n"
			+ "    SELECT '四月' AS CNT_MONTH, SUM(APR_CNT) AS CNT_SUM, MARRY_FLAG FROM MARRY_CNT GROUP BY MARRY_FLAG\r\n"
			+ "    UNION\r\n"
			+ "    SELECT '五月' AS CNT_MONTH, SUM(MAY_CNT) AS CNT_SUM, MARRY_FLAG FROM MARRY_CNT GROUP BY MARRY_FLAG\r\n"
			+ "    UNION\r\n"
			+ "    SELECT '六月' AS CNT_MONTH, SUM(JUN_CNT) AS CNT_SUM, MARRY_FLAG FROM MARRY_CNT GROUP BY MARRY_FLAG\r\n"
			+ "    UNION\r\n"
			+ "    SELECT '七月' AS CNT_MONTH, SUM(JUL_CNT) AS CNT_SUM, MARRY_FLAG FROM MARRY_CNT GROUP BY MARRY_FLAG\r\n"
			+ "    UNION\r\n"
			+ "    SELECT '八月' AS CNT_MONTH, SUM(AUG_CNT) AS CNT_SUM, MARRY_FLAG FROM MARRY_CNT GROUP BY MARRY_FLAG\r\n"
			+ "    UNION\r\n"
			+ "    SELECT '九月' AS CNT_MONTH, SUM(SEP_CNT) AS CNT_SUM, MARRY_FLAG FROM MARRY_CNT GROUP BY MARRY_FLAG\r\n"
			+ "    UNION\r\n"
			+ "    SELECT '十月' AS CNT_MONTH, SUM(OCT_CNT) AS CNT_SUM, MARRY_FLAG FROM MARRY_CNT GROUP BY MARRY_FLAG\r\n"
			+ "    UNION\r\n"
			+ "    SELECT '十一月' AS CNT_MONTH, SUM(NOV_CNT) AS CNT_SUM, MARRY_FLAG FROM MARRY_CNT GROUP BY MARRY_FLAG\r\n"
			+ "    UNION\r\n"
			+ "    SELECT '十二月' AS CNT_MONTH, SUM(DEC_CNT) AS CNT_SUM, MARRY_FLAG FROM MARRY_CNT GROUP BY MARRY_FLAG\r\n"
			+ ")\r\n"
			+ "SELECT\r\n"
			+ "    CASE B.MARRY_FLAG WHEN 'Y' THEN '結婚:' ELSE '離婚:' END + B.CNT_MONTH AS '最多月份',\r\n"
			+ "    CASE B.MARRY_FLAG WHEN 'Y' THEN '結婚:' ELSE '離婚:' END + A.CNT_MAX + '對' AS '最多數量',\r\n"
			+ "    CASE C.MARRY_FLAG WHEN 'Y' THEN '結婚:' ELSE '離婚:' END + C.CNT_MONTH AS '最少月份',\r\n"
			+ "    CASE C.MARRY_FLAG WHEN 'Y' THEN '結婚:' ELSE '離婚:' END + A.CNT_MIN + '對' AS '最少數量'\r\n"
			+ "FROM (\r\n"
			+ "    SELECT\r\n"
			+ "        CAST(MAX(CNT_SUM) AS NVARCHAR(5)) AS CNT_MAX,\r\n"
			+ "        CAST(MIN(CNT_SUM) AS NVARCHAR(5)) AS CNT_MIN,\r\n"
			+ "        MARRY_FLAG\r\n"
			+ "    FROM temp\r\n"
			+ "    GROUP BY MARRY_FLAG\r\n"
			+ "    HAVING MARRY_FLAG = ?\r\n"
			+ ") A\r\n"
			+ "INNER JOIN temp B ON A.CNT_MAX = B.CNT_SUM\r\n"
			+ "INNER JOIN temp C ON A.CNT_MIN = C.CNT_SUM";
	
	public static void main(String[] args) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);			
				
			String MARRY_FLAG = "N";
			PreparedStatement stmt = conn.prepareStatement(SQL);
			stmt.setString(1, MARRY_FLAG);
			ResultSet rs = stmt.executeQuery();

			while(rs.next()) {
				System.out.print("最多月份 = " + rs.getString("最多月份"));
				System.out.print(", 最多數量 = " + rs.getString("最多數量"));
				System.out.print(", 最少月份 = " + rs.getString("最少月份"));
				System.out.println(", 最少數量 = " + rs.getString("最少數量"));
			}
			
			MARRY_FLAG = "Y";
			stmt.setString(1, MARRY_FLAG);
			rs = stmt.executeQuery();

			while(rs.next()) {
				System.out.print("最多月份 = " + rs.getString("最多月份"));
				System.out.print(", 最多數量 = " + rs.getString("最多數量"));
				System.out.print(", 最少月份 = " + rs.getString("最少月份"));
				System.out.println(", 最少數量 = " + rs.getString("最少數量"));
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
