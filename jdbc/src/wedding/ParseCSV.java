package  wedding;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class ParseCSV {

	public static void parseCsv(   
			
			Map<String, String> area) {
		
		// read csv
		try (FileInputStream fis = new FileInputStream("C:\\Users\\USER\\Desktop\\product1\\AREA.csv");
				InputStreamReader isr = new InputStreamReader(fis, "MS950");
				BufferedReader br = new BufferedReader(isr);) {
			br.readLine();
			br.readLine();
			Iterable<CSVRecord> records = CSVFormat.RFC4180.withHeader("AREA_CODE","AREA_NAME"
					).parse(br);

			// int counter = 0;

			// parse data to list and map
			for (CSVRecord record : records) {

				/*
				 * String seq = record.get("SEQ"); --- 1 map.put("SEQ", seq); --- 2 String
				 * site_code = record.get("SITE_CODE"); String site_name =
				 * record.get("SITE_NAME"); String sex = record.get("SEX"); String birth_cnt =
				 * record.get("BIRTH_CNT"); String father_age = record.get("FATHER_AGE"); String
				 * mother_age = record.get("MOTHER_AGE"); String create_date =
				 * record.get("CREATE_DATE");
				 */
				
				Map<String, String> map = new HashMap<>();
				map.put("AREA_CODE", record.get("AREA_CODE")); // --- 1 + 2
				String AREA_CODE = record.get("AREA_CODE");
				map.put("AREA_NAME",record.get("AREA_NAME") );
				String AREA_NAME = record.get("AREA_NAME");
				
				
				if (!area.containsKey(AREA_CODE)) { // map_district.get(district) != null
					area.put(AREA_CODE, AREA_NAME);
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
