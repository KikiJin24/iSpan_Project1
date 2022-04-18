package project1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.json.simple.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Output {
	

	public static List<Map<String, String>> ListMapResultSetFromDB() {
		List<Map<String, String>> listWaitOutput = new ArrayList<Map<String,String>>();
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(CommonConst.DB_URL, CommonConst.USER, CommonConst.PASSWORD);
			Statement stmt = conn.createStatement();
			ResultSet rs = null;
			rs = stmt.executeQuery(SQLStatement.selectOutputAll);  
			
			while(rs.next()){
				Map<String,String> mapInList = new HashMap<String, String>();
				mapInList.put(CommonConst.SEQ,rs.getString(CommonConst.SEQ));
				mapInList.put(CommonConst.SITE_CODE,rs.getString(CommonConst.SITE_CODE));
				mapInList.put(CommonConst.SITE_NAME,rs.getString(CommonConst.SITE_NAME)) ;
				mapInList.put(CommonConst.FATHER_AGE,rs.getString(CommonConst.FATHER_AGE));
				mapInList.put(CommonConst.MOTHER_AGE,rs.getString(CommonConst.MOTHER_AGE));
				mapInList.put(CommonConst.BIRTH_CNT,rs.getString(CommonConst.BIRTH_CNT));
				mapInList.put(CommonConst.SEX,rs.getString(CommonConst.SEX));
				mapInList.put(CommonConst.UPDATE_DATE,rs.getString(CommonConst.UPDATE_DATE));
				listWaitOutput.add(mapInList);
			}

		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listWaitOutput;
	}

	
    
   
	//outputJson
	public static void outputJson(String filename) {
		JSONObject outerjsonSeq = new JSONObject();
		List<Map<String, String>> listMapResultSet = ListMapResultSetFromDB();
		int counter = 0;
		for( Map<String, String> mapInList : listMapResultSet ) {
			         //new JSONObject(map),把map放進去產生json物件,型態很像map
    		JSONObject rowjson = new JSONObject(mapInList);
    		counter+=1;
    		outerjsonSeq.put(counter, rowjson);
    		if(counter == 1000) {
    			//System.out.println(counter);
    			break;
    		}
    	}
		try {
			Files.write(Paths.get(filename), outerjsonSeq.toJSONString().getBytes());
		    System.out.println("output Json success");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/* 未完成outputCSV
	public static void outputCSV() {
    	Connection conn = null;
    	String outFile = "res/outputMyProject1.csv";
    			try {
					conn = DriverManager.getConnection(CommonConst.DB_URL, CommonConst.USER, CommonConst.PASSWORD);
					Statement stmt = conn.createStatement();
					ResultSet rs = null;
					rs = stmt.executeQuery(SQLStatement.selectOutputAll); 
					if (rs.next()) {
						FileOutputStream fos = new FileOutputStream(outFile);
						//OutputStreamWriter osw = new OutputStreamWriter(fos, "MS950");

						System.out.println("File output is successful!");
					} 
					rs.close();
					stmt.close();
					
				} catch (SQLException e) {
					e.printStackTrace();
				}catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (conn != null)
						try {
							conn.close();
						} catch(SQLException e) {
							e.printStackTrace();
						}
				}
            System.out.println("Output CSV Success");
        }
	*/
	
	
	private static final String OutputPath = "./testOutputCSV.csv";
	//outputCSV
    public static void outputCSV() {
    		try {
    			FileOutputStream fos = new FileOutputStream(OutputPath);
    			OutputStreamWriter osw = new OutputStreamWriter(fos, "MS950");
    			BufferedWriter writer = new BufferedWriter(osw);	
    			CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(
    					CommonConst.SEQ, 
    					CommonConst.SITE_CODE, 
    					CommonConst.SITE_NAME, 
    					CommonConst.FATHER_AGE, 
    					CommonConst.MOTHER_AGE, 
    					CommonConst.BIRTH_CNT, 
    					CommonConst.SEX ,
    					CommonConst.UPDATE_DATE));
    			
    			List<Map<String, String>> listMapResultSet = ListMapResultSetFromDB();
    			for( Map<String, String> mapInList : listMapResultSet ) {
    				csvPrinter.printRecord(mapInList.get(CommonConst.SEQ),
    						mapInList.get(CommonConst.SITE_CODE),
    						mapInList.get(CommonConst.SITE_NAME),
    						mapInList.get(CommonConst.FATHER_AGE),
    						mapInList.get(CommonConst.MOTHER_AGE),
    						mapInList.get(CommonConst.BIRTH_CNT),
    						mapInList.get(CommonConst.SEX),
    						mapInList.get(CommonConst.UPDATE_DATE));
    			}
    			csvPrinter.flush();    
    			System.out.println("Output CSV Success");
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        
    }
