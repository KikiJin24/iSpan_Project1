package test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestSimpleDateFormat {

	public static void main(String[] args) {
		//���o�{�b����A�榡�i�H�ۤv�]�w
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date current = new Date();
		System.out.println(sdFormat.format(current));
		
		//���o�{�b���(string���A)�A�নjava.sql.date�����A
		//new java.sql.Date(sdFormat.parse(sdFormat.format(current)).getTime())

	}

}
