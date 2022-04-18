package project1;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Output2 {
	
    public static void outputCSV() throws IOException {
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}catch (Exception e) {
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
    }
