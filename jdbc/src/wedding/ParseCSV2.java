package  wedding;

import java.awt.RadialGradientPaint;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.processing.AbstractProcessor;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class ParseCSV2 {

	public static void parseCsv(
			List<Map<String, String>> list_marry_cnt,
			Map<String, String> map_marry_cnt) {
		
		// read csv
		try (FileInputStream fis = new FileInputStream("D:\\jdbc\\jdbcworkspace\\Wedding\\bin\\wedding\\MARRY_CNT.csv");
				InputStreamReader isr = new InputStreamReader(fis, "UTF8");
				BufferedReader br = new BufferedReader(isr);) {
			@SuppressWarnings("deprecation")
			Iterable<CSVRecord> records = CSVFormat.RFC4180.withHeader("AREA_CODE", "YEAR", "JAN_CNT", "FEB_CNT" ,"MAR_CNT", "APR_CNT", "MAY_CNT", "JUN_CNT", "JUL_CNT", "AUG_CNT", "SEP_CNT", "OCT_CNT", "NOV_CNT", "DEC_CNT", "MARRY_FLAG", "OID").parse(br);
			Map<String, String> map = new HashMap<>();
			for (CSVRecord record : records) {
				System.out.println(record);
				System.out.println(record.get(0));
//				for(int i = 1 ; i<= record.size()-1; i++) {
				System.out.println(map.put("AREA_CODE",record.get("AREA_CODE")));
				map.put("AREA_CODE",record.get(0));
				map.put("YEAR",record.get(1));		
				map.put("JAN_CNT",record.get(2));			
				map.put("FEB_CNT",record.get(3));				
				map.put("MAR_CNT",record.get(4));				
				map.put("APR_CNT",record.get(5));				
				map.put("MAY_CNT",record.get(6));				
				map.put("JUN_CNT",record.get(7));				
				map.put("JUL_CNT",record.get(8));				
				map.put("AUG_CNT",record.get(9));				
				map.put("SEP_CNT",record.get(10));				
				map.put("OCT_CNT",record.get(11));				
				map.put("NOV_CNT",record.get(12));				
				map.put("DEC_CNT",record.get(13));				
				map.put("MARRY_FLAG",record.get(14));				
				map.put("OID",record.get(15));
				
				list_marry_cnt.add(map_marry_cnt);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

	}
	
}
