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
			System.out.println("  �w��i�J��s�t��");
			System.out.println("  ����109�~�ץ���a�Ϸs�ͨ��ƪ�");
			System.out.println("  > (�����}���t��,�п�Je)");
			System.out.println("  > �п�J�Q��s����ƧǸ�:");
			System.out.print("  > ");
			String userInput = scanner.nextLine();
			if (userInput.equals("e")) {
				break;
			} else if(Integer.parseInt(userInput) > 0){
				scannerSeqtoUpdate = Integer.parseInt(userInput);
			}else {
				System.out.println("��J���~�A�Э��s��J");
				continue;
			}

			//�ΧǸ���iSQL���O�A�d�X���
			//show�X���  -->StoredProcedure(show_row_by_seq)
			//user�T�{���  -->CommonFunc(getArowStoredProcedure)
			//��J��s����H�� 
			//��iSQL���O,update��� -->StoredProcedure(upd_birth_cnt)
			//show�X��s�᪺���  -->�W�誺StoredProcedure(show_row_by_seq)
			
			System.out.println("  > ");
			System.out.println("  > �z�Q��s���O�o�����:");		
			CommonFunc.getARowStoredProcedure(scannerSeqtoUpdate);

			System.out.println("  > �п�J��s�᪺����X�ͤH��:");
			System.out.print("  > ");
			int scannerBirthCnt = Integer.parseInt(scanner.nextLine());

			cstmt = conn.prepareCall(SQLStatement.updateBirthCntStoredProcedure);
			cstmt.setInt(1,scannerBirthCnt);
			cstmt.setInt(2,scannerSeqtoUpdate);
			cstmt.setDate(3, new java.sql.Date(sdFormat.parse(sdFormat.format(current)).getTime()));

			System.out.println("  > ");
			System.out.println("  > �T�w��s:�п�J1");
			System.out.println("  > ���}���t��:�п�J0");
			System.out.print("  > ");
			int scannerCheckUpdate = Integer.parseInt(scanner.nextLine());
			if(scannerCheckUpdate == 1) {
				//Boolean updSuccess = cstmt.execute();
				cstmt.execute();
				
					System.out.println("  > ");
					System.out.println("  > ��s����");
					System.out.printf("  > ��s������ܦp�U:%n");
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
