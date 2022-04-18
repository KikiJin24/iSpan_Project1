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
				System.out.println("  歡迎進入刪除系統");
				System.out.println("  此表為109年度全國地區新生兒資料表");
				System.out.println("  > (欲離開此系統,請輸入e)");
				System.out.println("  > 請輸入想刪除的資料序號:");
				System.out.print("  > ");
				String userInput = scanner.nextLine(); // userInput接到user輸入的String
				if (userInput.equals("e")) {
					break;
				} else if (Integer.parseInt(userInput) > 0) { // 把userInput轉成int型態 //如果user輸入的序號>0
					scannerSeqtoDelete = Integer.parseInt(userInput); // 轉成int存到scannerSeqtoDelete
				} else {
					System.out.println("輸入錯誤，請重新輸入");
					continue;
				}
				listMapResultSet = CommonFunc.getARow(scannerSeqtoDelete);
				System.out.println("  > ");
				System.out.println("  > 您想刪除的是這筆資料:");
				CommonFunc.showARow(listMapResultSet);
				System.out.println("  > ");
				System.out.println("  > 確定刪除:請輸入1");
				System.out.println("  > 離開此系統:請輸入0");
				System.out.print("  > ");
				int scannerCheckDelete = Integer.parseInt(scanner.nextLine());
				if (scannerCheckDelete == 1) {
					pstmt.setInt(1, scannerSeqtoDelete);
					int deleteCount = pstmt.executeUpdate();
					System.out.println("  > ");
					System.out.println("  > 刪除完成");
					System.out.printf("  > 您已刪除%d筆資料%n", deleteCount);
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
				System.out.println("  歡迎進入更新系統");
				System.out.println("  此表為109年度全國地區新生兒資料表");
				System.out.println("  > (欲離開此系統,請輸入e)");
				System.out.println("  > 請輸入想更新的資料序號:");
				System.out.print("  > ");
				String userInput = scanner.nextLine();
				if (userInput.equals("e")) {
					break;
				} else if (Integer.parseInt(userInput) > 0) {
					scannerSeqtoUpdate = Integer.parseInt(userInput);
				} else {
					System.out.println("輸入錯誤，請重新輸入");
					continue;
				}
				System.out.println("  > ");
				System.out.println("  > 您想更新的是這筆資料:");
				CommonFunc.getARowStoredProcedure(scannerSeqtoUpdate);

				System.out.println("  > 請輸入更新後的嬰兒出生人數:");
				System.out.print("  > ");
				int scannerBirthCnt = Integer.parseInt(scanner.nextLine());

				cstmt = conn.prepareCall(SQLStatement.updateBirthCntStoredProcedure);
				cstmt.setInt(1, scannerBirthCnt);
				cstmt.setInt(2, scannerSeqtoUpdate);
				cstmt.setDate(3, new java.sql.Date(sdFormat.parse(sdFormat.format(current)).getTime()));

				System.out.println("  > ");
				System.out.println("  > 確定更新:請輸入1");
				System.out.println("  > 離開此系統:請輸入0");
				System.out.print("  > ");
				int scannerCheckUpdate = Integer.parseInt(scanner.nextLine());
				if (scannerCheckUpdate == 1) {
					// Boolean updSuccess = cstmt.execute();
					cstmt.execute();

					System.out.println("  > ");
					System.out.println("  > 更新完成");
					System.out.printf("  > 更新後資料顯示如下:%n");
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
				System.out.println("  歡迎進入查詢系統");
				System.out.println("  此表為109年度全國地區新生兒資料表");
				System.out.println("  請輸入想查詢的選項(1~6)");
				System.out.println("  1 直接查詢全部資料");
				System.out.println("  2 條件查詢一:查詢生育人數不為0的地區");
				System.out.println("  3 條件查詢二:查詢生育人數不為0，且生育人數由少到多排序");
				System.out.println("  4 條件查詢三:查詢未成年生育的資料");
				System.out.println("  5 清除查詢");
				System.out.println("  6 離開查詢");
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
					System.out.println("輸入錯誤，請重新輸入");
					continue;
				}
				if (rs != null) {
					while (rs.next()) { // JDBC課本p29 while(rs.next()){}
						System.out.print(" 地區: " + rs.getString("SITE_NAME"));
						System.out.print(" ,父親年齡: " + rs.getString("FATHER_AGE"));
						System.out.print(" ,母親年齡: " + rs.getString("MOTHER_AGE"));
						System.out.print(" ,生育人數: " + rs.getInt("BIRTH_CNT"));
						System.out.println(" ,性別: " + rs.getString("SEX"));
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
				System.out.println("  歡迎進入新增系統");
				System.out.println("  此表為109年度全國地區新生兒資料表");
				System.out.println("  > (欲離開此系統,請輸入e)");
				System.out.println("  > 請開始輸入想要新增的資料");
				System.out.print("  > 序號: ");
				String inputSeq = scanner.nextLine();
				if (inputSeq.equalsIgnoreCase("e")) {
					break;
				} else {
					map_waitInsert.put(CommonConst.SEQ, inputSeq);
				}

				System.out.print("  > 區號: ");
				String inputSite_Code = scanner.nextLine();
				if (inputSite_Code.equalsIgnoreCase("e")) {
					break;
				} else {
					map_waitInsert.put(CommonConst.SITE_CODE, inputSite_Code);
				}

				System.out.print("  > 地區: ");
				String inputSite_Name = scanner.nextLine();
				if (inputSite_Name.equalsIgnoreCase("e")) {
					break;
				}else {
					map_waitInsert.put(CommonConst.SITE_NAME, inputSite_Name);
				}

				System.out.print("  > 父親年齡: ");
				String inputF_Age = scanner.nextLine();
				if (inputF_Age.equalsIgnoreCase("e")) {
					break;
				}else {
					map_waitInsert.put(CommonConst.FATHER_AGE, inputF_Age);
				}

				System.out.print("  > 母親年齡: ");
				String inputM_Age = scanner.nextLine();
				if (inputM_Age.equalsIgnoreCase("e")) {
					break;
				}else {
					map_waitInsert.put(CommonConst.MOTHER_AGE, inputM_Age);
				}

				System.out.print("  > 性別: ");
				String inputSex = scanner.nextLine();
				if (inputSex.equalsIgnoreCase("e")) {
					break;
				}else {
					map_waitInsert.put(CommonConst.SEX, inputSex);
				}

				System.out.print("  > 生育人數: ");
				String inputBirth_Cnt = scanner.nextLine();
				if (inputBirth_Cnt.equalsIgnoreCase("e")) {  //e
					break;
				} else {
					map_waitInsert.put(CommonConst.BIRTH_CNT, inputBirth_Cnt);
				}
				System.out.println("  > ");
				System.out.printf("  > 您新增的資料是:%n  > 序號:%d,區號:%d,地區:%s,父親年齡:%s,母親年齡:%s,性別:%s,生育人數:%d%n", Integer.parseInt(inputSeq) ,
						Integer.parseInt(inputSite_Code), inputSite_Name, inputF_Age, inputM_Age, inputSex, Integer.parseInt(inputBirth_Cnt));

				System.out.println("  > ");
				System.out.println("  > 確定新增:請輸入1");
				System.out.println("  > 重新輸入:請輸入0");
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
					System.out.println("  > 新增完成");
					System.out.println("  > ");
					System.out.println("  > 您新增的資料如下: ");
					CommonFunc.getARowStoredProcedure(Integer.parseInt(inputSeq));
					
					
				} else {
					System.out.println("輸入錯誤，請重新輸入");
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
