package project1_copy;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;


public class TestUpdateStoredProcedure {

public static Scanner scanner = new Scanner(System.in);
	
	
	
	public static void updatestoredProcedure() {
		
	Connection conn;
	CallableStatement cstmt;
	int scannerSeqtoUpdate = 0;
	
	SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");
	Date current = new Date();
	
	try {
		conn = DriverManager.getConnection(CommonConst.DB_URL, CommonConst.USER, CommonConst.PASSWORD);

		while (true) {
			System.out.println("==================================");
			System.out.println("  歡迎進入更新系統");
			System.out.println("  此表為109年度全國地區新生兒資料表");
			System.out.println("  > (欲離開此系統,請輸入e)");
			System.out.println("  > 請輸入想更新的資料序號:");
			System.out.print("  > ");
			String userInput = scanner.nextLine();
			if (userInput.equals("e")) {
				break;
			} else if(Integer.parseInt(userInput) > 0){
				scannerSeqtoUpdate = Integer.parseInt(userInput);
			}else {
				System.out.println("輸入錯誤，請重新輸入");
				continue;
			}

			//用序號丟進SQL指令，查出資料
			//show出資料  -->StoredProcedure(show_row_by_seq)
			//user確認資料  -->CommonFunc(getArowStoredProcedure)
			//輸入更新嬰兒人數 
			//丟進SQL指令,update資料 -->StoredProcedure(upd_birth_cnt)
			//show出更新後的資料  -->上方的StoredProcedure(show_row_by_seq)
			
			System.out.println("  > ");
			System.out.println("  > 您想更新的是這筆資料:");		
			CommonFunc.getARowStoredProcedure(scannerSeqtoUpdate);

			System.out.println("  > 請輸入更新後的嬰兒出生人數:");
			System.out.print("  > ");
			int scannerBirthCnt = Integer.parseInt(scanner.nextLine());

			cstmt = conn.prepareCall(SQLStatement.updateBirthCntStoredProcedure);
			cstmt.setInt(1,scannerBirthCnt);
			cstmt.setInt(2,scannerSeqtoUpdate);
			cstmt.setDate(3, new java.sql.Date(sdFormat.parse(sdFormat.format(current)).getTime()));

			System.out.println("  > ");
			System.out.println("  > 確定更新:請輸入1");
			System.out.println("  > 離開此系統:請輸入0");
			System.out.print("  > ");
			int scannerCheckUpdate = Integer.parseInt(scanner.nextLine());
			if(scannerCheckUpdate == 1) {
				//Boolean updSuccess = cstmt.execute();
				cstmt.execute();
				
					System.out.println("  > ");
					System.out.println("  > 更新完成");
					System.out.printf("  > 更新後資料顯示如下:%n");
					CommonFunc.getARowStoredProcedure(scannerSeqtoUpdate);	
	
			}else {
				break;
			}	
		}
		

	} catch (SQLException e) {
		e.printStackTrace();
	} catch (ParseException e) {
		e.printStackTrace();
	}
	}
	
	
}
