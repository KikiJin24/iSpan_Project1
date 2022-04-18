package test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestSimpleDateFormat {

	public static void main(String[] args) {
		//取得現在日期，格式可以自己設定
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date current = new Date();
		System.out.println(sdFormat.format(current));
		
		//取得現在日期(string型態)，轉成java.sql.date的型態
		//new java.sql.Date(sdFormat.parse(sdFormat.format(current)).getTime())

	}

}
