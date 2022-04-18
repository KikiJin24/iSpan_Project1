package project1_copy;

import java.io.IOException;
import java.util.Scanner;

public class Project1 {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("==================================");
			System.out.println("   此表為109年度全國地區新生兒資料表");
			System.out.println("   請輸入想要的功能(1~8)");
			System.out.println("   1 Input");
			System.out.println("   2 Create");
			System.out.println("   3 Read");
			System.out.println("   4 Update");
			System.out.println("   5 Delete");
			System.out.println("   6 Output");
			System.out.println("   7 Clear Console");
			System.out.println("   8 Exit");
			System.out.print(" => ");
			int option = Integer.parseInt(scanner.nextLine());
			if (option == 1) {
				InsertIntoSQL.doInsert();
			} else if (option == 2) {
				JdbcCRUD.create();
			} else if (option == 3) {
				JdbcCRUD.read();
			} else if (option == 4) {
				JdbcCRUD.update();
			} else if (option == 5) {
				JdbcCRUD.delete();
			} else if (option == 6) {
				Output.outputCSV();
				//Output.outputJson(".\\jsontest.json");
			} else if(option == 7) {
				CommonFunc.clearConsole();
			} else if(option == 8) {
				break;
			}else {
				System.out.println("輸入錯誤，請重新輸入");
				
			}
		}
		scanner.close();
	}
	
}
