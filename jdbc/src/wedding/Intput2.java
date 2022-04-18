package wedding;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

public class Intput2 {
    public static void main(String[] args) {
        /*
        將exam/e8/Employee.csv Copy至c:\java
        利用Commons csv library來處理csv檔案,注意cvs檔案編碼為MS950
        網址如下  https://commons.apache.org/proper/commons-csv/
        1.下載commons-csv-1.8-bin.zip，解壓縮後將jar放至lib目錄
        2.eclipse中設定library
        3.參考官方的User Guide處理此csv
        4.所有有實作AutoCloseable的物件都必須close
        5.不可自行將csv中的千分號移除，不可以改動csv中的任何一個字。
        取得薪水的加總
         */

    	try (FileInputStream fis = new FileInputStream("C:\\Users\\USER\\Desktop\\product1\\MARRY_CNT.csv");
            InputStreamReader isr = new InputStreamReader(fis, "MS950");
            BufferedReader br = new BufferedReader(isr);
    			){
            
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
//            	System.out.printf(line);
            	String[] fields = line.split(",");
            	
            	System.out.printf("AREA_CODE:%s,YEAR:%s,JAN_CNT:%s,FEB_CNT:%s ,MAR_CNT:%s, APR_CNT:%s, MAY_CNT:%s, JUN_CNT:%s, JUL_CNT:%s, AUG_CNT:%s, SEP_CNT:%s, OCT_CNT:%s, NOV_CNT:%s, DEC_CNT:%s, MARRY_FLAG:%s, OID:%s%n"
            			, fields[0], fields[1], fields[2], fields[3], fields[4], fields[5], fields[6], fields[7], fields[8], fields[9],fields[10], fields[11], fields[12], fields[13], fields[14], fields[15]);
            	 
            	//System.out.println(fields[2].replace("\"", "").replace(",", ""));
            	
            }
            
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            
           
        }
    }
}
