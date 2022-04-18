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

public class InsertToSQL2 {

	private static final String JDBC_DRIVER = 
			"com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static final String DB_URL = 
			"jdbc:sqlserver://localhost:1433;databaseName=Test_j";
	private static final String USER = "sa";
	private static final String PASSWORD = "passw0rd";
	
	public static final String SQL_MARRY_CNT = "INSERT INTO MARRY_CNT VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private static int count =0;
	
	
	public static void insertIntoMarryCnt(List<Map<String, String>> list_marry_cnt, Connection conn) {
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL_MARRY_CNT);
			int counter = 0;
			for (Map<String, String> marry_cnt : list_marry_cnt) {
//				System.out.println(marry_cnt.get("111" +pstmt));
				
				pstmt.setString(1, marry_cnt.get("AREA_CODE"));
				pstmt.setInt(2, Integer.parseInt(marry_cnt.get("YEAR")));				
				pstmt.setInt(3, Integer.parseInt(marry_cnt.get("JAN_CNT")));
				pstmt.setInt(4, Integer.parseInt(marry_cnt.get("FEB_CNT")));
				pstmt.setInt(5, Integer.parseInt(marry_cnt.get("MAR_CNT")));
				pstmt.setInt(6, Integer.parseInt(marry_cnt.get("APR_CNT")));
				pstmt.setInt(7, Integer.parseInt(marry_cnt.get("MAY_CNT")));
				pstmt.setInt(8, Integer.parseInt(marry_cnt.get("JUN_CNT")));
				pstmt.setInt(9, Integer.parseInt(marry_cnt.get("JUL_CNT")));
				pstmt.setInt(10, Integer.parseInt(marry_cnt.get("AUG_CNT")));
				pstmt.setInt(11, Integer.parseInt(marry_cnt.get("SEP_CNT")));
				pstmt.setInt(12, Integer.parseInt(marry_cnt.get("OCT_CNT")));
				pstmt.setInt(13, Integer.parseInt(marry_cnt.get("NOV_CNT")));
				pstmt.setInt(14, Integer.parseInt(marry_cnt.get("DEC_CNT")));
				pstmt.setString(15, marry_cnt.get("MARRY_FLAG"));
				pstmt.setString(16, marry_cnt.get("OID"));
				pstmt.addBatch();
				
				counter++;
				if(counter == 1000) {
					pstmt.executeBatch();
					count += counter;
					counter = 0;					
				}
			}
			
			if(counter !=0) {
				pstmt.execute();
				count += counter;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (NumberFormatException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	

	public static void batchInsertData(List<Map<String, String>> list_marry_cnt,Map<String, String> area) {
		Connection conn = null;
		try {
			Class.forName(JDBC_DRIVER); 
			conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);

			InsertToSQL.insertIntoArea(area, conn);
			insertIntoMarryCnt(list_marry_cnt,conn);

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
		List<Map<String, String>> list_marry_cnt = new ArrayList<>();
		
		
		
		ParseCSV2.parseCsv(list_marry_cnt,area);
		System.out.printf("=================");
		
		InsertToSQL2.batchInsertData(list_marry_cnt,area);
		System.out.printf("=====done=====%n");
		
	}

}
