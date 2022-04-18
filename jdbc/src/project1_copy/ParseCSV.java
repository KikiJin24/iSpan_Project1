package project1_copy;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class ParseCSV {

	public static void parseCsv(   //用list_new_born_baby和map_district變數，接呼叫時傳入的參數
			List<Map<String, String>> list_new_born_baby, // 0800起頭的list
			Map<String, String> map_district) {
		
		// read csv
		try (FileInputStream fis = new FileInputStream("D:\\JDBC\\pj1_newbornbaby109_final.csv\\");
				InputStreamReader isr = new InputStreamReader(fis, "MS950");
				BufferedReader br = new BufferedReader(isr);) {
			br.readLine();
			br.readLine();
			Iterable<CSVRecord> records = CSVFormat.RFC4180.withHeader(CommonConst.SEQ, CommonConst.SITE_CODE, CommonConst.SITE_NAME,CommonConst.SEX,
					CommonConst.BIRTH_CNT, CommonConst.FATHER_AGE, CommonConst.MOTHER_AGE, CommonConst.UPDATE_DATE).parse(br);

			// int counter = 0;

			// parse data to list and map
			for (CSVRecord record : records) {

				/*
				 * String seq = record.get("SEQ"); --- 1 //  map.put("SEQ", seq); --- 2 
				 * String site_code = record.get("SITE_CODE"); 
				 * String site_name = record.get("SITE_NAME"); 
				 * String sex = record.get("SEX"); 
				 * String birth_cnt = record.get("BIRTH_CNT"); String father_age = record.get("FATHER_AGE");
				 * String mother_age = record.get("MOTHER_AGE");
				 * String create_date = record.get("CREATE_DATE");
				 */
				Map<String, String> map = new HashMap<>();
				map.put(CommonConst.SEQ, record.get(CommonConst.SEQ)); // --- 1 + 2
				String site_code = record.get(CommonConst.SITE_CODE);
				map.put(CommonConst.SITE_CODE, site_code);
				String site_name = record.get(CommonConst.SITE_NAME);
				map.put(CommonConst.SEX, record.get(CommonConst.SEX));
				map.put(CommonConst.BIRTH_CNT, record.get(CommonConst.BIRTH_CNT));
				map.put(CommonConst.FATHER_AGE, record.get(CommonConst.FATHER_AGE));
				map.put(CommonConst.MOTHER_AGE, record.get(CommonConst.MOTHER_AGE));
				map.put(CommonConst.UPDATE_DATE, record.get(CommonConst.UPDATE_DATE));
				list_new_born_baby.add(map);
				if (!map_district.containsKey(site_code)) { // map_district.get(district) != null
					map_district.put(site_code, site_name);
				}
				/*
				 * if (counter < 10) { System.out.printf("%s, %s, %s, %s, %s, %s, %s, %s, %s\n",
				 * seq, site_code, site_name, sex, birth_cnt, father_age, mother_age,
				 * create_date); } ++counter;
				 */
			}
			// list_new_born_baby => [{"SEQ": "1", "SITE_CODE": "65000010", ..., "CREATE_DATE": "20210202"}, {}, {}, ..., {}]

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

	}
	
}
