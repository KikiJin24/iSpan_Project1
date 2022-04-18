package wedding;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

public class Intput {
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

    	try (FileInputStream fis = new FileInputStream("D:/workspace/Wedding/src/wedding/AREA.csv");
            InputStreamReader isr = new InputStreamReader(fis, "MS950");
            BufferedReader br = new BufferedReader(isr);
    			){
            
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
//            	System.out.printf(line);
            	String[] fields = line.split(",");
            	System.out.printf("AREA_CODE:%s,AREA_NAME:%s%n", fields[0], fields[1]);
            	//System.out.println(fields[2].replace("\"", "").replace(",", ""));
            	
            }
            
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
