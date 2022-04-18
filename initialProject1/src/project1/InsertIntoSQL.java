package project1;

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

public class InsertIntoSQL {

	public static final String SQL_DISTRICT = "INSERT INTO DISTRICT VALUES (?,?)";
	public static final String SQL_NEW_BORN_BABY = "INSERT INTO NEW_BORN_BABY VALUES (?,?,?,?,?,?,?)";
	private static int countTableNewBornBaby = 0; 
	private static int countTableDistrict = 0;
	
	public static void inputDistrict(Map<String, String> map_district, Connection conn) {
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL_DISTRICT);
			for (String site_code : map_district.keySet()) {
				pstmt.setInt(1, Integer.parseInt(site_code));
				pstmt.setString(2, map_district.get(site_code));
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			countTableDistrict+=map_district.size();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void inputNewBornBaby(List<Map<String, String>> list_new_born_baby, Connection conn) {
		PreparedStatement pstmt;
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		try {
			pstmt = conn.prepareStatement(SQL_NEW_BORN_BABY);
			// list_new_born_baby�̭��O6�U�h��Map(�@��ROW),�ҥH�����Map��list�R�X�Ӫ��C�@��map
			int counter = 0;
			for (Map<String, String> newBornData : list_new_born_baby) { // for each element in list_new_born_baby
				pstmt.setInt(1, Integer.parseInt(newBornData.get(CommonConst.SEQ)));
				pstmt.setInt(2, Integer.parseInt(newBornData.get(CommonConst.SITE_CODE)));
				pstmt.setString(3, newBornData.get(CommonConst.SEX));
				pstmt.setInt(4, Integer.parseInt(newBornData.get(CommonConst.BIRTH_CNT)));
				pstmt.setString(5, newBornData.get(CommonConst.FATHER_AGE));
				pstmt.setString(6, newBornData.get(CommonConst.MOTHER_AGE));
				pstmt.setDate(7, new java.sql.Date(format.parse(newBornData.get(CommonConst.UPDATE_DATE)).getTime()));
				pstmt.addBatch();
				counter++;
				if (counter == 1000) {
					pstmt.executeBatch();
					countTableNewBornBaby+=counter;
					counter = 0;
				}
			}
			if (counter != 0) {
				pstmt.executeBatch();
				countTableNewBornBaby+=counter;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	public static void batchInsertData(List<Map<String, String>> list_new_born_baby, Map<String, String> map_district) {
		Connection conn = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); // �ϥ�Class.forName(�M��W.���O�W)���UJDBC Driver
			conn = DriverManager.getConnection(CommonConst.DB_URL, CommonConst.USER, CommonConst.PASSWORD);
			inputDistrict(map_district, conn);
			inputNewBornBaby(list_new_born_baby, conn);

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
		List<Map<String, String>> list_new_born_baby = new ArrayList<>(); 
		Map<String, String> map_district = new HashMap<>();

		ParseCSV.parseCsv(list_new_born_baby, map_district);
		System.out.printf("==================���Ū������=====================%n");
		System.out.println();
		System.out.printf(" (��@)109�~�׷s�ͨ��ƪ�: Ū��%d��, %n (��G)���꿤���a�ϻP�N�X:  Ū��%d�� %n", list_new_born_baby.size(), map_district.size());
		System.out.println();
		System.out.printf("===================�}�l�פJDB======================%n");
		System.out.println();
		InsertIntoSQL.batchInsertData(list_new_born_baby, map_district);
		System.out.printf("===================�פJDB����======================%n");
		System.out.println();
		System.out.printf(" (��@)109�~�׷s�ͨ��ƪ�: �פJ%d��, %n (��G)���꿤���a�ϻP�N�X:  �פJ%d�� %n", countTableNewBornBaby, countTableDistrict);
		System.out.println();
	}

}
