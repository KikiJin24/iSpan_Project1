package project1;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class JdbcCRUD {

	public static Scanner scanner = new Scanner(System.in);

	public static void delete() {
		Connection conn = null;
		PreparedStatement pstmt;
		try {
			conn = DriverManager.getConnection(CommonConst.DB_URL, CommonConst.USER, CommonConst.PASSWORD);
			int scannerSeqtoDelete = 0;
			List<Map<String, String>> listMapResultSet = null;
			pstmt = conn.prepareStatement(SQLStatement.deleteARow);
			while (true) {
				System.out.println("======================================");
				System.out.println("  �w��i�J�R���t��");
				System.out.println("  ����109�~�ץ���a�Ϸs�ͨ��ƪ�");
				System.out.println("  > (�����}���t��,�п�Je)");
				System.out.println("  > �п�J�Q�R������ƧǸ�:");
				System.out.print("  > ");
				String userInput = scanner.nextLine(); // userInput����user��J��String
				if (userInput.equals("e")) {
					break;
				} else if (Integer.parseInt(userInput) > 0) { // ��userInput�নint���A //�p�Guser��J���Ǹ�>0
					scannerSeqtoDelete = Integer.parseInt(userInput); // �নint�s��scannerSeqtoDelete
				} else {
					System.out.println("��J���~�A�Э��s��J");
					continue;
				}
				listMapResultSet = CommonFunc.getARow(scannerSeqtoDelete);
				System.out.println("  > ");
				System.out.println("  > �z�Q�R�����O�o�����:");
				CommonFunc.showARow(listMapResultSet);
				System.out.println("  > ");
				System.out.println("  > �T�w�R��:�п�J1");
				System.out.println("  > ���}���t��:�п�J0");
				System.out.print("  > ");
				int scannerCheckDelete = Integer.parseInt(scanner.nextLine());
				if (scannerCheckDelete == 1) {
					pstmt.setInt(1, scannerSeqtoDelete);
					int deleteCount = pstmt.executeUpdate();
					System.out.println("  > ");
					System.out.println("  > �R������");
					System.out.printf("  > �z�w�R��%d�����%n", deleteCount);
				} else {
					break;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void update() {
		Connection conn;
		CallableStatement cstmt;
		int scannerSeqtoUpdate = 0;
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date current = new Date();

		try {
			conn = DriverManager.getConnection(CommonConst.DB_URL, CommonConst.USER, CommonConst.PASSWORD);
			while (true) {
				System.out.println("======================================");
				System.out.println("  �w��i�J��s�t��");
				System.out.println("  ����109�~�ץ���a�Ϸs�ͨ��ƪ�");
				System.out.println("  > (�����}���t��,�п�Je)");
				System.out.println("  > �п�J�Q��s����ƧǸ�:");
				System.out.print("  > ");
				String userInput = scanner.nextLine();
				if (userInput.equals("e")) {
					break;
				} else if (Integer.parseInt(userInput) > 0) {
					scannerSeqtoUpdate = Integer.parseInt(userInput);
				} else {
					System.out.println("��J���~�A�Э��s��J");
					continue;
				}
				System.out.println("  > ");
				System.out.println("  > �z�Q��s���O�o�����:");
				CommonFunc.getARowStoredProcedure(scannerSeqtoUpdate);

				System.out.println("  > �п�J��s�᪺����X�ͤH��:");
				System.out.print("  > ");
				int scannerBirthCnt = Integer.parseInt(scanner.nextLine());

				cstmt = conn.prepareCall(SQLStatement.updateBirthCntStoredProcedure);
				cstmt.setInt(1, scannerBirthCnt);
				cstmt.setInt(2, scannerSeqtoUpdate);
				cstmt.setDate(3, new java.sql.Date(sdFormat.parse(sdFormat.format(current)).getTime()));

				System.out.println("  > ");
				System.out.println("  > �T�w��s:�п�J1");
				System.out.println("  > ���}���t��:�п�J0");
				System.out.print("  > ");
				int scannerCheckUpdate = Integer.parseInt(scanner.nextLine());
				if (scannerCheckUpdate == 1) {
					// Boolean updSuccess = cstmt.execute();
					cstmt.execute();

					System.out.println("  > ");
					System.out.println("  > ��s����");
					System.out.printf("  > ��s������ܦp�U:%n");
					CommonFunc.getARowStoredProcedure(scannerSeqtoUpdate);

				} else {
					break;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public static void read() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(CommonConst.DB_URL, CommonConst.USER, CommonConst.PASSWORD);
			Statement stmt = conn.createStatement();
			ResultSet rs = null;
			while (true) {
				System.out.println("======================================");
				System.out.println("  �w��i�J�d�ߨt��");
				System.out.println("  ����109�~�ץ���a�Ϸs�ͨ��ƪ�");
				System.out.println("  �п�J�Q�d�ߪ��ﶵ(1~6)");
				System.out.println("  1 �����d�ߥ������");
				System.out.println("  2 ����d�ߤ@:�d�ߥͨ|�H�Ƥ���0���a��");
				System.out.println("  3 ����d�ߤG:�d�ߥͨ|�H�Ƥ���0�A�B�ͨ|�H�ƥѤ֨�h�Ƨ�");
				System.out.println("  4 ����d�ߤT:�d�ߥ����~�ͨ|�����");
				System.out.println("  5 �M���d��");
				System.out.println("  6 ���}�d��");
				System.out.print(" > ");
				int option = Integer.parseInt(scanner.nextLine());
				if (option == 1) {
					rs = stmt.executeQuery(SQLStatement.selectJoinAll);
				} else if (option == 2) {
					rs = stmt.executeQuery(SQLStatement.selectNonZero);
				} else if (option == 3) {
					rs = stmt.executeQuery(SQLStatement.selectBornCountSorted);
				} else if (option == 4) {
					rs = stmt.executeQuery(SQLStatement.selectPremature);
				} else if (option == 5) {
					CommonFunc.clearConsole();
				} else if (option == 6) {
					break;
				} else {
					System.out.println("��J���~�A�Э��s��J");
					continue;
				}
				if (rs != null) {
					while (rs.next()) { // JDBC�ҥ�p29 while(rs.next()){}
						System.out.print(" �a��: " + rs.getString("SITE_NAME"));
						System.out.print(" ,���˦~��: " + rs.getString("FATHER_AGE"));
						System.out.print(" ,���˦~��: " + rs.getString("MOTHER_AGE"));
						System.out.print(" ,�ͨ|�H��: " + rs.getInt("BIRTH_CNT"));
						System.out.println(" ,�ʧO: " + rs.getString("SEX"));
					}
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
	}

	public static void create() {

		Connection conn = null;
		PreparedStatement pstmt = null;
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date current = new Date();
		
		Map<String, String> map_waitInsert = new HashMap<String, String>();
		try {
			conn = DriverManager.getConnection(CommonConst.DB_URL, CommonConst.USER, CommonConst.PASSWORD);
			while (true) {
				System.out.println("======================================");
				System.out.println("  �w��i�J�s�W�t��");
				System.out.println("  ����109�~�ץ���a�Ϸs�ͨ��ƪ�");
				System.out.println("  > (�����}���t��,�п�Je)");
				System.out.println("  > �ж}�l��J�Q�n�s�W�����");
				System.out.print("  > �Ǹ�: ");
				String inputSeq = scanner.nextLine();
				if (inputSeq.equalsIgnoreCase("e")) {
					break;
				} else {
					map_waitInsert.put(CommonConst.SEQ, inputSeq);
				}

				System.out.print("  > �ϸ�: ");
				String inputSite_Code = scanner.nextLine();
				if (inputSite_Code.equalsIgnoreCase("e")) {
					break;
				} else {
					map_waitInsert.put(CommonConst.SITE_CODE, inputSite_Code);
				}

				System.out.print("  > �a��: ");
				String inputSite_Name = scanner.nextLine();
				if (inputSite_Name.equalsIgnoreCase("e")) {
					break;
				}else {
					map_waitInsert.put(CommonConst.SITE_NAME, inputSite_Name);
				}

				System.out.print("  > ���˦~��: ");
				String inputF_Age = scanner.nextLine();
				if (inputF_Age.equalsIgnoreCase("e")) {
					break;
				}else {
					map_waitInsert.put(CommonConst.FATHER_AGE, inputF_Age);
				}

				System.out.print("  > ���˦~��: ");
				String inputM_Age = scanner.nextLine();
				if (inputM_Age.equalsIgnoreCase("e")) {
					break;
				}else {
					map_waitInsert.put(CommonConst.MOTHER_AGE, inputM_Age);
				}

				System.out.print("  > �ʧO: ");
				String inputSex = scanner.nextLine();
				if (inputSex.equalsIgnoreCase("e")) {
					break;
				}else {
					map_waitInsert.put(CommonConst.SEX, inputSex);
				}

				System.out.print("  > �ͨ|�H��: ");
				String inputBirth_Cnt = scanner.nextLine();
				if (inputBirth_Cnt.equalsIgnoreCase("e")) {  //e
					break;
				} else {
					map_waitInsert.put(CommonConst.BIRTH_CNT, inputBirth_Cnt);
				}
				System.out.println("  > ");
				System.out.printf("  > �z�s�W����ƬO:%n  > �Ǹ�:%d,�ϸ�:%d,�a��:%s,���˦~��:%s,���˦~��:%s,�ʧO:%s,�ͨ|�H��:%d%n", Integer.parseInt(inputSeq) ,
						Integer.parseInt(inputSite_Code), inputSite_Name, inputF_Age, inputM_Age, inputSex, Integer.parseInt(inputBirth_Cnt));

				System.out.println("  > ");
				System.out.println("  > �T�w�s�W:�п�J1");
				System.out.println("  > ���s��J:�п�J0");
				System.out.print("  > ");
				int userInput = Integer.parseInt(scanner.nextLine());
				if (userInput == 0) {
					continue;
				} else if (userInput == 1) {
					//Insert Into DISTRICT
					pstmt = conn.prepareStatement(SQLStatement.insertIntoDISTRICT);
					if(!CommonFunc.getAllDISTRICT().containsKey(Integer.parseInt(inputSite_Code))) {
						pstmt.setInt(1,Integer.parseInt(inputSite_Code));
						pstmt.setString(2, inputSite_Name);
					}
					pstmt.execute();
					
					//Insert Into NEW_BORN_BABY
					pstmt = conn.prepareStatement(SQLStatement.insertIntoNEW_BORN_BABY);
					pstmt.setInt(1,Integer.parseInt(inputSeq));
					pstmt.setInt(2,Integer.parseInt(inputSite_Code));
					pstmt.setString(3, inputSex);
					pstmt.setInt(4,Integer.parseInt(inputBirth_Cnt));
					pstmt.setString(5, inputF_Age);
					pstmt.setString(6, inputM_Age);
					pstmt.setDate(7, new java.sql.Date(sdFormat.parse(sdFormat.format(current)).getTime()));
					pstmt.execute();
					System.out.println("  > ");
					System.out.println("  > �s�W����");
					System.out.println("  > ");
					System.out.println("  > �z�s�W����Ʀp�U: ");
					CommonFunc.getARowStoredProcedure(Integer.parseInt(inputSeq));
					
					
				} else {
					System.out.println("��J���~�A�Э��s��J");
					continue;
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
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

}
