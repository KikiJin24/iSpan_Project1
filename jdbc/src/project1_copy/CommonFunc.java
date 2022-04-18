package project1_copy;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CommonFunc {
	                                  // url�����s���S�w��Ʈw�һݪ���T
	public static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=project1";
	public static final String USER = "sa";
	public static final String PASSWORD = "passw0rd";

	public static Scanner scanner = new Scanner(System.in);
	
	public static void clearConsole() {
		for (int i = 0; i < 1000; i++) {
			System.out.println();
		}
	}

	
	public static Map<Integer, String> getAllDISTRICT() {
		Connection conn = null;
		Map<Integer, String> map_AllDISTRICT = new HashMap<Integer, String>();
		//{(9007010,�s�����n��m),(9007020,�s�����_��m)...}
		try {
			conn = DriverManager.getConnection(CommonConst.DB_URL, CommonConst.USER, CommonConst.PASSWORD);
			Statement stmt = conn.createStatement();
			ResultSet rs = null; 
			rs = stmt.executeQuery(SQLStatement.selectAllDISTRICT);
			
			while(rs.next()) {
				map_AllDISTRICT.put(rs.getInt(CommonConst.SITE_CODE),rs.getString(CommonConst.SITE_NAME));	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map_AllDISTRICT;
		
	}

		
		

	public static void getARowStoredProcedure(int inputSeq) {
		Connection conn = null;
		CallableStatement cstmt;
		try {
			conn = DriverManager.getConnection(CommonConst.DB_URL, CommonConst.USER, CommonConst.PASSWORD);
			cstmt = conn.prepareCall(SQLStatement.selectRowBySeqStoredProcedure);
			cstmt.setInt(1,inputSeq);  //input SEQ
			cstmt.registerOutParameter(2, Types.INTEGER);   //SEQ
			cstmt.registerOutParameter(3, Types.INTEGER);   //SITE_CODE
			cstmt.registerOutParameter(4, Types.NVARCHAR);  //SITE_NAME
			cstmt.registerOutParameter(5, Types.NVARCHAR);  //FATHER_AGE
			cstmt.registerOutParameter(6, Types.NVARCHAR);  //MOTHER_AGE
			cstmt.registerOutParameter(7, Types.INTEGER);   //BIRTH_CNT
			cstmt.registerOutParameter(8, Types.NVARCHAR);  //SEX			
			
			cstmt.execute();
			/*System.out.printf("�z�o��@����ơA��ܦp�U:%n > �Ǹ�:%d,�ϸ�:%d,�a��:%s,���˦~��:%s,���˦~��:%s,�ʧO:%s,�ͨ|�H��:%d",
					cstmt.getInt("SEQ"),cstmt.getInt("SITE_CODE"),cstmt.getString("SITE_NAME"),cstmt.getString("FATHER_AGE"),
					cstmt.getString("MOTHER_AGE"),cstmt.getString("SEX"),cstmt.getInt("BIRTH_CNT"));*/
			
			
			System.out.printf("  > �Ǹ�:%d,�ϸ�:%d,�a��:%s,���˦~��:%s,���˦~��:%s,�ͨ|�H��:%d,�ʧO:%s%n",
					cstmt.getInt(2),cstmt.getInt(3),cstmt.getString(4),cstmt.getString(5),
					cstmt.getString(6),cstmt.getInt(7),cstmt.getString(8));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	
	
	
	
	//��Jseq�A�qDB���@�C���(row)
	public static List<Map<String, String>> getARow(int scannerSeq) {
		Connection conn = null;
		List<Map<String, String>> listMapResultSet = new ArrayList<Map<String,String>>();
		try {
			conn = DriverManager.getConnection(CommonConst.DB_URL, CommonConst.USER, CommonConst.PASSWORD);
			Statement stmt = conn.createStatement();
			ResultSet rs = null;   //Integer.parseInt(��String���A��)->��String�নint   //Integer.toString(��int���A��)->��int�୼String
			rs = stmt.executeQuery(SQLStatement.selectRow + Integer.toString(scannerSeq)); 
			
			while(rs.next()) {
				Map<String,String> mapInList = new HashMap<String, String>();
				mapInList.put(CommonConst.SEQ,rs.getString(CommonConst.SEQ));
				mapInList.put(CommonConst.SITE_CODE,rs.getString(CommonConst.SITE_CODE));
				mapInList.put(CommonConst.SITE_NAME,rs.getString(CommonConst.SITE_NAME)) ;
				mapInList.put(CommonConst.FATHER_AGE,rs.getString(CommonConst.FATHER_AGE));
				mapInList.put(CommonConst.MOTHER_AGE,rs.getString(CommonConst.MOTHER_AGE));
				mapInList.put(CommonConst.BIRTH_CNT,rs.getString(CommonConst.BIRTH_CNT));
				mapInList.put(CommonConst.SEX,rs.getString(CommonConst.SEX));
				mapInList.put(CommonConst.UPDATE_DATE,rs.getString(CommonConst.UPDATE_DATE));
				listMapResultSet.add(mapInList);
				
			}
			if (listMapResultSet != null) {    
					return listMapResultSet;
				}
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
		return null;
	}
	
	
	public static void showARow(List<Map<String, String>> listMapResultSet ) {
		for(Map<String, String> row : listMapResultSet) {  //JDBC�ҥ�p29 while(rs.next()){}
			System.out.print("  > �Ǹ�:" + row.get(CommonConst.SEQ));
			System.out.print(" , �ϸ�:" + row.get(CommonConst.SITE_CODE));
			System.out.print(" , �a��:" + row.get(CommonConst.SITE_NAME));
			System.out.print(" , ���˦~��:" + row.get(CommonConst.FATHER_AGE));
			System.out.print(" , ���˦~��:" + row.get(CommonConst.MOTHER_AGE));
			System.out.print(" , �ͨ|�H��:" + row.get(CommonConst.BIRTH_CNT));
			System.out.print(" , �ʧO:" + row.get(CommonConst.SEX));
			System.out.println(" ,��s�ɶ�:" + row.get(CommonConst.UPDATE_DATE));
		}
	}
	
	
	public static int getTableCount(String tableName) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(CommonConst.DB_URL, CommonConst.USER, CommonConst.PASSWORD);
			Statement stmt = conn.createStatement();
			ResultSet rs = null;
			rs = stmt.executeQuery(SQLStatement.selectCount + tableName);
			if (rs != null) {
				while (rs.next()) {  //next��cursor����U�@��(�Ĥ@��)  //JDBC�ҥ�p29 while(rs.next()){} 
					return rs.getInt("CNT");
				}
			}

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
		return 0;
	}

}
